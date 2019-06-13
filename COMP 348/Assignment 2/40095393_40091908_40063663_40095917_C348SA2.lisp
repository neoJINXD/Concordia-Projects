;; EXERCISE 1

;; A) 

;; a. (CAR ’((A (B C)) D (E F))) → (A (B C))
;; b. (CDR ’((A (B C)) D (E F))) → (D (E F))
;; c. (CDR (CAR ’((A (B C)) D (E F)))) → (B C)
;; d. (CAR(CDR(CDR ’((A (B C)) D (E F))))) → (E F)
;; e. (CONS ’P (CONS ’O ’(L))) → (P O L)
;; f. (CONS (CAR ’((CAR A) (CDR A))) (CAR ’(((CONS A B))))) → ((CAR A) (CONS A B))


;; B)

;; ;; (? ’(A B C D))→ D
;; (write (car (cdr (cdr (cdr '(A B C D))))))

;; ;; (? ’((A (B C)) E)); → C
;; (write (car (cdr (car (cdr (car '((A (B C)) E)))))))

;; ;; (? ’(((D) E) U)); → D
;; (write (car (car (car '(((D) E) U)))))

;; ;; (? ’(((D) E) U)); → E
;; (write (car (cdr (car '(((D) E) U)))))

;;  EXERCISE 2

(defun elementIsNumber(L)
    (numberp (second L))
)

;; (write (elementIsNumber '(1 2 3 4)))
;; (write (elementIsNumber '(1 a b 4)))


;; EXERCISE 3

(defun elementIsList(L)
    (consp (second L))
)

;; (write (elementIsList '(1 2 3 4)))
;; (write (elementIsList '(1 a b 4)))
;; (write (elementIsList '(1 (2) 3 4)))
;; (write (elementIsList '(1 (a b) 4)))

;; EXERCISE 4

(defun base8(N)
    (cond 
        ((< N 8) (list N))
        (t (append (base8 (floor (/ N 8))) (list (floor (mod N 8)))))
    )
)

;; (write (base8 -1))
;; (write (base8 7))
;; (write (base8 8))
;; (write (base8 204))


;; EXERCISE 5


(defun myMember(x lst)
    (cond
        ((null lst) NIL)
        ((= x (car lst)) t)
        (t (myMember x (cdr lst)))
    )
)

;; (write (myMember 2 '(1 2)))
;; (write (myMember 3 '(1 2)))

;; EXERCISE 6

(defun nbDigits(N)

    (if (< N 10) 1
        (+ 1 (nbDigits(floor (/ N 10))))
    )

)

;; (write (nbDigits -12))
;; (write (nbDigits 0))
;; (write (nbDigits 12))
;; (write (nbDigits 1234))
;; (write (nbDigits 1234567890))


;; EXERCISE 7

(defun binary_length_(N)

    (if (or (zerop N) (= N 1)) 1
        (+ 1 (binary_length_(floor (/ N 2))))
    )
    

)


;; (write (binary_length_ 1))
;; (write (binary_length_ 4))
;; (write (binary_length_ 9))

;; EXERCISE 8

(defun binary_list(N)
    (cond
        ((or (zerop N) (< N 1)) '())
        ((= N 1) (list 1))
        (t (append (binary_list (floor (/ N 2))) (list (mod N 2))))
    )

)


;; (write (binary_list -1))
;; (write (binary_list 4))
;; (write (binary_list 10))


;; EXERCISE 9

(defun NTH2(n lst)
    (cond
        ((null lst) NIL)
        ((= n 1) (car lst))
        (t (NTH2 (- n 1) (cdr lst)))
    )

)

;; (write (NTH2 1 '()))
;; (write (NTH2 1 '(12 3 4)))
;; (write (NTH2 2 '(12 4 5)))
;; (write (NTH2 3 '(1 2 3)))



;;  EXERCISE 10

(defun NTHCDR2(n lst)
    (cond
        ((null lst) NIL)
        ((= n 0) lst)
        (t (NTHCDR2 (- n 1) (cdr lst)))
    )

)


;; (write (NTHCDR2 0 '()))
;; (write (NTHCDR2 0 '(1 2 3)))
;; (write (NTHCDR2 1 '(1 2 3)))


;; EXERCISE 11

(defun NTHCAR2(n lst)
    (cond
        ((null lst) NIL)
        ((zerop n) NIL)
        ((= n 1) (car lst))
        (t (append (list(car lst)) (list(NTHCAR2 (- n 1) (cdr lst)))))
    
    )

)


;; (write (NTHCAR2 0 '()))
;; (write (NTHCAR2 0 '(1 2 3 4)))
;; (write (NTHCAR2 1 '(1 2 5)))
;; (write (NTHCAR2 2 '(1 3 5)))

