package alignment;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class Global extends Alignment{

	
    public Global(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}

    public String checkScore() {
		float score = 0;
		char[] check;
		for (int i = 0; i < finalAlignment.getSequenceA().length; i++) {
			if ((check = finalAlignment.getSequenceA())[i] == '-'
					|| (check = finalAlignment.getSequenceB())[i] == '-') {
				int gaps = 1;
				while (i + 1 < check.length && check[i + 1] == '-') {
					i++;
					gaps++;
				}
				score += gapFunction.calcPenalty(gaps);
			} else {
				score += scoringMatrix.getScore(
						finalAlignment.getSequenceA()[i],
						finalAlignment.getSequenceB()[i]);
			}
		}
		return String.format("%.4f", (score / 1000f));
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
		backtrack(aMatrix.length - 1, aMatrix[0].length - 1, Integer.MIN_VALUE);
	}
	
}
