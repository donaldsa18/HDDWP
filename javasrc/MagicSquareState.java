import java.util.HashSet;
import java.util.Set;

public class MagicSquareState extends MagicSquare {
	public int[] sumRow;
	public int[] sumCol;
	public int[] sumDiag = new int[2];
	public int mConst;
	public int[] countRow;
	public int[] countCol;
	public int[] countDiag = new int[2];
	public Set<Integer> dict = new HashSet<Integer>();
	public int numEmpty;
	
	public MagicSquareState(int num) {
		super(num);
		sumRow = new int[n];
		sumCol = new int[n];
		countRow = new int[n];
		countCol = new int[n];
		countDiag = new int[2];
		numEmpty = n*n;
	}

		public MagicSquareState copy() {
			MagicSquareState newSq = new MagicSquareState(n);
			newSq.sumRow = sumRow;
			newSq.sumCol = sumCol;
			newSq.countRow = countRow;
			newSq.countCol = countCol;
			newSq.countDiag = countDiag;
			newSq.dict.addAll(dict);
			return newSq;
		}
		public void setNumber(int i, int j, int num) {
			if(square[i][j] == MagicSquare.INITNUM && num != MagicSquare.INITNUM) {
				numEmpty--;
			}
			square[i][j] = num;
			sumRow[i] += num;
			sumCol[j] += num;
			countRow[i]++;
			countCol[j]++;
			dict.add(num);
		}
		
		public void shuffleSquare() {
			int[][] newSq = new int[n][n];
			int rand = (int)(Math.random()*8);
			for(int i=0;i<n;i++) {
				for(int j=0;j<n;j++) {
					switch(rand) {
						case 0: newSq[i][j] = square[i][j];
								break;
						case 1: newSq[i][j] = square[n-i-1][j];
								break;
						case 2: newSq[i][j] = square[i][n-j-1];
								break;
						case 3: newSq[i][j] = square[n-i-1][n-j-1];
								break;
						case 4: newSq[i][j] = square[j][i];
								break;
						case 5: newSq[i][j] = square[j][n-i-1];
								break;
						case 6: newSq[i][j] = square[n-j-1][i];
								break;
						case 7: newSq[i][j] = square[n-j-1][n-i-1];
								break;
						default:
								break;
					}
				}
			}
			square = newSq;
		}
		public void addToSquare(int add) {
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					square[i][j] += add;
				}
			}
			testSquare();
		}
		
		public boolean testSquare() {
			int sum = 0;
			sumRow = new int[n];
			sumCol = new int[n];
			countRow = new int[n];
			countCol = new int[n];
			countDiag = new int[2];
			boolean rtn = true;
			for(int i=0;i<n;i++) {
				for(int j=0;j<n;j++) {
					if(square[i][j] != MagicSquare.INITNUM && dict.contains(square[i][j])) {
						//System.out.println("Contains two "+square[i][j]+"'s");
						rtn = false;
					}
					dict.add(square[i][j]);
					sum += square[i][j];
					sumRow[i] += square[i][j];
					sumCol[j] += square[i][j];
					if(square[i][j] != INITNUM) {
						countRow[i]++;
						countCol[j]++;
					}
					if(i == j) {
						sumDiag[0] += square[i][j];
						if(square[i][j] != INITNUM) {
							countDiag[0]++;
						}
					}
					if(n-i-1 == j) {
						sumDiag[1] += square[i][j];
						if(square[i][j] != INITNUM) {
							countDiag[1]++;
						}
					}
				}
			}
			mConst = sum/n;
			//System.out.println("cols: "+Arrays.toString(sumCol)+"\nrows: "+Arrays.toString(sumRow)+"\ndiag: "+Arrays.toString(sumDiag));
			for(int i=0;i<n;i++) {
				if(mConst != sumCol[i]) {
					//System.out.println("not equal to "+magicConst+" @ "+i+", check col, "+Arrays.toString(sumCol));
					rtn = false;
				}
				if(mConst != sumRow[i]) {
					//System.out.println("not equal to "+magicConst+" @ "+i+", check row, "+Arrays.toString(sumRow));
					rtn = false;
				}
			}
			if(sumCol[0] != sumDiag[0]) {
				//System.out.println("check 1st diag, "+sumDiag[0]);
				rtn = false;
			}
			if(sumCol[0] != sumDiag[1]) {
				//System.out.println("check 2nd diag, "+sumDiag[1]);
				rtn = false;
			}
			return rtn;
		}
	}