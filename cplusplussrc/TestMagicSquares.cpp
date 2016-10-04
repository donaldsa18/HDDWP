#include "TestMagicSquares.h"
#include "MagicSquaresGenerator.h"
#include "MagicSquareState.h"
#include "GeneralSolver.h"

void TestMagicSquares::main(std::string args[])
{
	MagicSquareState *square = MagicSquareState::toMagicSquareState(MagicSquaresGenerator::generateSquare(10));
	std::cout << std::string("The original square:") << std::endl;
	std::cout << square << std::endl;
	square->toPuzzle(1);
	std::cout << std::string("The original square with hidden elements:") << std::endl;
	std::cout << square->puzzleString() << std::endl;
	MagicSquareState *removed = square->copy();
	removed->removeInvisible();
	std::cout << std::string("The original square with removed elements:") << std::endl;
	std::cout << removed << std::endl;
	MagicSquareState *start = MagicSquareState::toMagicSquareState(removed);
	GeneralSolver *solver = new GeneralSolver();
	MagicSquareState *finished = solver->checkPossibilities(start);
	std::cout << std::string("The solved square:") << std::endl;
	std::cout << finished << std::endl;
	std::cout << std::string("The squares are equal: ") << square->equals(finished) << std::endl;
}
