#include "GeneralSolver.h"
#include "MagicSquareState.h"
#include "MagicSquare.h"


void GeneralSolver::main(std::string args[]) throw(std::exception)
{
	//load csv
	MagicSquareState *sq = loadCSV();
	GeneralSolver *solve = new GeneralSolver();
	//solve.checkZeros(sq);
	solve->checkPossibilities(sq);
	std::cout << sq << std::endl;

}

MagicSquareState *GeneralSolver::checkPossibilities(MagicSquareState *sq)
{
	//the following looks for largest sum to start guessing
	int highest = 0;
	int index = 0;
	char orientation = 'a';
	int size = sq->n;
	GeneralSolver *solve = new GeneralSolver();
	solve->checkZeros(sq); //can solve easiest versions
	sq->testSquare(); //grabs values for sums and whether a number is used
	if (sq->testSquare())
	{
		return sq;
	}

	//The following three for loops look for the line with the highest value.
	for (int i = 0; i < sq->sumRow->length; i++)
	{
		if (highest < sq->sumRow[i])
		{
			highest = sq->sumRow[i];
			index = i;
			orientation = 'r';
		}
	}

	for (int i = 0; i < sq->sumCol->length; i++)
	{
		if (highest < sq->sumCol[i])
		{
			highest = sq->sumCol[i];
			index = i;
			orientation = 'c';
		}
	}

	for (int i = 0; i < sq->sumDiag->length; i++)
	{
		if (highest < sq->sumDiag[i])
		{
			highest = sq->sumDiag[i];
			index = i;
			orientation = 'd';
		}
	}

	if (orientation != 'a')
	{
		int remainingSum = sq->mConst - highest;
		int remainingNums = 0;
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: int[] set = new int[sq.numEmpty];
		int *set = new int[sq->numEmpty] {};
		std::unordered_set<std::vector<int>> dict;
		int j = 0;
		for (int i = 0; i < remainingSum; i++)
		{
			if (!sq->dict->contains(i))
			{
				set[j++] = i;
			}
		}
		if (orientation == 'r')
		{
			for (int i = 0; i < size; i++)
			{
				if (sq->square[index][i] == MagicSquare::INITNUM)
				{
					remainingNums++;
				}
			}
		}
		else if (orientation == 'c')
		{
			for (int i = 0; i < size; i++)
			{
				if (sq->square[i][index] == MagicSquare::INITNUM)
				{
					remainingNums++;
				}
			}
		}
		else if (orientation == 'd')
		{
			if (index == 0)
			{
				for (int i = 0; i < size; i++)
				{
					if (sq->square[i][i] == MagicSquare::INITNUM)
					{
						remainingNums++;
					}
				}
			}
			else if (index == 1)
			{
				for (int i = 0; i < size; i++)
				{
					if (sq->square[size - i - 1][i] == MagicSquare::INITNUM)
					{
						remainingNums++;
					}
				}
			}
		}
		//System.out.println(Arrays.toString(set));
		std::vector<MagicSquareState*> squares;
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: int[] tup = new int[remainingNums];
		int *tup = new int[remainingNums] {};
		subsetSum(set, tup, remainingNums, 0, 0, 0, remainingSum, dict);

		for (auto list : dict)
		{
			std::vector<std::vector<int>> &combinations = tryAllComb(list,list.size());
			if (orientation == 'r')
			{
				for (int i = 0; i < combinations.size(); i++)
				{
					int c = 0;
					MagicSquareState *newSq = sq->copy();
					for (int k = 0; k < size; k++)
					{
						if (newSq->square[index][k] == MagicSquare::INITNUM)
						{
							newSq->setNumber(index,k,combinations[i][c++]);
						}
					}
					newSq->testSquare();
					squares.push_back(newSq);
				}
			}
			else if (orientation == 'c')
			{
				for (int i = 0; i < combinations.size(); i++)
				{
					int c = 0;
					MagicSquareState *newSq = sq->copy();
					for (int k = 0; k < size; k++)
					{
						if (newSq->square[k][index] == MagicSquare::INITNUM)
						{
							newSq->setNumber(k,index,combinations[i][c++]);
						}
					}
					newSq->testSquare();
					squares.push_back(newSq);
				}
			}
			else if (orientation == 'd')
			{
				for (int i = 0; i < combinations.size(); i++)
				{
					int c = 0;
					MagicSquareState *newSq = sq->copy();
					for (int k = 0; k < size; k++)
					{
						if (index == 0 && sq->square[k][k] == MagicSquare::INITNUM)
						{
							newSq->setNumber(k,k,combinations[i][c++]);
						}
						else if (index == 1 && sq->square[size - k - 1][k] == MagicSquare::INITNUM)
						{
							newSq->setNumber(size - k - 1,k,combinations[i][c++]);
						}
					}
					newSq->testSquare();
					squares.push_back(newSq);
				}
			}
		}
		//System.out.println("The square size: "+squares.size());
		for (int i = 0; i < squares.size();i++)
		{
			MagicSquareState *newSq = checkPossibilities(squares[i]);
			if (newSq->testSquare())
			{
				return newSq;
			}
		}
	}
	return sq;
}

std::vector<std::vector<int>> GeneralSolver::tryAllComb(std::vector<int> &nums, int length)
{
	std::vector<std::vector<int>> rtn;
	std::vector<int> arr;
	if (length == 1)
	{
		arr.push_back(nums[0]);
		rtn.push_back(arr);
	}
	else
	{
		std::vector<std::vector<int>> &result = tryAllComb(nums, length - 1);
		for (int i = length - 1; i >= 0; i--)
		{
			//add last number first and then the rest of the result
			arr.push_back(nums[i]);
			arr.addAll(result[i]);
			rtn.push_back(arr);

			//add last number last and then the rest of the result first
			arr = std::vector<int>();
			arr.addAll(result[i]);
			arr.push_back(nums[i]);
			rtn.push_back(arr);
		}
	}
	return rtn;
}

void GeneralSolver::subsetSum(int set[], int tup[], int tupLength, int tupMaxSize, int sum, int ite, int target, std::unordered_set<std::vector<int>> &dict)
{
	if (tupLength >= tupMaxSize)
	{
		return;
	}
	if (target == sum)
	{
		std::vector<int> newTup;
		for (int i = 0; i < tupLength; i++)
		{
			newTup.push_back(tup[i]);
		}
		dict.insert(newTup);
		if (ite+1 < (sizeof(set) / sizeof(set[0])) && sum - set[ite] + set[ite+1] <= target)
		{
			subsetSum(set, tup, tupLength - 1,tupMaxSize+1, sum - set[ite], ite+1, target, dict);
		}
		return;
	}
	else
	{
		if (ite < (sizeof(set) / sizeof(set[0])) && sum - set[ite] + set[ite+1] <= target)
		{
			for (int i = ite; i < (sizeof(set) / sizeof(set[0])); i++)
			{
				tup[tupLength] = set[i];
				if (sum + set[i] <= target)
				{
					subsetSum(set, tup, tupLength + 1,tupMaxSize-1,sum + set[i], i + 1, target, dict);
				}
			}
		}
	}
}

void GeneralSolver::checkZeros(MagicSquareState *sq)
{
	int size = sq->n;
	int num0s = 0;
	int sum = 0;
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: int[] loc = new int[2];
	int *loc = new int[2] {};
	bool newInsert = true;
	while (newInsert)
	{
		newInsert = false;
		//look through rows
		for (int i = 0; i < size; i++)
		{
			//reset values for each row
			num0s = 0;
			sum = 0;
			for (int j = 0; j < size; j++)
			{
				//count 0s
				if (sq->square[i][j] == 0)
				{
					num0s++;
					loc[0] = i;
					loc[1] = j;
					//locate last 0
				}
				sum += sq->square[i][j];
				//find sum of row
			}
			//insert value if there is only one 0
			if (num0s == 1)
			{
				sq->square[loc[0]][loc[1]] = sq->mConst - sum;

				newInsert = true;
			}
		}
		//look through cols (using similar logic)
		for (int i = 0; i < size; i++)
		{
			num0s = 0;
			sum = 0;
			for (int j = 0; j < size; j++)
			{
				if (sq->square[j][i] == 0)
				{
					num0s++;
					loc[0] = j;
					loc[1] = i;
				}
				sum += sq->square[j][i];
			}
			if (num0s == 1)
			{
				sq->square[loc[0]][loc[1]] = sq->mConst - sum;

				newInsert = true;
			}
		}

		//look through diags
		num0s = 0;
		sum = 0;
		for (int i = 0; i < size; i++)
		{
			if (sq->square[i][i] == 0)
			{
				num0s++;
				loc[0] = i;
			}
			sum += sq->square[i][i];
		}
		if (num0s == 1)
		{
			sq->square[loc[0]][loc[0]] = sq->mConst - sum;
			newInsert = true;
		}
		num0s = 0;
		sum = 0;
		for (int i = 0; i < size; i++)
		{
			if (sq->square[i][size - i - 1] == 0)
			{
				num0s++;
				loc[0] = i;
			}
			sum += sq->square[i][size - i - 1];
		}
		if (num0s == 1)
		{
			sq->square[loc[0]][size - loc[0] - 1] = sq->mConst - sum;
			newInsert = true;
		}
	} //if filled in any square, repeat
}

MagicSquareState *GeneralSolver::loadCSV() throw(FileNotFoundException)
{
	File tempVar("puzzle.csv");
	Scanner *s = new Scanner(&tempVar);
	std::string csv = "";

	while (s->hasNextLine())
	{
		csv += s->nextLine() + std::string("\n");
	}

//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: String[] temp1 = csv.split("\n");
//JAVA TO C++ CONVERTER TODO TASK: There is no direct native C++ equivalent to the Java String 'split' method:
	std::string *temp1 = csv.split("\n");
	MagicSquareState *rtn = new MagicSquareState(temp1->length);

	for (int i = 0; i < rtn->n; i++)
	{
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: String[] temp2 = temp1[i].split(",");
//JAVA TO C++ CONVERTER TODO TASK: There is no direct native C++ equivalent to the Java String 'split' method:
		std::string *temp2 = temp1[i].split(",");
		for (int j = 0; j < rtn->n; j++)
		{
			rtn->square[i][j] = std::stoi(temp2[j]);
		}
	}

//JAVA TO C++ CONVERTER TODO TASK: There is no native C++ equivalent to 'toString':
	std::cout << rtn->toString() << std::endl;

	s->close();

	return rtn;
}
