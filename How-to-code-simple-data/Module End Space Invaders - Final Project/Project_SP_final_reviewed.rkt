#lang racket

;;--------------------------------------------------------------------------------------
;; Project: Tank and Invader Game
;; Description: This Racket code creates a basic game where a tank shoots missiles
;;              at invaders. The game handles drawing the tank, missiles, and invaders,
;;              as well as the logic for movement and collision.
;;--------------------------------------------------------------------------------------

;;--------------------------------------------------------------------------------------
;; Libraries
;;--------------------------------------------------------------------------------------

(require 2htdp/universe)
(require 2htdp/image)

;;--------------------------------------------------------------------------------------
;; Constants
;;--------------------------------------------------------------------------------------

;; Screen size constants
(define WIDTH 400)
(define HEIGHT 600)

;; Struct to define visual properties of invaders
;; Includes width, height, and color information for rendering
(define-struct invader-properties (width height color))
(define INVADER-WIDTH 10)
(define INVADER-HEIGHT 15)
(define INVADER-COLOR "blue")
(define INVADER-PROPERTIES (make-invader-properties INVADER-WIDTH INVADER-HEIGHT INVADER-COLOR))

;; Struct to define visual properties of the tank
;; Includes dimensions and color for different parts of the tank
(define-struct tank-properties (tread-center-width tread-center-height
                                                   tread-color tread-outline-width tread-outline-height
                                                   body-color gun-width gun-height body-width body-height))
(define (create-visual-properties width height color)
  (make-invader-properties width height color))
(define TANK-PROPERTIES (create-visual-properties 28 8 "black"))

;; Struct to define visual properties of missiles
;; Includes dimensions, color, and speed for rendering and behavior
(define-struct missile-properties (width height color speed))
(define MISSILE-PROPERTIES (make-missile-properties 5 10 "red" 5))



;; Tank constants
(define TANK-TREAD-CENTER-WIDTH 28)
(define TANK-TREAD-CENTER-HEIGHT 8)
(define TANK-TREAD-COLOR "black")
(define TANK-TREAD-OUTLINE-WIDTH 30)
(define TANK-TREAD-OUTLINE-HEIGHT 10)
(define TANK-BODY-COLOR "green")
(define TANK-GUN-WIDTH 5)
(define TANK-GUN-HEIGHT 10)
(define TANK-BODY-WIDTH 20)
(define TANK-BODY-HEIGHT 10)

;; Missile constants
(define MISSILE-WIDTH 5)
(define MISSILE-HEIGHT 15)
(define MISSILE-COLOR "red")
(define INVADER-X-SPEED 1.5) 
(define INVADER-Y-SPEED 1.5)
(define TANK-SPEED 3)
(define MISSILE-SPEED 10)
(define HIT-RANGE 10)
(define INVADE-RATE 30)


;;--------------------------------------------------------------------------------------
;; Helper Functions
;;--------------------------------------------------------------------------------------

;; Helper function to create ellipses
(define (create-ellipse width height style color)
  (ellipse width height style color))

;; Helper function to create rectangles
(define (create-rectangle width height color)
  (rectangle width height "solid" color))


;;--------------------------------------------------------------------------------------
;; Object Creation Functions
;;--------------------------------------------------------------------------------------

;; Function to create an invader
(define (create-invader)
  (overlay/xy (create-ellipse INVADER-WIDTH INVADER-HEIGHT "outline" INVADER-COLOR)
              -5 6
              (create-ellipse (* 2 INVADER-WIDTH) (* INVADER-HEIGHT 2/3) "solid" INVADER-COLOR)))

;; Function to create a tank
(define (create-tank)
  (overlay/xy (overlay (create-ellipse TANK-TREAD-CENTER-WIDTH TANK-TREAD-CENTER-HEIGHT "solid" TANK-TREAD-COLOR)
                       (create-ellipse TANK-TREAD-OUTLINE-WIDTH TANK-TREAD-OUTLINE-HEIGHT "solid" TANK-BODY-COLOR))
              5 -14
              (above (create-rectangle TANK-GUN-WIDTH TANK-GUN-HEIGHT TANK-TREAD-COLOR)
                     (create-rectangle TANK-BODY-WIDTH TANK-BODY-HEIGHT TANK-TREAD-COLOR))))


;; Function to create a missile
(define (create-missile)
  (create-ellipse MISSILE-WIDTH MISSILE-HEIGHT "solid" MISSILE-COLOR))



(define BACKGROUND (empty-scene WIDTH HEIGHT))
(define INVADER (create-invader))
(define TANK (create-tank))
(define TANK-MID (/ (image-height TANK) 2))
(define TANK-Y (- HEIGHT TANK-MID))
(define TANK-LIMR (- WIDTH (/ (image-width TANK) 2)))
(define TANK-LIML (/ (image-width TANK) 2))
(define INVADER-LIMR (- WIDTH (/ (image-width INVADER) 2)))
(define INVADER-LIML (/ (image-width INVADER) 2))
(define MISSILE (create-missile))


(define-struct game (invaders missiles t))
;; Game is (make-game  (listof Invader) (listof Missile) Tank)
;; interp. the current state of a space invaders game
;;         representing the invaders, missiles, and tank position


(define-struct tank (x dir))
;; Tank is (make-tank Number Integer[-1, 1])
;; interp. the tank location is x, and the y-coordinate is HEIGHT - TANK-HEIGHT in screen coordinates.
;; The tank moves TANK-SPEED pixels per clock tick:
;;      - left if dir is -1 
;;      - right if dir is 1
(define space-ship (make-tank (/ WIDTH 2) 1))   ; tank centered, moving right


(define-struct invader (x y dx))
;; Invader is (make-invader Number Number Number)
;; interp. the invader's position is at (x, y) in screen coordinates.
;;         dx represents the horizontal speed of the invader (pixels per clock tick):
;;         - Positive dx: moving to the right
;;         - Negative dx: moving to the left


(define-struct missile (x y))
;; Missile is (make-missile Number Number)
;; interp. represents the missile's location at coordinates (x, y)


;; Main function to run the game with initial game state g.
;; g: Game
(define (main g)
  (begin
    ;; Initialize the big-bang framework with the game state `g`
    (big-bang g                         
              ;; On each clock tick, update the game state with revstate
              (on-tick   revstate)          
              ;; Render the game using draw-game
              (to-draw   draw-game)
              ;; Handle key presses using keypressed function
              (on-key    keypressed)
              ;; Stop the game when the finished? condition is met
              (stop-when finished?))
    ;; Return void to avoid any additional outputs after the game ends
    (void)))



;; -----------------------------------------------------------------------------
;; Function: start-game
;; -----------------------------------------------------------------------------
;; Description: This function initializes and starts the game when called. 
;; It does not run automatically upon loading the program, allowing for more 
;; control over when the game starts. To start the game, the user needs to 
;; explicitly call `(start-game)` in the REPL.
;; -----------------------------------------------------------------------------
;; Arguments: None
;; -----------------------------------------------------------------------------
;; Returns: None (It triggers the game by calling the `main` function)
;; -----------------------------------------------------------------------------
(define start-game 
  (lambda () 
    ;; Call the `main` function with the initial game state
    (main (make-game empty empty space-ship))))


;; -----------------------------------------------------------------------------
;; Game Development - Functions
;; -----------------------------------------------------------------------------
;; These functions handle the game logic, including updating the game state,
;; managing invaders, missiles, and checking collisions between them.
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; revstate
;; -----------------------------------------------------------------------------
;; Updates the game state. This includes:
;; - Adding new invaders.
;; - Checking for invader-missile collisions and updating the list of invaders.
;; - Updating the position of the missiles and the tank.
;; Arguments:
;;  - `g`: the current game state.
;; Returns:
;;  - The updated game state.
;; -----------------------------------------------------------------------------
(define (update-invaders invaders missiles)
  (add-invader (check-invaders (review-invaders invaders missiles))))
(define (revstate g)
  (make-game (update-invaders (game-invaders g) (game-missiles g))
             (full-missiles (missiles-impacted (game-missiles g) (game-invaders g)))
             (move-tank (game-t g))))

;; -----------------------------------------------------------------------------
;; add-invader
;; -----------------------------------------------------------------------------
;; Adds a new invader to the game based on a random chance (INVADE-RATE).
;; Arguments:
;;  - `loi`: list of invaders.
;; Returns:
;;  - The updated list of invaders.
;; -----------------------------------------------------------------------------
(define (add-invader loi)
  (if (> INVADE-RATE (random 1000))
      (cons (make-invader (random WIDTH) 10 1) loi)  ;; Add a new invader
      loi))  ;; No invader added

;; -----------------------------------------------------------------------------
;; check-invaders
;; -----------------------------------------------------------------------------
;; Updates the position of each invader in the list.
;; Arguments:
;;  - `loi`: list of invaders.
;; Returns:
;;  - The updated list of invaders.
;; -----------------------------------------------------------------------------
(define (check-invaders loi)
  (let loop ([loi loi] [result empty])
    (if (empty? loi)
        result
        (loop (rest loi) (cons (movement-invader (first loi)) result)))))

;; -----------------------------------------------------------------------------
;; movement-invader
;; -----------------------------------------------------------------------------
;; Updates the position of an invader, reversing direction if it reaches the screen limits.
;; Arguments:
;;  - `invader`: the invader to move.
;; Returns:
;;  - The updated invader with the new position.
;; -----------------------------------------------------------------------------
(define (movement-invader invader)
  (let* ([invader-x (invader-x invader)]
         [invader-dx (invader-dx invader)]
         [new-x (+ invader-x (* invader-dx INVADER-X-SPEED))]
         [new-y (+ (invader-y invader) (* INVADER-Y-SPEED (abs invader-dx)))])
    ;; Handle screen limits and reverse direction if necessary
    (cond [(> new-x INVADER-LIMR) (make-invader INVADER-LIMR new-y (- invader-dx))]
          [(< new-x INVADER-LIML) (make-invader INVADER-LIML new-y (- invader-dx))]
          [else (make-invader new-x new-y invader-dx)])))

;; -----------------------------------------------------------------------------
;; review-invaders
;; -----------------------------------------------------------------------------
;; Removes invaders that have been hit by missiles.
;; Arguments:
;;  - `loi`: list of invaders.
;;  - `lom`: list of missiles.
;; Returns:
;;  - The updated list of invaders.
;; -----------------------------------------------------------------------------
(define (review-invaders loi lom)
  (let loop ([loi loi] [result empty])
    (if (empty? loi)
        result
        (let ([i (first loi)])
          (loop (rest loi) (if (check-invader-death? i lom) result (cons i result)))))))

;; -----------------------------------------------------------------------------
;; check-invader-death?
;; -----------------------------------------------------------------------------
;; Checks if an invader has been hit by a missile.
;; Arguments:
;;  - `invader`: the invader to check.
;;  - `lom`: list of missiles.
;; Returns:
;;  - `true` if the invader was hit, otherwise `false`.
;; -----------------------------------------------------------------------------
(define (check-invader-death? invader lom)
  (let loop ([lom lom])
    (cond [(empty? lom) false]
          [(impact-invader-missile? invader (first lom)) true]
          [else (loop (rest lom))])))

;; -----------------------------------------------------------------------------
;; impact-invader-missile?
;; -----------------------------------------------------------------------------
;; Checks if a missile has hit an invader.
;; Arguments:
;;  - `invader`: the invader to check.
;;  - `missile`: the missile to check.
;; Returns:
;;  - `true` if there was an impact, otherwise `false`.
;; -----------------------------------------------------------------------------
(define (impact-invader-missile? invader missile)
  (let* ([invader-x (invader-x invader)]
         [missile-x (missile-x missile)]
         [invader-y (invader-y invader)]
         [missile-y (missile-y missile)])
    (and (<= (abs (- invader-x missile-x)) HIT-RANGE)
         (<= (abs (- invader-y missile-y)) HIT-RANGE))))


;; -----------------------------------------------------------------------------
;; full-missiles
;; -----------------------------------------------------------------------------
;; Updates the position of each missile and removes missiles that are off the screen.
;; Arguments:
;;  - `lom`: list of missiles.
;; Returns:
;;  - The updated list of missiles.
;; -----------------------------------------------------------------------------
(define (full-missiles lom)
  (let loop ([lom lom] [result empty])
    (if (empty? lom)
        result
        (let ([missile (first lom)])
          (loop (rest lom) (if (out-missile? missile) result (cons (check-missile missile) result)))))))

;; -----------------------------------------------------------------------------
;; check-missile
;; -----------------------------------------------------------------------------
;; Updates the position of a missile.
;; Arguments:
;;  - `missile`: the missile to update.
;; Returns:
;;  - The updated missile with the new position.
;; -----------------------------------------------------------------------------
(define (check-missile missile)
  (let* ([missile-x (missile-x missile)]
         [new-missile-y (- (missile-y missile) MISSILE-SPEED)])
    (make-missile missile-x new-missile-y)))

;; -----------------------------------------------------------------------------
;; out-missile?
;; -----------------------------------------------------------------------------
;; Checks if a missile is off the screen.
;; Arguments:
;;  - `missile`: the missile to check.
;; Returns:
;;  - `true` if the missile is off the screen, otherwise `false`.
;; -----------------------------------------------------------------------------
(define (out-missile? missile)
  (let* ([missile-y (missile-y missile)]
         [half-missile-height (/ (image-height MISSILE) 2)])
    (<= missile-y (- half-missile-height))))

;; -----------------------------------------------------------------------------
;; missiles-impacted
;; -----------------------------------------------------------------------------
;; Removes missiles that have impacted invaders.
;; Arguments:
;;  - `lom`: list of missiles.
;;  - `loi`: list of invaders.
;; Returns:
;;  - The updated list of missiles.
;; -----------------------------------------------------------------------------
(define (missiles-impacted lom loi)
  (let loop ([lom lom] [result empty])
    (if (empty? lom)
        result
        (let ([missile (first lom)])
          (loop (rest lom) (if (missile-impact? missile loi) result (cons missile result)))))))

;; -----------------------------------------------------------------------------
;; missile-impact?
;; -----------------------------------------------------------------------------
;; Checks if a missile has hit any invader.
;; Arguments:
;;  - `missile`: the missile to check.
;;  - `loi`: list of invaders.
;; Returns:
;;  - `true` if the missile has hit an invader, otherwise `false`.
;; -----------------------------------------------------------------------------
(define (missile-impact? missile loi)
  (let loop ([loi loi])
    (cond [(empty? loi) false]
          [(impact-invader-missile? (first loi) missile) true]
          [else (loop (rest loi))])))

;; -----------------------------------------------------------------------------
;; keypressed
;; -----------------------------------------------------------------------------
;; Handles keypress events and updates the game state accordingly.
;; Arguments:
;;  - `g`: the current game state
;;  - `keyp`: the key that was pressed
;; Returns the updated game state, depending on the key press.
;; -----------------------------------------------------------------------------
(define (keypressed g keyp)
  (let* ([tank (game-t g)]                  ;; Extract the tank from the game state
         [dir (tank-dir tank)]              ;; Get the current direction of the tank
         [x (tank-x tank)])                 ;; Get the current x-coordinate of the tank
    ;; Handle key press conditions
    (cond
      ;; Fire a missile if the spacebar is pressed
      [(key=? keyp " ")
       (make-game (game-invaders g)                    ;; Keep invaders unchanged
                  (cons (make-missile x HEIGHT)        ;; Add a new missile
                        (game-missiles g))             ;; To the list of existing missiles
                  tank)]                               ;; Tank remains unchanged

      ;; Move tank left if the current direction is 1 and "left" is pressed
      [(and (= 1 dir) (key=? keyp "left"))
       (make-game (game-invaders g)
                  (game-missiles g)
                  (make-tank x -1))]                   ;; Update tank direction to move left

      ;; Move tank right if the current direction is -1 and "right" is pressed
      [(and (= -1 dir) (key=? keyp "right"))
       (make-game (game-invaders g)
                  (game-missiles g)
                  (make-tank x 1))]                    ;; Update tank direction to move right

      ;; No key match, return the unchanged game state
      [else g])))



;; -----------------------------------------------------------------------------
;; move-tank
;; -----------------------------------------------------------------------------
;; Moves the tank according to its current direction and speed.
;; Arguments:
;;  - `tnk`: the current tank
;; Returns an updated tank with the new x-coordinate.
;; Ensures that the tank does not exceed the game boundaries.
;; -----------------------------------------------------------------------------
(define (move-tank tnk)
  (let* ([x (tank-x tnk)]                  ;; Get the current x-coordinate of the tank
         [dir (tank-dir tnk)]              ;; Get the current direction of the tank
         [new-x (+ x (* TANK-SPEED dir))]) ;; Calculate the new x-coordinate based on direction and speed
    ;; Handle boundary conditions
    (cond 
      ;; If the tank moves beyond the right boundary, set it to the right limit
      [(> new-x TANK-LIMR)
       (make-tank TANK-LIMR dir)]           ;; Move tank to right boundary

      ;; If the tank moves beyond the left boundary, set it to the left limit
      [(< new-x TANK-LIML)
       (make-tank TANK-LIML dir)]           ;; Move tank to left boundary

      ;; Otherwise, update tank to the new x-coordinate
      [else
       (make-tank new-x dir)])))            ;; Move tank to the new position



;; -----------------------------------------------------------------------------
;; Game State Check Functions
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; finished?
;; -----------------------------------------------------------------------------
;; Checks if the game is finished based on the position of the invaders.
;; If any invader has reached the bottom of the screen, the game is considered over.
;; Arguments:
;;  - `g`: the current game state
;; Returns:
;;  - `true` if the game is finished, otherwise `false`.
;; -----------------------------------------------------------------------------
(define (finished? g)
  ;; Check if any invader has reached the bottom using the invader-down? function
  (invader-down? (game-invaders g)))

;; -----------------------------------------------------------------------------
;; invader-down?
;; -----------------------------------------------------------------------------
;; Checks if any invader has reached the bottom of the screen.
;; It recursively checks each invader in the list to see if it has reached the bottom.
;; Arguments:
;;  - `loi`: list of invaders (list of invader objects)
;; Returns:
;;  - `true` if at least one invader has reached the bottom, otherwise `false`.
;; -----------------------------------------------------------------------------
(define (invader-down? loi)
  ;; Loop through the list of invaders recursively
  (let loop ([loi loi])
    (cond
      ;; If the list is empty, no invader has reached the bottom
      [(empty? loi) false]

      ;; If the first invader in the list has reached the bottom, return true
      [(reach? (first loi)) true]

      ;; Otherwise, continue checking the rest of the invaders
      [else (loop (rest loi))])))

;; -----------------------------------------------------------------------------
;; reach?
;; -----------------------------------------------------------------------------
;; Checks if an individual invader has reached the bottom of the screen.
;; Arguments:
;;  - `invader`: an invader object
;; Returns:
;;  - `true` if the invader's y-coordinate is greater than or equal to the screen height.
;; -----------------------------------------------------------------------------
(define (reach? invader)
  ;; Check if the invader's y-coordinate has reached or exceeded the height of the screen
  (>= (invader-y invader) HEIGHT))




;; -----------------------------------------------------------------------------
;; Game Drawing Functions
;; -----------------------------------------------------------------------------
;; These functions are responsible for drawing the game elements on the screen.
;; They handle the tank, missiles, invaders, and background image composition.
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; draw-game
;; -----------------------------------------------------------------------------
;; Draws the current game state, including the tank, missiles, and invaders.
;; Takes a game state `g` and returns the image with all elements drawn.
;; -----------------------------------------------------------------------------
(define (draw-game g)
  (let* ([tank (game-t g)]               ;; Get tank from the game state
         [missiles (game-missiles g)]    ;; Get missiles from the game state
         [invaders (game-invaders g)])   ;; Get invaders from the game state
    ;; Draw the tank, missiles, and invaders in the correct order
    (draw-tank tank
               (draw-missiles missiles
                             (draw-invaders invaders)))))

;; -----------------------------------------------------------------------------
;; draw-tank
;; -----------------------------------------------------------------------------
;; Draws the tank on the given image.
;; Takes the tank `tnk` and the current image `image` as arguments.
;; -----------------------------------------------------------------------------
(define (draw-tank tnk image)
  ;; Place the tank image at the correct coordinates
  (place-image TANK (tank-x tnk) TANK-Y image))

;; -----------------------------------------------------------------------------
;; draw-missiles
;; -----------------------------------------------------------------------------
;; Draws the missiles on the given image.
;; Takes a list of missiles `lom` and the current image `image` as arguments.
;; -----------------------------------------------------------------------------
(define (draw-missiles lom image)
  ;; Recursive function to place each missile on the image
  (let loop ([lom lom] [image image])
    (if (empty? lom)
        image
        (loop (rest lom) (new-missile (first lom) image)))))

;; -----------------------------------------------------------------------------
;; new-missile
;; -----------------------------------------------------------------------------
;; Places a new missile on the given image.
;; Takes a missile and the current image as arguments.
;; -----------------------------------------------------------------------------
(define (new-missile missile image)
  ;; Use the generalized place-object function to position the missile
  (place-object MISSILE (missile-x missile) (missile-y missile) image))

;; -----------------------------------------------------------------------------
;; draw-invaders
;; -----------------------------------------------------------------------------
;; Draws the invaders on the given image.
;; Takes a list of invaders `loi` and returns the image with all invaders drawn.
;; -----------------------------------------------------------------------------
(define (draw-invaders loi)
  ;; Use foldl to place each invader on the background image
  (foldl (lambda (invader acc-img)
           (new-invader invader acc-img))
         BACKGROUND
         loi))

;; -----------------------------------------------------------------------------
;; new-invader
;; -----------------------------------------------------------------------------
;; Places a new invader on the given image.
;; Takes an invader and the current image as arguments.
;; -----------------------------------------------------------------------------
(define (new-invader invader image)
  ;; Use the generalized place-object function to position the invader
  (place-object INVADER (invader-x invader) (invader-y invader) image))

;; -----------------------------------------------------------------------------
;; place-object
;; -----------------------------------------------------------------------------
;; General function to place any object (missile, invader, etc.) on the given image.
;; Takes an object `obj`, x and y coordinates, and the current image.
;; -----------------------------------------------------------------------------
(define (place-object obj x y image)
  ;; Place the object at the specified coordinates on the image
  (place-image obj x y image))
