#include "MagicSquaresGenerator.h"
#include "MagicSquare.h"

void MagicSquaresGenerator::main(std::string args[])
{
	int n = 0;
	int d = -1;
	MagicSquare *square = nullptr;
	if ((sizeof(args) / sizeof(args[0])) == 3 || (sizeof(args) / sizeof(args[0])) == 5)
	{
		//n = Integer.parseInt(args[2]);
		for (int i = 0;i < ((sizeof(args) / sizeof(args[0])) - 1);i++)
		{
			try
			{
				if (boost::to_lower_copy(args[i]).equals("-s"))
				{
					n = std::stoi(args[i + 1]);
				}
				if (boost::to_lower_copy(args[i]).equals("-s"))
				{
					d = std::stoi(args[i + 1]);
				}
			}
			catch (NumberFormatException e)
			{
				std::cout << std::string("Invalid integer.") << std::endl;
			}
		}
	}
	Scanner *scan = new Scanner(System::in);
	while (n < 1 || n == 2 || n > MagicSquare::MAX_SIZE && square == nullptr)
	{
		std::cout << std::string("Enter the size of the magic square: ") << std::endl;
		n = scan->nextInt();
		scan->nextLine();
		square = generateSquare(n);
	}
	while (d < 0 || d > 1)
	{
		std::cout << std::string("0 for Easy, 1 for Hard: ") << std::endl;
		d = scan->nextInt();
		scan->nextLine();
		square->toPuzzle(0);
	}

	//System.out.println(square);
	std::cout << square->puzzleString() << std::endl;
	square->saveCSV();
	std::cout << std::string("Saved .csv") << std::endl;
	/*while(!square.isSolved()) {
		System.out.println("Press enter for a hint.");
		//"(h)int or (s)ave as .csv and exit?"
		String k = scan.nextLine();
		//String k = scan.nextChar();
		//switch to select, another while loop if unterminated
		square.hint();
		System.out.println(square.puzzleString());
	}*/
	//System.out.println(square.toPuzzle(d));
	scan->close();

	//System.out.println("Solved.");
}

MagicSquare *MagicSquaresGenerator::generateSquare(int n)
{
	if (n <= 0 || n > MagicSquare::MAX_SIZE || n == 2)
	{
		return nullptr;
	}
	MagicSquare *newSquare = new MagicSquare(n);
	//Odd squares
	if (n % 2 == 1)
	{
		for (int i = 1;i <= n;i++)
		{
			for (int j = 1;j <= n;j++)
			{
				//Algorithm from https://en.wikipedia.org/wiki/Magic_square#Method_for_constructing_a_magic_square_of_odd_order
				newSquare->square[i - 1][j - 1] = n*((i + j - 1 + static_cast<int>(n / 2)) % n) + ((i + 2*j - 2) % n) + 1;
			}
		}
	}
	//Doubly even squares
	else if (n % 4 == 0)
	{
		//Algorithm from https://en.wikipedia.org/wiki/Magic_square#A_method_of_constructing_a_magic_square_of_doubly_even_order
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: int[] pattern = new int[n];
		int *pattern = new int[n] {};
		for (int i = 0;i < n;i++)
		{
			pattern[i] = ((i + 1) % 4) / 2;
		}
		for (int i = 0;i < n;i++)
		{
			for (int j = 0;j < n;j++)
			{
				if (pattern[i] == pattern[j])
				{
					newSquare->square[i][j] = n*(n - i) - j;
				}
				else
				{
					newSquare->square[i][j] = (n*i) + j + 1;
				}
			}
		}
	}
	//Singly even squares
	else
	{
		//Algorithm from MATLAB magic function
		int half = n / 2;
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: MagicSquare[] squares = new MagicSquare[4];
		MagicSquare **squares = new MagicSquare*[4] {};
		squares[0] = generateSquare(half);
		for (int i = 1;i < 4;i++)
		{
			squares[i] = squares[0]->copy();
			squares[i]->addToSquare(static_cast<int>(i*std::pow(half, 2)));
		}
		for (int i = 0;i < n;i++)
		{
			for (int j = 0;j < n;j++)
			{
				if (i < half && j < half)
				{
					newSquare->square[i][j] = squares[0]->square[i % half][j % half];
				}
				else if (i >= half && j < half)
				{
					newSquare->square[i][j] = squares[3]->square[i % half][j % half];
				}
				else if (i < half && j >= half)
				{
					newSquare->square[i][j] = squares[2]->square[i % half][j % half];
				}
				else
				{
					newSquare->square[i][j] = squares[1]->square[i % half][j % half];
				}
			}
		}
		int k = (n - 2) / 4;
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: int[] j2 = new int[(2*k) - 1];
		int *j2 = new int[(2*k) - 1] {};
		for (int i = 0;i < k;i++)
		{
			j2[i] = i;
		}
		for (int i = ((2*k) - 2);i >= k;i--)
		{
			j2[i] = n - (2*k) + 1 + i;
		}
		for (int i = 0;i < half;i++)
		{
			for (int j = 0;j < j2->length;j++)
			{
				int temp = newSquare->square[i][j2[j]];
				newSquare->square[i][j2[j]] = newSquare->square[i + half][j2[j]];
				newSquare->square[i + half][j2[j]] = temp;
			}
		}
//JAVA TO C++ CONVERTER WARNING: Java to C++ Converter has converted this array to a pointer. You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: int[] i2 = {k,k+half};
		int *i2 = new int[] {k,k + half};
		j2 = new int[]{0,k};
		for (int j = 0;j < j2->length;j++)
		{
			int temp = newSquare->square[i2[0]][j2[j]];
			newSquare->square[i2[0]][j2[j]] = newSquare->square[i2[1]][j2[j]];
			newSquare->square[i2[1]][j2[j]] = temp;
		}
	}
	newSquare->shuffleSquare();
	//rotate and/or flip the square randomly
	return newSquare;
}
