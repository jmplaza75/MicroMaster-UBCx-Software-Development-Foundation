package filters;

/**
 * Parse a string in the filter language and return the filter.
 * Throws a SyntaxError exception on failure.
 * This is a top-down recursive descent parser (a.k.a., LL(1)).
 * The grammar (EBNF) for our filter language is:
 * goal    ::= expr
 * expr    ::= orexpr
 * orexpr  ::= andexpr ( "or" andexpr )*
 * andExpr ::= notExpr ( "and" notExpr )*
 * notExpr ::= prim | "not" notExpr
 * prim    ::= word | "(" expr ")"
 * The precedence order (decreasing) is:
 *      parens
 *      not
 *      and
 *      or
 */
public class Parser {
    private final Scanner scanner;

    private static final String LPAREN = "(";
    private static final String RPAREN = ")";
    private static final String OR = "or";
    private static final String AND = "and";
    private static final String NOT = "not";

    public Parser(String input) {
        this.scanner = new Scanner(input);
    }

    public Filter parse() throws SyntaxError {
        Filter result = orExpr();
        if (scanner.peek() != null) {
            throw new SyntaxError("Unexpected token after parsing complete: " + scanner.peek());
        }
        return result;
    }

    private Filter orExpr() throws SyntaxError {
        Filter result = andExpr();
        String token;
        while ((token = scanner.peek()) != null && OR.equals(token)) {
            scanner.advance();
            result = new OrFilter(result, andExpr());
        }
        return result;
    }

    private Filter andExpr() throws SyntaxError {
        Filter result = notExpr();
        String token;
        while ((token = scanner.peek()) != null && AND.equals(token)) {
            scanner.advance();
            result = new AndFilter(result, notExpr());
        }
        return result;
    }

    private Filter notExpr() throws SyntaxError {
        String token = scanner.peek();
        if (NOT.equals(token)) {
            scanner.advance();
            return new NotFilter(notExpr());
        } else {
            return prim();
        }
    }

    private Filter prim() throws SyntaxError {
        String token = scanner.peek();
        if (LPAREN.equals(token)) {
            scanner.advance();
            Filter result = orExpr();  // Start from the top of the precedence
            if (!RPAREN.equals(scanner.peek())) {
                throw new SyntaxError("Expected ')', found: " + scanner.peek());
            }
            scanner.advance();
            return result;
        } else {
            scanner.advance();
            return new BasicFilter(token);
        }
    }
}