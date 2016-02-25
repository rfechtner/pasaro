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
		int opI = aMatrix[i][j-1] + gapFunction.calcPenalty(1);
		int extI = iMatrix[i][j-1] + gapFunction.getGapExtend();
		iMatrix[i][j] = Math.max(opI, extI);
		int opD = aMatrix[i-1][j] + gapFunction.calcPenalty(1);
		int extD = dMatrix[i-1][j] + gapFunction.getGapExtend();
		dMatrix[i][j] = Math.max(opD, extD);
		int match = aMatrix[i-1][j-1] + scoringMatrix.getScore(sequence.getSequenceA()[i-1], sequence.getSequenceB()[j-1]);
		int ins = iMatrix[i][j];
		int del = dMatrix[i][j];
		aMatrix[i][j] = Math.max(0, Math.max(match, Math.max(ins, del)));
	}
	
	public void calcMatrix(int i, int j) {
		calcGotoh(i, j);
}

	public void backtrack(int i, int j, int end) {
		gotohBacktrack(i, j, end);
	}
}
