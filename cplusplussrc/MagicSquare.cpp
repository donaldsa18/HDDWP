#include "MagicSquare.h"
#include "RectangularArrays.h"
#include <ctime>
#include <map>
#include <iostream>
#include <fstream>

int MagicSquare::INITNUM = 0;
int MagicSquare::MAX_SIZE = 100;

MagicSquare::MagicSquare(int num)
{
	n = num;
//JAVA TO C++ CONVERTER NOTE: The following call to the 'RectangularArrays' helper class reproduces the rectangular array initialization that is automatic in Java:
//ORIGINAL LINE: square = new int[n][n];
	square = RectangularArrays::ReturnRectangularIntArray(n, n);
//JAVA TO C++ CONVERTER NOTE: The following call to the 'RectangularArrays' helper class reproduces the rectangular array initialization that is automatic in Java:
//ORIGINAL LINE: isVisible = new boolean[n][n];
	isVisible = RectangularArrays::ReturnRectangularBoolArray(n, n);
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			square[i][j] = INITNUM;
			isVisible[i][j] = true;
		}
	}
}

MagicSquare *MagicSquare::copy()
{
	MagicSquare *newSq = new MagicSquare(n);
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			newSq->square[i][j] = square[i][j];
			newSq->isVisible[i][j] = isVisible[i][j];
		}
	}
	return newSq;
}

std::string MagicSquare::toString()
{
	std::string rtn = "";
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			rtn += std::to_string(square[i][j]) + std::string("\t");
		}
		rtn += std::string("\n");
	}
	return rtn;
}

void MagicSquare::shuffleSquare()
{
	int **newSq = RectangularArrays::ReturnRectangularIntArray(n, n);
	srand(std::time(NULL));
	int rand = std::rand() % 8;
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
	delete[] square;
	square = newSq;
}

void MagicSquare::addToSquare(int add)
{
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			square[i][j] += add;
		}
	}
}



bool MagicSquare::testSquare()
{
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: int[] sumRow = new int[n];
	int *sumRow = new int[n] {};
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: int[] sumCol = new int[n];
	int *sumCol = new int[n] {};
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: int[] sumDiag = new int[2];
	int *sumDiag = new int[2] {};

	int sum = 0;
	int magicConst;
	int *dict = new int[n*n] {};
	for (int i = 0;i < n;i++)
	{
		for (int j = 0;j < n;j++)
		{
			if (square[i][j] == dict[i*n+j])
			{
				std::cout << std::string("Contains two ") << square[i][j] << std::string("'s") << std::endl;
				return false;
			}
			dict[i*n + j] = square[i][j];
			sum += square[i][j];
			sumRow[i] += square[i][j];
			sumCol[j] += square[i][j];
			if (i == j)
			{
				sumDiag[0] += square[i][j];
			}
			if (n - i - 1 == j)
			{
				sumDiag[1] += square[i][j];
			}
		}
	}
	magicConst = sum / n;
	//System.out.println("cols: "+Arrays.toString(sumCol)+"\nrows: "+Arrays.toString(sumRow)+"\ndiag: "+Arrays.toString(sumDiag));
	for (int i = 0;i < n;i++)
	{
		if (magicConst != sumCol[i])
		{
//JAVA TO C++ CONVERTER TODO TASK: There is no native C++ equivalent to 'toString':
			std::cout << std::string("not equal to ") << magicConst << std::string(" @ ") << i << std::string(", check col");
			delete[] sumRow;
			delete[] sumCol;
			delete[] sumDiag;
			return false;
		}
		if (magicConst != sumRow[i])
		{
//JAVA TO C++ CONVERTER TODO TASK: There is no native C++ equivalent to 'toString':
			std::cout << std::string("not equal to ") << magicConst << std::string(" @ ") << i << std::string(", check row");
			delete[] sumRow;
			delete[] sumCol;
			delete[] sumDiag;
			return false;
		}
	}
	if (sumCol[0] != sumDiag[0])
	{
		std::cout << std::string("check 1st diag, ") << sumDiag[0] << std::endl;
		delete[] sumRow;
		delete[] sumCol;
		delete[] sumDiag;
		return false;
	}
	if (sumCol[0] != sumDiag[1])
	{
		std::cout << std::string("check 2nd diag, ") << sumDiag[1] << std::endl;
		delete[] sumRow;
		delete[] sumCol;
		delete[] sumDiag;
		return false;
	}
	delete[] sumRow;
	delete[] sumCol;
	delete[] sumDiag;
	return true;
}

void MagicSquare::toPuzzle(int dif)
{
	srand(std::time(NULL));
	switch (0)
	{
	case 1:
		for (int i = 0; i < n / 2; i++)
		{
			int randJ = std::rand() % n;
			int randI = std::rand() % n;
			this->isVisible[randI][randJ] = false;
		}
	case 0:
		for (int i = 0; i < n; i++)
		{
			int randJ = std::rand() % n;
			this->isVisible[i][randJ] = false;
		}
		//iterate through columns

		for (int i = 0; i < n; i++)
		{
			bool isFull = true;
			for (int j = 0; j < n; j++)
			{
				//check if column is full
				isFull = isFull && this->isVisible[j][i];
			}
			//column done
			if (isFull)
			{
				int randJ = std::rand() % n;
				this->isVisible[randJ][i] = false;
			}
		}
	}
}

std::string MagicSquare::puzzleString()
{
	std::string rtn = "";
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			if (this->isVisible[i][j])
			{
				rtn += std::to_string(square[i][j]) + std::string("\t");
			}
			else
			{
				rtn += std::string("\t");
			}
		}
		rtn += std::string("\n");
	}
	return rtn;
}

bool MagicSquare::isSolved()
{
	int visibleCount = 0;
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			if (this->isVisible[i][j])
			{
				visibleCount++;
			}
		}

	}
	if (visibleCount == n * n)
	{
		return true;
	}
	else
	{
		return false;
	}
}

void MagicSquare::hint()
{
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			if (!this->isVisible[i][j])
			{
				this->isVisible[i][j] = true;
				return;
			}
		}
	}
}

bool MagicSquare::equals(MagicSquare *sq)
{
	if (sq == nullptr)
	{
		return false;
	}
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			if (square[i][j] != sq->square[i][j])
			{
				return false;
			}
		}
	}
	return true;
}

void MagicSquare::saveCSV()
{
	try
	{
		std::ofstream csv;
		csv.open("puzzle.csv");
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				if (isVisible[i][j])
				{
					csv << square[i][j]+"";
				}
				else
				{
					csv << '0';
				}
				if (j != n - 1)
				{
					csv << ',';
				}
			}
			if (i != n - 1)
			{
				csv << '\n';
			}
		}
		csv.close();
	}
	catch (std::exception &e)
	{
		std::cout << std::string("Something happened.") << std::endl;
	}
}
