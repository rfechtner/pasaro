package alignment;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class Global extends Alignment{

	
    public Global(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}

	public void initMatrix() {
		super.initMatrix();
		for (int i = 1; i < aMatrix.length; i++) {
			aMatrix[i][0] = gapFunction.calcPenalty(i); 
		}
		for (int i = 1; i < aMatrix[0].length; i++) {
			aMatrix[0][i] = gapFunction.calcPenalty(i);
		}
	}	
	
	public void make() {
		super.make();
		backtrack(aMatrix.length - 1, aMatrix[0].length - 1, Integer.MAX_VALUE);
	}
	
}
