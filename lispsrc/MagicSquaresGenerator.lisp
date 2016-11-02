;Generates magic squares of odd length
(defun generateOdd(n)
	(if (= (mod n 2) 1)
		(loop for i from 1 to n
			collect (loop for j from 1 to n
				collect (+ (* n (mod (+ i j -1 (floor (/ n 2))) n)) (mod (-(+ i (* 2 j)) 2) n) 1)
			)
		)
		1
	)
)

;Generates magic squares with a length dividible by 4
(defun generateDoublyEven(n)
	(setf pattern 
		(loop for i from 1 to n
			collect (floor (/ (mod i 4) 2))
		)
	)
	(if (= (mod n 4) 0)
		(loop for i from 0 to (- n 1)
			collect (loop for j from 0 to (- n 1)
				collect (if (= (nth i pattern) (nth j pattern))
					(- (* n (- n i)) j)
					(+ (* n i) j 1)
				)
			)
		)
		1
	)
)

;Adds n to each element in l
(defun addToList(l n)
	(loop for e in l
		collect (+ e n)
	)
)

;Adds n to each element in a 2D list
(defun addTo(n sq add)
	(loop for i from 0 to (- n 1)
		collect (addToList (elt sq i) add)
	)
)

;Converts nested lists into a 2D array
;http://stackoverflow.com/questions/9549568/common-lisp-convert-between-lists-and-arrays
(defun list-to-2d-array (list)
  (make-array (list (length list)
                    (length (first list)))
              :initial-contents list))

;Converts a 2D array into nested lists
(defun 2d-array-to-list (array)
  (loop for i below (array-dimension array 0)
        collect (loop for j below (array-dimension array 1)
                      collect (aref array i j))))

;Generates a magic square when it's size is not divisible by 4, but still even
(defun generateSinglyEven(n)
	(setf half (floor (/ n 2)))
	(setf sq (generateOdd half))
	(setf quad 
			(loop for i from 0 to 3
				collect (addto half sq (floor (* i half half)))
			)
	)
	(setf newsq
		(loop for i from 0 to (- n 1)
			collect (loop for j from 0 to (- n 1)
				collect (cond
					((and (< i half) (< j half)) (elt (elt (elt quad 0) (mod i half)) (mod j half)))
					((and (>= i half) (< j half)) (elt (elt (elt quad 3) (mod i half)) (mod j half)))
					((and (< i half) (>= j half)) (elt (elt (elt quad 2) (mod i half)) (mod j half)))
					((= 1 1) (elt (elt (elt quad 1) (mod i half)) (mod j half)))
				)
			)
		)
	)
	(setf k (/ (- n 2) 4))
	(setf j2
		(append 
			(loop for i from 0 to (- k 1)
				collect i
			)
			(loop for j from k to (- (* 2 k) 2)
				collect (- (+ n j 1) (* 2 k))
			)
		)
	)
	(setf newsqarr (list-to-2d-array newsq))
	(loop for i from 0 to (- half 1)
		do (loop for j from 0 to (- (* 2 k) 2)
			do (progn
				(setf temp (aref newsqarr i (elt j2 j)))
				(setf (aref newsqarr i (elt j2 j)) (aref newsqarr (+ i half) (elt j2 j)))
				(setf (aref newsqarr (+ i half) (elt j2 j)) temp)
			)
		)
	)
	(loop for j from 0 to (- (* 2 k) 2)
		do (progn
			(setf temp (aref newsqarr k (elt j2 j)))
			(setf (aref newsqarr k (elt j2 j)) (aref newsqarr (+ k half) (elt j2 j)))
			(setf (aref newsqarr (+ k half) (elt j2 j)) temp)
		)
	)
	(return-from generateSinglyEven (2d-array-to-list newsqarr))
)

;Generates a magic square of any size using algorithms based
;off of its divisibility. A hard constraint is set to limit
;the size to between 2 and 100 to ensure good runtime performance.
(defun generate(n)
	(if (and (> n 2) (< n 100))
		(if (= (mod n 2) 1)
			(generateOdd n)
			(if (= (mod n 4) 0)
				(generateDoublyEven n)
				(generateSinglyEven n)
			)
		)
		1
	)
)
