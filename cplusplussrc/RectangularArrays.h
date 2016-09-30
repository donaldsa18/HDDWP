//----------------------------------------------------------------------------------------
//	Copyright © 2007 - 2016 Tangible Software Solutions Inc.
//	This class can be used by anyone provided that the copyright notice remains intact.
//
//	This class includes methods to convert Java rectangular arrays (jagged arrays
//	with inner arrays of the same length).
//----------------------------------------------------------------------------------------
class RectangularArrays
{
public:
    static int** ReturnRectangularIntArray(int size1, int size2)
    {
        int** newArray = new int*[size1];
        for (int array1 = 0; array1 < size1; array1++)
        {
            newArray[array1] = new int[size2] {};
        }

        return newArray;
    }

    static bool** ReturnRectangularBoolArray(int size1, int size2)
    {
        bool** newArray = new bool*[size1];
        for (int array1 = 0; array1 < size1; array1++)
        {
            newArray[array1] = new bool[size2] {};
        }

        return newArray;
    }
};