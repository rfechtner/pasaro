package alignment;

public class Global extends Alignment{

	
    public Global(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}

	public void initMatrix() {
		getaMatrix()[0][0] = 0;
		for (int i = 1; i < getaMatrix().length; i++) {
			getaMatrix()[i][0] = getaMatrix()[i-1][0] + getGapFunction().calcPenalty(false); 
		}
		for (int i = 1; i < getaMatrix()[0].length; i++) {
			getaMatrix()[0][i] = getaMatrix()[0][i-1] + getGapFunction().calcPenalty(false);
		}
	}	

	public void backtrack() {
		for (int j = 0; j < getaMatrix()[0].length; j++) {
			for (int i = 0; i < getaMatrix().length; i++) {
				System.out.print(getaMatrix()[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
}
