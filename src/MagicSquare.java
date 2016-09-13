import java.util.Random;

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
	public static int INITNUM = 0;
	public static int MAX_SIZE = 1000;
	
	public MagicSquare(int num) {
		n = num;
		square = new int[n][n];
		/*for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				square[i][j] = INITNUM;
			}
		}*/
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
	
	public String toPuzzle(int dif){
        String result = "";
        int[] randArray = new int[n*n];
        Random rand = new Random();
        for(int i=0;i<randArray.length;i++) {
        	randArray[i] = i;
        }
        for(int i=0;i<randArray.length;i++) {
        	int temp = randArray[i];
        	int randInd = rand.nextInt(randArray.length);
        	randArray[i] = randArray[randInd];
        	randArray[randInd] = temp;
        }
        double delChance = (double)this.n * (double)dif / 9.0; //or 10 maybe?
        
         for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(Math.random() * this.n > delChance) {
                    result += this.square[i][j];
                }
                result += "\t";
            }
            result += "\n";
        }
        
       return result;
    }
}
