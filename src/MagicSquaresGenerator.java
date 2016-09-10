/**
 * 
 */

import java.util.Scanner;

public class MagicSquaresGenerator {
	public static void main(String[] args) {
		int n = 0;
		int d = 0;
		MagicSquare square = null;
		if(args.length == 3 || args.length == 5) {
			//n = Integer.parseInt(args[2]);
			for(int i=0;i<(args.length-1);i++) {
				try {
					if(args[i].toLowerCase().equals("-s")) {
						n = Integer.parseInt(args[i+1]);
					}
					if(args[i].toLowerCase().equals("-s")) {
						d = Integer.parseInt(args[i+1]);
					}
				}
				catch(NumberFormatException e) {
					System.out.println("Invalid integer.");
				}
			}
		}
		Scanner scan = new Scanner(System.in);
		while(n < 1 || n == 2 || n > MagicSquare.MAX_SIZE && square == null) {
			System.out.println("Enter the size of the magic square: ");
			n = scan.nextInt();
			square = generateSquare(n);
		}
		while(d < 1 || d > 5) {
			System.out.println("Enter a difficulty (1-5): ");
			d = scan.nextInt();
		}
		scan.close();
		System.out.println(square);
		System.out.println(square.toPuzzle(d));
	}
	
	private static MagicSquare generateSquare(int n) {
		if(n <= 0 || n > MagicSquare.MAX_SIZE || n == 2) {
			return null;
		}
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
			//Algorithm from https://en.wikipedia.org/wiki/Magic_square#A_method_of_constructing_a_magic_square_of_doubly_even_order
			int[] pattern = new int[n];
			for(int i=0;i<n;i++) {
				pattern[i] = ((i+1)%4)/2;
			}
			for(int i=0;i<n;i++) {
				for(int j=0;j<n;j++) {
					if(pattern[i] == pattern[j]) {
						newSquare.square[i][j] = n*(n-i)-j;
					}
					else {
						newSquare.square[i][j] = (n*i)+j+1;
					}
				}
			}
		}
		//Singly even squares
		else {
			//Algorithm from MATLAB magic function
			int half = n/2;
			MagicSquare[] squares = new MagicSquare[4];
			squares[0] = generateSquare(half);
			for(int i=1;i<4;i++) {
				squares[i] = squares[0].copy();
				squares[i].addToSquare((int)(i*Math.pow(half, 2)));
			}
			for(int i=0;i<n;i++) {
				for(int j=0;j<n;j++) { 
					if(i < half && j < half) {
						newSquare.square[i][j] = squares[0].square[i%half][j%half];
					}
					else if(i >= half && j < half) {
						newSquare.square[i][j] = squares[3].square[i%half][j%half];
					}
					else if(i < half && j >= half) {
						newSquare.square[i][j] = squares[2].square[i%half][j%half];
					}
					else {
						newSquare.square[i][j] = squares[1].square[i%half][j%half];
					}
				}
			}
			int k = (n-2)/4;
			int[] j2 = new int[(2*k) - 1];
			for(int i=0;i<k;i++) {
				j2[i] = i;
			}
			for(int i=((2*k)-2);i>=k;i--) {
				j2[i] = n-(2*k)+1+i;
			}
			for(int i=0;i<half;i++) {
				for(int j=0;j<j2.length;j++) { 
					int temp = newSquare.square[i][j2[j]];
					newSquare.square[i][j2[j]] = newSquare.square[i+half][j2[j]];
					newSquare.square[i+half][j2[j]] = temp;
				}
			}
			int[] i2 = {k,k+half};
			j2 = new int[]{0,k};
			for(int j=0;j<j2.length;j++) { 
				int temp = newSquare.square[i2[0]][j2[j]];
				newSquare.square[i2[0]][j2[j]] = newSquare.square[i2[1]][j2[j]];
				newSquare.square[i2[1]][j2[j]] = temp;
			}
		}
		System.out.println("The square is valid: "+newSquare.testSquare());
		//rotate and/or flip the square randomly
		return newSquare;
	}
}
