#ifndef MAGICSQUARESTATE
#define MAGICSQUARESTATE

#include "MagicSquare.h"

//JAVA TO C++ CONVERTER NOTE: Forward class declarations:
class MagicSquare;

/// <summary>
/// Contains additional functions to assist solving a Magic Square
/// 
/// @author Anthony Donaldson
/// @author Phillip Manalili-Simeon
/// </summary>

class MagicSquareState : public MagicSquare
{
public:
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: public int[] sumRow;
	int *sumRow;
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: public int[] sumCol;
	int *sumCol;
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: public int[] sumDiag = new int[2];
	int *sumDiag = new int[2] {};
	int mConst = 0;
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: public int[] countRow;
	int *countRow;
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: public int[] countCol;
	int *countCol;
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: public int[] countDiag = new int[2];
	int *countDiag = new int[2] {};
	Set<int> *dict = std::unordered_set<int>();
	int numEmpty = 0;

	MagicSquareState(int num);

	static MagicSquareState *toMagicSquareState(MagicSquare *sq);

	/// <summary>
	/// Returns a copy of the MagicSquareState 
	/// </summary>
	/// <returns>      A copy of the MagicSquareState </returns>
	virtual MagicSquareState *copy() override;

	virtual void setNumber(int i, int j, int num);

	virtual void removeInvisible();

	virtual void shuffleSquare() override;
	virtual void addToSquare(int add) override;

	virtual bool testSquare() override;
};


#endif	//#ifndef MAGICSQUARESTATE
