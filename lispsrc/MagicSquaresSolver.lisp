
;http://stackoverflow.com/questions/3513128/transposing-lists-in-common-lisp
(defun rotate (list-of-lists)
  (apply #'mapcar #'list list-of-lists))

(defun findZero(lst i)
	(if lst
		(if (= (car lst) 0)
			i
			(findZero (cdr lst) (+ i 1))
		)
		-1
	)
)

(defun magicSum(n)
	(/ (* n (+ (* n n) 1)) 2)
)

(defun sumList(lst)
	(if lst
		(+ (car lst) (sumList (cdr lst)))
		0
	)
)

(defun checkZerosList(lst size)
	(if (= (length (remove 0 lst)) (- size 1))
		(setf (elt lst (findZero lst 0)) (- (magicSum size) (sumList lst)))
	)
	lst
)

(defun list-to-2d-array (list)
  (make-array (list (length list)
                    (length (first list)))
              :initial-contents list))

(defun 2d-array-to-list (array)
  (loop for i below (array-dimension array 0)
        collect (loop for j below (array-dimension array 1)
                      collect (aref array i j))))

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
(defun checkZeros2D(sq size)
	(loop for i from 0 to (- size 1)
		collect (checkZerosList (elt sq i) size)
	)
)

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

