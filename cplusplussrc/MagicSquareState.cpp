#include "MagicSquareState.h"

MagicSquareState::MagicSquareState(int num) : MagicSquare(num)
{
	sumRow = new int[num] {};
	sumCol = new int[num] {};
	countRow = new int[num] {};
	countCol = new int[num] {};
	countDiag = new int[num] {};
	mConst = num*((num*num) + 1) / 2;
	numEmpty = num*num;
//JAVA TO C++ CONVERTER NOTE: The following call to the 'RectangularArrays' helper class reproduces the rectangular array initialization that is automatic in Java:
//ORIGINAL LINE: isVisible = new boolean[n][n];
	isVisible = RectangularArrays::ReturnRectangularBoolArray(n, n);
	for (int i = 0;i < n;i++)
	{
		for (int j = 0;j < n;j++)
		{
			isVisible[i][j] = true;
		}
	}
}

MagicSquareState *MagicSquareState::toMagicSquareState(MagicSquare *sq)
{
	MagicSquareState *newSq = new MagicSquareState(sq->n);
	newSq->square = sq->square;
	newSq->testSquare();
	return newSq;
}

MagicSquareState *MagicSquareState::copy()
{
	MagicSquareState *newSq = new MagicSquareState(n);
	/*newSq.sumRow = Arrays.copyOf(sumRow,n);
	newSq.sumCol = Arrays.copyOf(sumCol,n);
	newSq.sumDiag = Arrays.copyOf(sumDiag,2);
	newSq.countRow = Arrays.copyOf(countRow,n);
	newSq.countCol = Arrays.copyOf(countCol,n);
	newSq.countDiag = Arrays.copyOf(countDiag,2);
	newSq.dict.addAll(dict);
	newSq.numEmpty = numEmpty;*/
	for (int i = 0;i < n;i++)
	{
		for (int j = 0;j < n;j++)
		{
			newSq->isVisible[i][j] = isVisible[i][j];
			newSq->square[i][j] = square[i][j];
		}
	}
	newSq->testSquare();
	return newSq;
}

void MagicSquareState::setNumber(int i, int j, int num)
{
	if (square[i][j] == MagicSquare::INITNUM && num != MagicSquare::INITNUM)
	{
		numEmpty--;
	}
	square[i][j] = num;
	sumRow[i] += num;
	countRow[i]++;
	sumCol[j] += num;
	countCol[j]++;
	if (i == j)
	{
		sumDiag[0] += num;
		countDiag[0]++;
	}
	if (i == n - j - 1)
	{
		sumDiag[1] += num;
		countDiag[1]++;
	}
	dict->add(num);
}

void MagicSquareState::removeInvisible()
{
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			if (!this->isVisible[i][j])
			{
				setNumber(i,j,MagicSquare::INITNUM);
			}
		}
	}
	testSquare();
}

void MagicSquareState::shuffleSquare()
{
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: int[][] newSq = new int[n][n];
//JAVA TO C++ CONVERTER NOTE: The following call to the 'RectangularArrays' helper class reproduces the rectangular array initialization that is automatic in Java:
	int **newSq = RectangularArrays::ReturnRectangularIntArray(n, n);
	int rand = static_cast<int>(Math::random()*8);
	for (int i = 0;i < n;i++)
	{
		for (int j = 0;j < n;j++)
		{
			switch (rand)
			{
				case 0:
					newSq[i][j] = square[i][j];
						break;
				case 1:
					newSq[i][j] = square[n - i - 1][j];
						break;
				case 2:
					newSq[i][j] = square[i][n - j - 1];
						break;
				case 3:
					newSq[i][j] = square[n - i - 1][n - j - 1];
						break;
				case 4:
					newSq[i][j] = square[j][i];
						break;
				case 5:
					newSq[i][j] = square[j][n - i - 1];
						break;
				case 6:
					newSq[i][j] = square[n - j - 1][i];
						break;
				case 7:
					newSq[i][j] = square[n - j - 1][n - i - 1];
						break;
				default:
						break;
			}
		}
	}
	square = newSq;
	testSquare();
}

void MagicSquareState::addToSquare(int add)
{
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			square[i][j] += add;
		}
	}
	testSquare();
}

bool MagicSquareState::testSquare()
{
	int sum = 0;
	sumRow = new int[n] {};
	sumCol = new int[n] {};
	countRow = new int[n] {};
	countCol = new int[n] {};
	countDiag = new int[2] {};
	numEmpty = n*n;
	bool rtn = true;
	for (int i = 0;i < n;i++)
	{
		for (int j = 0;j < n;j++)
		{
			if (square[i][j] != MagicSquare::INITNUM)
			{
				numEmpty--;
				if (dict->contains(square[i][j]))
				{
					rtn = false;
				}
			}
			dict->add(square[i][j]);
			sum += square[i][j];
			sumRow[i] += square[i][j];
			sumCol[j] += square[i][j];
			if (square[i][j] != INITNUM)
			{
				countRow[i]++;
				countCol[j]++;
			}
			if (i == j)
			{
				sumDiag[0] += square[i][j];
				if (square[i][j] != INITNUM)
				{
					countDiag[0]++;
				}
			}
			if (n - i - 1 == j)
			{
				sumDiag[1] += square[i][j];
				if (square[i][j] != INITNUM)
				{
					countDiag[1]++;
				}
			}
		}
	}
	//mConst = sum/n;
	//System.out.println("cols: "+Arrays.toString(sumCol)+"\nrows: "+Arrays.toString(sumRow)+"\ndiag: "+Arrays.toString(sumDiag));
	for (int i = 0;i < n;i++)
	{
		if (mConst != sumCol[i])
		{
			//System.out.println("not equal to "+magicConst+" @ "+i+", check col, "+Arrays.toString(sumCol));
			rtn = false;
		}
		if (mConst != sumRow[i])
		{
			//System.out.println("not equal to "+magicConst+" @ "+i+", check row, "+Arrays.toString(sumRow));
			rtn = false;
		}
	}
	if (sumCol[0] != sumDiag[0])
	{
		//System.out.println("check 1st diag, "+sumDiag[0]);
		rtn = false;
	}
	if (sumCol[0] != sumDiag[1])
	{
		//System.out.println("check 2nd diag, "+sumDiag[1]);
		rtn = false;
	}
	return rtn;
}
