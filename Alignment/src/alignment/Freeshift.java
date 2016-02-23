package alignment;

public class Freeshift extends Alignment{

	public Freeshift(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}
	
	public void make() {
		super.make();
		int lengthX = getaMatrix().length - 1;
		int lengthY = getaMatrix()[0].length - 1; 
		int i = lengthX;
		int j = lengthY;
		int maxScore = getaMatrix()[i][j]; 
		for (int x = lengthX - 1; x > 0; x--) {
			if(getaMatrix()[x][lengthY] > maxScore) {
				maxScore = getaMatrix()[x][lengthY];
				i = x;
				j = lengthY;
			}
		}
		for (int x = lengthY - 1; x > 0; x--) {
			if(getaMatrix()[lengthX][x] > maxScore) {
				maxScore = getaMatrix()[lengthX][x];
				i = lengthX;
				j = x;
			}
		}
		
		
		backtrack(i,j);
		printMatrix();
	}
}