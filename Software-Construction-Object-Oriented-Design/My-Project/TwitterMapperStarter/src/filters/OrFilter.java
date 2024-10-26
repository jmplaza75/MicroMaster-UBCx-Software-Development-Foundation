package filters;

import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * A filter that represents the logical OR operation of its child filters.
 * This filter matches a tweet if at least one of the child filters matches the tweet.
 */
public class OrFilter implements Filter {
    private final Filter child1;
    private final Filter child2;

    /**
     * Constructs an OrFilter with two child filters.
     *
     * @param child1 the first child filter
     * @param child2 the second child filter
     */
    public OrFilter(Filter child1, Filter child2) {
        this.child1 = child1;
        this.child2 = child2;
    }

    /**
     * Checks if the tweet matches at least one of the child filters.
     *
     * @param s the tweet to check
     * @return true if either child filter matches the tweet, false otherwise
     */
    @Override
    public boolean matches(Status s) {
        return child1.matches(s) || child2.matches(s);
    }

    /**
     * Returns a list of terms associated with this filter.
     * The terms are collected from the child filters.
     *
     * @return a list of terms from both child filters
     */
    @Override
    public List<String> terms() {
        List<String> terms = new ArrayList<>(child1.terms().size() + child2.terms().size());
        terms.addAll(child1.terms());
        terms.addAll(child2.terms());
        return terms;
    }

    /**
     * Returns a string representation of the filter, indicating the logical OR operation.
     *
     * @return a string representing the filter
     */
    @Override
    public String toString() {
        return "(" + child1.toString() + " OR " + child2.toString() + ")";
    }
}