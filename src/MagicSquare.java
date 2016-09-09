import java.util.Arrays;

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
	
	public MagicSquare(int num) {
		n = num;
		square = new int[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				square[i][j] = -1;
			}
		}
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
}
