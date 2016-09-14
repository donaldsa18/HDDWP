
public class TestMagicSquares {
	public static void main(String[] args) {
		MagicSquare square = MagicSquaresGenerator.generateSquare(7);
		System.out.println("The original square:");
		System.out.println(square);
		square.toPuzzle(5);
		System.out.println("The original square with hidden elements:");
		System.out.println(square.puzzleString());
		MagicSquare removed = square.copy();
		removed.removeInvisible(); 
		System.out.println("The original square with removed elements:");
		System.out.println(removed);
		//MagicSquareState start = removed.toMagicSquareState();
		//start.testSquare();
		//MagicSquareSolver solver = new MagicSquareSolver();
		//MagicSquareState finished = solver.solve(start);
		//System.out.println("The solved square:");
		//System.out.println(finished);
		//System.out.println("The squares are equal: "+square.equals(finished));
	}
}
