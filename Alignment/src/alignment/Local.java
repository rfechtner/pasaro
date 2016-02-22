package alignment;

public class Local extends Alignment{

	public Local(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}

//	public void initMatrix() {
//		for (int i = 0; i < getAidmatrices().getaMatrix().length; i++) {
//			getAidmatrices().getaMatrix()[i][0] = 0;
//		}
//		for (int i = 0; i < getAidmatrices().getaMatrix()[0].length; i++) {
//			getAidmatrices().getaMatrix()[0][i] = 0;
//		}
//	}

	public void fillMatrix() {
		for (int i = 1; i < getaMatrix().length; i++) {
			for (int j = 1; j < getaMatrix()[0].length; j++) {
				int gap1 = getaMatrix()[i][j-1] + getGapFunction().calcPenalty(false);
				int gap2 = getaMatrix()[i-1][j] + getGapFunction().calcPenalty(false);
				int match = getaMatrix()[i-1][j-1] + getScoringMatrix().getScore(getSequence().getSequenceA().charAt(i-1), getSequence().getSequenceB().charAt(j-1));
				getaMatrix()[i][j] = Math.max(0, Math.max(match, Math.max(gap1, gap2)));
			}
		}
	}

	public void backtrack() {
	
	}
}
