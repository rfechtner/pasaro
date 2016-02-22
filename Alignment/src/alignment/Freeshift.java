package alignment;

public class Freeshift extends Alignment{

	public Freeshift(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
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

	public void backtrack() {
		for (int j = 0; j < getaMatrix()[0].length; j++) {
			for (int i = 0; i < getaMatrix().length; i++) {
				System.out.print(getaMatrix()[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
}
