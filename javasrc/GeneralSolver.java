import java.io.*;
import java.util.Scanner;
import java.lang.Integer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class GeneralSolver {
	
	//static MagicSquare sq;
	//static int mConst; //magic constant
	//static boolean[][][] possibleNums; //[i][j][which are possible]
	//static int[] sumRow;
	//static int[] sumCol;
	//static int[] sumDiag;
	//static boolean[] isUsed; //numbers used;
	//static int numEmpty;
	
	public static void main(String[] args) throws FileNotFoundException {
		//load csv
		MagicSquareState sq = loadCSV();
		//int size = sq.n;
		//mConst = size * ((size * size) + 1 ) / 2;
		//sumRow = new int[size]; //r
		//sumCol = new int[size]; //c
		//sumDiag = new int[2]; //d
		GeneralSolver solve = new GeneralSolver();
		solve.checkZeros(sq); //can solve easiest versions
		solve.analyze(sq); //grabs values for sums and whether a number is used
		solve.checkPossibilities(sq);
		
		
		
		System.out.println(sq);
		
	}
	void checkPossibilities(MagicSquareState sq) {
		//the following looks for largest sum to start guessing
		int highest = 0;
		int index = 0;
		char orientation = 'a';
		int size = sq.n;
		GeneralSolver solve = new GeneralSolver();
		for(int i = 0; i < sq.sumRow.length; i++) {
		    if(highest<sq.sumRow[i]) {
		    	highest = sq.sumRow[i];
		    	index = i;
		    	orientation = 'r';
		    }
		}
		
		for(int i = 0; i < sq.sumCol.length; i++) {
		    if(highest<sq.sumCol[i]) {
		    	highest = sq.sumCol[i];
		    	index = i;
		    	orientation = 'c';
		    }
		}
		
		for(int i = 0; i < sq.sumDiag.length; i++) {
		    if(highest<sq.sumDiag[i]) {
		    	highest = sq.sumDiag[i];
		    	index = i;
		    	orientation = 'd';
		    }
		}
		
		if(orientation != 'a') {
			int remainingSum = sq.mConst - highest;
			int remainingNums = 0;
			int[] set = new int[sq.numEmpty];
			Set<List<Integer>> dict = new HashSet<List<Integer>>();
			int j = 0;
			for(int i=0; i<remainingSum; i++) {
				if(!sq.dict.contains(i)) {
					set[j++] = i;
				}
			}
			if(orientation == 'r') {
				for(int i=0; i<size; i++) {
					if(sq.square[index][i] == MagicSquare.INITNUM) {
						remainingNums++;
					}
				}
			}
			else if(orientation == 'c') {
				for(int i=0; i<size; i++) {
					if(sq.square[i][index] == MagicSquare.INITNUM) {
						remainingNums++;
					}
				}
				
			}
			else if(orientation == 'd') {
				if(index == 0) {
					for(int i=0; i<size; i++) {
						if(sq.square[i][i] == MagicSquare.INITNUM) {
							remainingNums++;
						}
					}
				}
				else if(index == 1) {
					for(int i=0; i<size; i++) {
						if(sq.square[size-i-1][i] == MagicSquare.INITNUM) {
							remainingNums++;
						}
					}
				}
			}
			ArrayList<MagicSquare> squares = new ArrayList<MagicSquare>();
			int[] tup = new int[remainingNums];
			subsetSum(set, tup, remainingNums, 0, 0, 0, remainingSum, dict);
			for(List<Integer> list: dict) {
				ArrayList<ArrayList<Integer>> combinations = tryAllComb(list,list.size());
				if(orientation == 'r') {
					for(int i=0; i<combinations.size(); i++) {
						int c=0;
						MagicSquareState newSq = sq.copy();
						for(int k=0; k<size; k++) {
							if(newSq.square[index][k] == MagicSquare.INITNUM) {
								newSq.square[index][k] = combinations.get(i).get(c++);
							}
						}
						solve.analyze(newSq);
						squares.add(newSq);
					}
				}
				else if(orientation == 'c') {
					for(int i=0; i<combinations.size(); i++) {
						int c=0;
						MagicSquareState newSq = sq.copy();
						for(int k=0; k<size; k++) {
							if(newSq.square[k][index] == MagicSquare.INITNUM) {
								newSq.square[k][index] = combinations.get(i).get(c++);
							}
						}
						solve.analyze(newSq);
						squares.add(newSq);
					}
					
				}
				else if(orientation == 'd') {
					for(int i=0; i<combinations.size(); i++) {
						int c=0;
						MagicSquareState newSq = sq.copy();
						for(int k=0; k<size; k++) {
							if(index == 0 && sq.square[k][k] == MagicSquare.INITNUM) {
								newSq.square[k][k] = combinations.get(i).get(c++);
							}
							else if(index == 1 && sq.square[size-k-1][k] == MagicSquare.INITNUM) {
								newSq.square[size-k-1][k] = combinations.get(i).get(c++);
							}
						}
						solve.analyze(newSq);
						squares.add(newSq);
					}
				}
			}
		}
		
	}
	
	static ArrayList<ArrayList<Integer>> tryAllComb(List<Integer> nums, int length) {
		ArrayList<ArrayList<Integer>> rtn = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		if(length == 1) {
			arr.add(nums.get(0));
			rtn.add(arr);
		}
		else {
			ArrayList<ArrayList<Integer>> result = tryAllComb(nums, length-1);
			for(int i=length-1; i>=0; i--) {
				//add last number first and then the rest of the result
				arr.add(nums.get(i));
				arr.addAll(result.get(i));
				rtn.add(arr);
				
				//add last number last and then the rest of the result first
				arr = new ArrayList<Integer>();
				arr.addAll(result.get(i));
				arr.add(nums.get(i));
				rtn.add(arr);
			}
		}
		return rtn;
	}
	
	static void subsetSum(int set[], int[] tup, int tupLength, int tupMaxSize, int sum, int ite, int target, Set<List<Integer>> dict) {
		if(tupLength >= tupMaxSize) {
			return;
		}
		if(target == sum) {
			List<Integer> newTup = new ArrayList<Integer>();
			for(int i=0; i<tupLength; i++) {
				newTup.add(tup[i]);
			}
			dict.add(newTup);
			if(ite+1 < set.length && sum-set[ite]+set[ite+1] <= target) {
				subsetSum(set, tup, tupLength-1,tupMaxSize+1, sum-set[ite], ite+1, target, dict);
			}
			return;
		}
		else {
			if(ite < set.length && sum-set[ite]+set[ite+1] <= target) {
				for(int i=ite; i<set.length; i++) {
					tup[tupLength] = set[i];
					if(sum+set[i] <= target) {
						subsetSum(set, tup, tupLength+1,tupMaxSize-1,sum+set[i], i+1, target, dict);
					}
				}
			}
		}
	}
	
	public void analyze(MagicSquareState sq) {
		//sum rows and cols
		int size = sq.n;
		sq.numEmpty = 0;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				sq.sumRow[i] += sq.square[i][j];
				sq.sumCol[i] += sq.square[j][i];
				
				//check if used
				//sq.isUsed[sq.square[i][j]] = true;
				
				//keep count of empty 
				if(sq.square[i][j] == MagicSquare.INITNUM) {
					sq.numEmpty++;
				}
			}
		}
		//sum diag
		for(int i = 0; i < size; i++) {
			sq.sumDiag[0] += sq.square[i][i];
			sq.sumDiag[1] += sq.square[i][size-1-i];
		}
	}
	
	public void checkZeros(MagicSquareState sq) {
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
					sq.square[loc[0]][loc[1]] = sq.mConst - sum;
					
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
					sq.square[loc[0]][loc[1]] = sq.mConst - sum;
					
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
				sq.square[loc[0]][loc[0]] = sq.mConst - sum;
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
				sq.square[loc[0]][size-loc[0]-1] = sq.mConst - sum;
				newInsert = true;
			}
		}//if filled in any square, repeat
	}
	
	public static MagicSquareState loadCSV() throws FileNotFoundException {
		Scanner s = new Scanner(new File("puzzle.csv"));
		String csv = "";
		
		while(s.hasNextLine()){
			csv += s.nextLine() + "\n";
		}
		
		String[] temp1 = csv.split("\n");
		MagicSquareState rtn = new MagicSquareState(temp1.length);
		
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
	
}
