/**
 * 
 */

/**
 * @author donaldsa18
 *
 */
import java.util.Scanner;

public class MagicSquaresGenerator {
	public static void main(String[] args) {
		int n = 0;
		int d = 1;
		
		if(args.length == 3 && args[1] == "-s") {
			n = Integer.parseInt(args[2]);
		}
		if(args.length <= 1) {
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter the size of the magic square: ");
			n = scan.nextInt();
			System.out.println("Enter a difficulty (1-5): ");
			d = scan.nextInt();
			scan.close();
		}
		else {
			System.out.println("Invalid arguments. Either run with no arguments or with the argument '-s' followed by a positive non-zero integer.");
		}
		if(n != 0) {
			MagicSquare square = generateSquare(n);
			System.out.println(square);
			System.out.println(square.toPuzzle(d));
		}
	}
	
	private static MagicSquare generateSquare(int n) {
		MagicSquare newSquare = new MagicSquare(n);
		//Odd squares
		if(n % 2 == 1) {
			for(int i=1;i<=n;i++) {
				for(int j=1;j<=n;j++) {
					//Algorithm from https://en.wikipedia.org/wiki/Magic_square#Method_for_constructing_a_magic_square_of_odd_order
					newSquare.square[i-1][j-1] = n*( (i+j-1+(int)(n/2))%n ) + ((i+2*j-2)%n) + 1;
				}
			}
		}
		//Doubly even squares
		else if(n % 4 == 0) {
			for(int i=0;i<n;i++) {
				for(int j=0;j<n;j++) {
					//Algorithm from https://en.wikipedia.org/wiki/Magic_square#A_method_of_constructing_a_magic_square_of_doubly_even_order
					if(i == j || (n-i-1) == j) {
						newSquare.square[i][j] = (i*n)+j+1;
					}
					else {
						newSquare.square[i][j] = (n*n)-((i*n)+j);
					}
				}
			}
		}
		//Singly even squares
		else {
			int p = n/2;
			MagicSquare[] squares = new MagicSquare[4];
			squares[0] = generateSquare(p);
			for(int i=1;i<4;i++) {
				squares[i] = squares[0].copy();
				squares[i].addToSquare((int)(i*Math.exp(p)));
			}
			for(int i=0;i<n;i++) {
				for(int j=0;j<n;j++) { 
					if(i < p && j < p) {
						newSquare.square[i][j] = squares[0].square[i%p][j%p];
					}
					else if(i >= p && j < p) {
						newSquare.square[i][j] = squares[3].square[i%p][j%p];
					}
					else if(i < p && j >= p) {
						newSquare.square[i][j] = squares[2].square[i%p][j%p];
					}
					else {
						newSquare.square[i][j] = squares[1].square[i%p][j%p];
					}
				}
			}
			if(n == 2) {
				newSquare.shuffleSquare();
				return newSquare;
			}
			int k = (n-2)/4;
			int[] j2 = new int[2*k];
			for(int i=0;i<=k;i++) {
				j2[i] = i;
			}
			for(int i=(n-k+2);i<=n;i++) {
				j2[i] = i;
			}
			for(int i=0;i<n;i++) {
				for(int j=0;j<j2.length;j++) { 
					int temp = newSquare.square[i][j2[j]];
					newSquare.square[i][j2[j]] = newSquare.square[(i+p)%n][j2[j]];
					newSquare.square[(i+p)%n][j2[j]] = temp;
				}
			}
			int[] i2 = {k+1,k+1+p};
			j2 = new int[]{1,k+1};
			for(int i=0;i<i2.length;i++) {
				for(int j=0;j<j2.length;j++) { 
					int temp = newSquare.square[i2[i]][j2[j]];
					newSquare.square[i2[i]][j2[j]] = newSquare.square[i2[(i+1)%2]][j2[j]];
					newSquare.square[i2[(i+1)%2]][j2[j]] = temp;
				}
			}
		}
		//rotate and/or flip the square randomly
		newSquare.shuffleSquare();
		return newSquare;
	}
}
