#ifndef MAGICSQUARE
#define MAGICSQUARE

#include <string>
#include <iostream>
#include <stdexcept>

/// <summary>
/// Contains information about a Magic Square puzzle.
/// Also contains methods for generating a puzzle from a valid puzzle. 
/// 
/// @author donaldsa18
/// @author manalili18
/// </summary>




class MagicSquare
{
public:
	int n = 3;
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: public int[][] square;
	int **square;
	static int INITNUM;
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: public boolean[][] isVisible;
	bool **isVisible;
	static int MAX_SIZE;

	MagicSquare(int num);
	virtual MagicSquare *copy();
	virtual std::string toString() override;
	virtual void shuffleSquare();
	virtual void addToSquare(int add);

	virtual bool testSquare();

	virtual void toPuzzle(int dif);

	virtual std::string puzzleString();

	virtual bool isSolved();

	virtual void hint();

	virtual bool equals(MagicSquare *sq);

	virtual void saveCSV();
};


#endif	//#ifndef MAGICSQUARE
