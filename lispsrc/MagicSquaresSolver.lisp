;Transposes a 2D list
;http://stackoverflow.com/questions/3513128/transposing-lists-in-common-lisp
(defun rotate (list-of-lists)
  (apply #'mapcar #'list list-of-lists))

;Returns the location of the first 0 found in a list
(defun findZero(lst i)
	(if lst
		(if (= (car lst) 0)
			i
			(findZero (cdr lst) (+ i 1))
		)
		-1
	)
)

;Returns the sum of a magic square row, column, or diagonal
;based on the size of the magic square
(defun magicSum(n)
	(/ (* n (+ (* n n) 1)) 2)
)

;Returns the sum of all of the elements in a list
(defun sumList(lst)
	(if lst
		(+ (car lst) (sumList (cdr lst)))
		0
	)
)

;Checks for a single zero in a list and fills it in
;with the (magic number - sum)
(defun checkZerosList(lst size)
	(if (= (length (remove 0 lst)) (- size 1))
		(setf (elt lst (findZero lst 0)) (- (magicSum size) (sumList lst)))
	)
	lst
)

;Converts nested lists into a 2d array
(defun list-to-2d-array (list)
  (make-array (list (length list)
                    (length (first list)))
              :initial-contents list))

;Converts a 2d array into nested lists
(defun 2d-array-to-list (array)
  (loop for i below (array-dimension array 0)
        collect (loop for j below (array-dimension array 1)
                      collect (aref array i j))))

;Checks the diagonals for a single zero and fills it in
(defun checkDiagonals(sq size)
	(setf sum1 0)
	(setf sum2 0)
	(setf count1 0)
	(setf count2 0)
	(setf ind1 -1)
	(setf ind2 -1)
	(setf newsq (list-to-2d-array sq))
	(loop for i from 0 to (- size 1)
		do (progn
			(if (= (aref newsq i i) 0)
				(progn
					(setf count1 (+ count1 1))
					(setq ind1 i)
				)
			)
			(if (= (aref newsq i (- size 1 i)) 0)
				(progn
					(setf count2 (+ count2 1))
					(setq ind2 i)
				)
			)
			(setf sum1 (+ sum1 (aref newsq i i)))
			(setf sum2 (+ sum2 (aref newsq i (- size 1 i))))
		)
	)
	(if (= count1 1)
		(setf (aref newsq ind1 ind1) (- (magicSum size) sum1))
	)
	(if (= count2 1)
		(setf (aref newsq ind2 (- size 1 ind2)) (- (magicSum size) sum2))
	)
	(2d-array-to-list newsq)
)

;Checks each row for a single zero and fills it in with the (magic number - sum)
(defun checkZeros2D(sq size)
	(loop for i from 0 to (- size 1)
		collect (checkZerosList (elt sq i) size)
	)
)

;Solves any magic square that can be solved without a decision tree.
;It solves a magic square one element at a time until it reaches a
;2x2 empty square or larger.
(defun solve(sq)
	(setf size (list-length (elt sq 0)))
	(setf oldsq sq)
	(setf newsq (checkZeros2D sq size))
	(setf newsq (checkDiagonals newsq size))
	(setf newsq (rotate newsq))
	(setf newsq (checkZeros2D newsq size))
	(setf newsq (rotate newsq))
	(setf newsq (rotate newsq))
	(setf newsq (rotate newsq))
	(if (equal oldsq newsq)
		sq
		(solve newsq)
	)
)

;Tests a square and makes sure it obeys the properties of a magic square
(defun testSq(sq)
	(setf rotsq (rotate newsq))
	(setf size (list-length (elt sq 0)))
	(setf mSum (magicSum size))
	(setq diag1 0)
	(setq diag2 0)
	(loop for i from 0 to size
		do (
			(setq diag1 (+ diag1 (elt (elt sq i) i)))
			(setq diag2 (+ diag2 (elt (elt sq i) (- size i 1))))
			(if (or (/= (sumRow (elt sq i)) mSum) (/= (sumRow (elt rotsq i)) mSum))
				(return-from testSq nil)
			)
		)
	)
	(if (or (/= diag1 mSum) (/= diag2 mSum))
		(return-from testSq nil)
	)
	T
)