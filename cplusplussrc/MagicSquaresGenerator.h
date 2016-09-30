#ifndef MAGICSQUARESGENERATOR
#define MAGICSQUARESGENERATOR

#include <string>
#include <iostream>
#include <cmath>
#include <boost/algorithm/string.hpp>

//JAVA TO C++ CONVERTER NOTE: Forward class declarations:
class MagicSquare;

/// <summary>
/// Generates a MagicSquare of size 3-100, prints it to the screen, and saves it as puzzle.csv
/// 
/// @author Anthony Donaldson
/// @author Phillip Manalili-Simeon
/// </summary>



class MagicSquaresGenerator
{
	static void main(std::string args[]);


	/// <summary>
	/// Returns a solved MagicSquare of size n
	/// </summary>
	/// <param name="n">  The dimensions of the new magic square </param>
	/// <returns>     Returns a solved MagicSquare of size n </returns>
public:
	static MagicSquare *generateSquare(int n);
};


#endif	//#ifndef MAGICSQUARESGENERATOR
