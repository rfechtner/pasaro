package alignment;

public class Global extends Alignment{

	
    public Global(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}

	public void initMatrix() {
		super.initMatrix();
		getaMatrix()[0][0] = 0;
		for (int i = 1; i < getaMatrix().length; i++) {
			getaMatrix()[i][0] = getaMatrix()[i-1][0] + getGapFunction().calcPenalty(false); 
		}
		for (int i = 1; i < getaMatrix()[0].length; i++) {
			getaMatrix()[0][i] = getaMatrix()[0][i-1] + getGapFunction().calcPenalty(false);
		}
	}	
	
	public void make() {
		super.make();
		backtrack(getaMatrix().length - 1, getaMatrix()[0].length - 1);
	}
}
