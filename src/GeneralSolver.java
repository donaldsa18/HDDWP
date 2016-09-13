import java.io.*;
import java.util.Scanner;
import java.lang.Integer;

public class GeneralSolver {
	
	static MagicSquare sq;
	static int mConst; //magic constant
	//static boolean[][][] possibleNums; //[i][j][which are possible]
	static int[] sumRow;
	static int[] sumCol;
	static int[] sumDiag;
	static boolean[] isUsed; //numbers used;
	
	/*
	public GeneralSolver(MagicSquare magic) {
		sq = magic;
		int size = sq.n;
		mConst = size * ((size * size) + 1 ) / 2;
		
		possibleNums = new boolean [size][size][size*size+1];
		
		
		/*
		isUsed = new boolean[size * size + 1];
		for(boolean x : isUsed) x = false;
		isUsed[0] = true;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(sq.isVisible[i][j]){
					isUsed[sq.square[i][j]] = true;
				}
			}
		}
		
	}*/
	
	
	public static void main(String[] args) throws FileNotFoundException {
		//load csv
		sq = loadCSV();
		int size = sq.n;
		mConst = size * ((size * size) + 1 ) / 2;
		sumRow = new int[size]; //r
		sumCol = new int[size]; //c
		sumDiag = new int[2]; //d
		checkZeros(); //can solve easiest versions
		analyze(); //grabs values for sums and whether a number is used
		
		//the following looks for largest sum to start guessing
		//unfinished
		int highest = 0;
		int index = 0;
		char orientation;
		for(int i = 0; i < sumRow.length; i++) {
		    if(highest<sumRow[i]) {
		    	highest = sumRow[i];
		    	index = i;
		    	orientation = 'r';
		    }
		}
		
		for(int i = 0; i < sumCol.length; i++) {
		    if(highest<sumCol[i]) {
		    	highest = sumCol[i];
		    	index = i;
		    	orientation = 'c';
		    }
		}
		
		for(int i = 0; i < sumDiag.length; i++) {
		    if(highest<sumDiag[i]) {
		    	highest = sumDiag[i];
		    	index = i;
		    	orientation = 'd';
		    }
		}
		
		System.out.println(sq);
		
	}
	
	public static void analyze() {
		//sum rows and cols
		int size = sq.n;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				sumRow[i] += sq.square[i][j];
				sumCol[i] += sq.square[j][i];
			}
		}
		//sum diag
		for(int i = 0; i < size; i++) {
			sumDiag[0] += sq.square[i][i];
			sumDiag[1] += sq.square[i][size-1-i];
		}
		//check if used
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				isUsed[sq.square[i][j]] = true;
			}
		}
	}
	
	public static void checkZeros() {
		int size = sq.n;
		int num0s = 0;
		int sum = 0;
		int[] loc = new int[2];
		boolean newInsert = true;
		while(newInsert){
			newInsert = false;
			//look through rows
			for(int i = 0; i < size; i++) {
				//reset values for each row
				num0s = 0;
				sum = 0;
				for(int j = 0; j < size; j++) {
					//count 0s
					if(sq.square[i][j] == 0) {
						num0s++;
						loc[0] = i;
						loc[1] = j;
						//locate last 0
					}
					sum += sq.square[i][j];
					//find sum of row
				}
				//insert value if there is only one 0
				if(num0s == 1) {
					sq.square[loc[0]][loc[1]] = mConst - sum;
					
					newInsert = true;
				}
			}
			//look through cols (using similar logic)
			for(int i = 0; i < size; i++) {
				num0s = 0;
				sum = 0;
				for(int j = 0; j < size; j++) {
					if(sq.square[j][i] == 0) {
						num0s++;
						loc[0] = j;
						loc[1] = i;
					}
					sum += sq.square[j][i];
				}
				if(num0s == 1) {
					sq.square[loc[0]][loc[1]] = mConst - sum;
					
					newInsert = true;
				}
			}
			
			//look through diags
			num0s = 0;
			sum = 0;
			for(int i = 0; i < size; i++) {
				if(sq.square[i][i] == 0) {
					num0s++;
					loc[0] = i;
				}
				sum += sq.square[i][i];
			}
			if(num0s == 1) {
				sq.square[loc[0]][loc[0]] = mConst - sum;
				newInsert = true;
			}
			num0s = 0;
			sum = 0;
			for(int i = 0; i < size; i++) {
				if(sq.square[i][size-i-1] == 0) {
					num0s++;
					loc[0] = i;
				}
				sum += sq.square[i][size-i-1];
			}
			if(num0s == 1) {
				sq.square[loc[0]][size-loc[0]-1] = mConst - sum;
				newInsert = true;
			}
		}//if filled in any square, repeat
	}
	
	public static MagicSquare loadCSV() throws FileNotFoundException {
		Scanner s = new Scanner(new File("puzzle.csv"));
		String csv = "";
		
		while(s.hasNextLine()){
			csv += s.nextLine() + "\n";
		}
		
		String[] temp1 = csv.split("\n");
		MagicSquare rtn = new MagicSquare(temp1.length);
		
		/*
		for(String str1 : temp1) {
			String[] temp2 = str1.split(",");
			for(String str2 : temp2) {
				
			}
		}*/
		
		for(int i = 0; i < rtn.n; i++) {
			String[] temp2 = temp1[i].split(",");
			for(int j = 0; j < rtn.n; j++) {
				rtn.square[i][j] = Integer.parseInt(temp2[j]);
			}
		}
		
		
		System.out.println(rtn.toString());
		
		s.close();
		
		return rtn;
	}
	
	public static void analyze1(){
		
		//possibleNums = new boolean [size][size][size*size+1]; // check this in analyze2
		/*
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				//check 
			}
		}*/
		
	}
	
}
