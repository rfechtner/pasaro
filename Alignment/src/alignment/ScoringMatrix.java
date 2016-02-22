package alignment;

public class ScoringMatrix {
	private int[][] scoringMatrix;

	public ScoringMatrix(String matrix) {
		
	}
	
	public void setMatrix(String name) {
		
	}
	
	public int getScore(char a, char b) {
		//zu testzwecken
		if(a == b) {
			return 3;
		}else {
			return -2;
		}
	}
}
