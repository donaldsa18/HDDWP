import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 */

/**
 * @author donaldsa18
 *
 */
public class MagicSquare {
	public int n = 3;
	public int[][] square;
	public static int INITNUM = -1;
	public static int MAX_SIZE = 1000;
	public MagicSquare(int num) {
		n = num;
		square = new int[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				square[i][j] = INITNUM;
			}
		}
	}
	public MagicSquare copy() {
		MagicSquare newSq = new MagicSquare(n);
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				newSq.square[i][j] = square[i][j];
			}
		}
		return newSq;
	}
	public String toString() {
		String rtn = "";
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				rtn += square[i][j]+"\t";
			}
			rtn += "\n";
		}
		return rtn;
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
	}
	
	public boolean testSquare() {
		int[] sumRow = new int[n];
		int[] sumCol = new int[n];
		int[] sumDiag = new int[2];
		int sum = 0;
		int magicConst;
		Set<Integer> dict = new HashSet<Integer>();
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				if(dict.contains(square[i][j])) {
					System.out.println("Contains two "+square[i][j]+"'s");
					return false;
				}
				dict.add(square[i][j]);
				sum += square[i][j];
				sumRow[i] += square[i][j];
				sumCol[j] += square[i][j];
				if(i == j) {
					sumDiag[0] += square[i][j];
				}
				if(n-i-1 == j) {
					sumDiag[1] += square[i][j];
				}
			}
		}
		magicConst = sum/n;
		System.out.println("cols: "+Arrays.toString(sumCol)+"\nrows: "+Arrays.toString(sumRow)+"\ndiag: "+Arrays.toString(sumDiag));
		for(int i=0;i<n;i++) {
			if(magicConst != sumCol[i]) {
				System.out.println("not equal to "+magicConst+" @ "+i+", check col, "+Arrays.toString(sumCol));
				return false;
			}
			if(magicConst != sumRow[i]) {
				System.out.println("not equal to "+magicConst+" @ "+i+", check row, "+Arrays.toString(sumRow));
				return false;
			}
		}
		if(sumCol[0] != sumDiag[0]) {
			System.out.println("check 1st diag, "+sumDiag[0]);
			return false;
		}
		if(sumCol[0] != sumDiag[1]) {
			System.out.println("check 2nd diag, "+sumDiag[1]);
			return false;
		}
		return true;
	}
	
	public String toPuzzle(int dif){
        String result = "";
        double delChance = (double)this.n * (double)dif / 9.0; //or 10 maybe?
        
         for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(Math.random() * this.n > delChance) {
                    result += this.square[i][j]+"\t";
                } else {
                    result += "\t";
                }
            }
            result += "\n";
        }
        
       return result;
    }
}
