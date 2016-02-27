package alignment;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class Freeshift extends Alignment{

	public Freeshift(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}
	
	public void make() {
		super.make();
		int lengthX = aMatrix.length - 1;
		int lengthY = aMatrix[0].length - 1; 
		int i = lengthX;
		int j = lengthY;
		int maxScore = aMatrix[i][j]; 
		for (int x = lengthX; x > 0; x--) {
			if(aMatrix[x][lengthY] > maxScore) {
				maxScore = aMatrix[x][lengthY];
				i = x;
				j = lengthY;
			}
		}
		for (int x = lengthY; x > 0; x--) {
			if(aMatrix[lengthX][x] > maxScore) {
				maxScore = aMatrix[lengthX][x];
				i = lengthX;
				j = x;
			}
		}
		backtrack(i,j, Integer.MAX_VALUE);
	}
}