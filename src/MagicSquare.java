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
	public static int INITNUM = -1;
	public MagicSquare(int num) {
		n = num;
		square = new int[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				square[i][j] = INITNUM;
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
	/*public static MagicSquare shuffleSquare(MagicSquare sq) {
		MagicSquare newSquare = new MagicSquare()
		if(sq.square != null && sq.square[0] != null && sq.square.length == sq.square[0].length) {
			for(int i=0;i<square.length;i++) {
				for(int j=0;j<square[0].length;j++) {
					
				}
			}
		}
		return null;
	}*/
}
