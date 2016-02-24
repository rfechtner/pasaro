package alignment;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class GotohFreeshift extends Freeshift{

	public GotohFreeshift(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}

	public void initMatrix() { 
		super.initMatrix();
		initID();
	}
	
	public void calcMatrix(int i, int j) {
				calcGotoh(i, j);
	}

	public void backtrack(int i, int j, int end) {
		gotohBacktrack(i, j, end);
	}
}
