package alignment;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class GotohLocal extends Local{

	public GotohLocal(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}

	public void initMatrix() { 
		super.initMatrix();
		super.initID();
	}
	
	public void calcGotoh(int i, int j) {
		float opI = aMatrix[i][j-1] + gapFunction.calcPenalty(1);
		float extI = iMatrix[i][j-1] + gapFunction.getGapExtend();
		iMatrix[i][j] = Math.max(opI, extI);
		float opD = aMatrix[i-1][j] + gapFunction.calcPenalty(1);
		float extD = dMatrix[i-1][j] + gapFunction.getGapExtend();
		dMatrix[i][j] = Math.max(opD, extD);
		float match = aMatrix[i-1][j-1] + scoringMatrix.getScore(sequence.getSequenceA()[i-1], sequence.getSequenceB()[j-1]);
		float ins = iMatrix[i][j];
		float del = dMatrix[i][j];
		aMatrix[i][j] = Math.max(0, Math.max(match, Math.max(ins, del)));
	}
	
	public void fillMatrix() {
		for (int i = 1; i < aMatrix.length; i++) {
			for (int j = 1; j < aMatrix[0].length; j++) {
				calcGotoh(i, j);
			}
		}
	}

	public void backtrack() {
		
	}
}
