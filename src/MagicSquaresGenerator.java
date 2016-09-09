/**
 * 
 */

/**
 * @author donaldsa18
 *
 */
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class MagicSquaresGenerator {
	public static void main(String[] args) {
		int n = 0;
		if(args.length == 3 && args[1] == "-s") {
			n = Integer.parseInt(args[2]);
		}
		if(args.length <= 1) {
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter the size of the magic square: ");
			n = scan.nextInt();
			scan.close();
		}
		else {
			System.out.println("Invalid arguments. Either run with no arguments or with the argument '-s' followed by a positive non-zero integer.");
		}
		if(n != 0) {
			MagicSquare square = generateSquare(n);
			System.out.println(square);
		}
	}
	
	private static MagicSquare generateSquare(int n) {
		MagicSquare newSquare = new MagicSquare(n);
		if(n % 2 == 1) {
			int rand = (int)(Math.random()*3);
			if(rand == 0) {
				for(int i=1;i<=n;i++) {
					for(int j=1;j<=n;j++) {
						//Algorithm from https://en.wikipedia.org/wiki/Magic_square#Method_for_constructing_a_magic_square_of_odd_order
						newSquare.square[i-1][j-1] = n*( (i+j-1+(int)(n/2))%n ) + ((i+2*j-2)%n) + 1;
					}
				}
			}
			else if(rand == 1) {
				for(int i=1;i<=n;i++) {
					for(int j=1;j<=n;j++) {
						//Algorithm from https://en.wikipedia.org/wiki/Magic_square#Method_for_constructing_a_magic_square_of_odd_order
						newSquare.square[i-1][n-j] = n*( (i+j-1+(int)(n/2))%n ) + ((i+2*j-2)%n) + 1;
					}
				}
			}
			else if(rand == 2) {
				for(int i=1;i<=n;i++) {
					for(int j=1;j<=n;j++) {
						//Algorithm from https://en.wikipedia.org/wiki/Magic_square#Method_for_constructing_a_magic_square_of_odd_order
						newSquare.square[n-i][j-1] = n*( (i+j-1+(int)(n/2))%n ) + ((i+2*j-2)%n) + 1;
					}
				}
			}
			else if(rand == 3) {
				for(int i=1;i<=n;i++) {
					for(int j=1;j<=n;j++) {
						//Algorithm from https://en.wikipedia.org/wiki/Magic_square#Method_for_constructing_a_magic_square_of_odd_order
						newSquare.square[n-i][n-j] = n*( (i+j-1+(int)(n/2))%n ) + ((i+2*j-2)%n) + 1;
					}
				}
			}
			return newSquare;
		}
		else {
			return null;
		}
	}
	private static int[] generateShuffledArray(int size) {
		//Efficient way of generating random numbers
		int[] seqArr = new int[size];
		for(int i=0;i<size;i++) {
			seqArr[i] = i;
		}
		Random rand = ThreadLocalRandom.current();
		for(int i=size-1; i > 0; i--) {
			int ind = rand.nextInt(i+1);
			int a = seqArr[i];
			seqArr[ind] = seqArr[i];
			seqArr[i] = a;
		}
		return seqArr;
	}
}
