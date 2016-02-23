package alignment;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class GotohFreeshift extends Freeshift{

	public GotohFreeshift(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}

	public void fillMatrix() {
		
	}

	public void backtrack() {
	
	}
}
