package alignment;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class GotohGlobal extends Global {

	public GotohGlobal(SequencePair sequence, GapFunction gapFunction,
			ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}

	public void printMatrix() {
		super.printMatrix();
		for (int i = 0; i < iMatrix[0].length; i++) {
			String row = "";
			for (int j = 0; j < iMatrix.length; j++) {
				row += String.format("%20d", iMatrix[j][i]);
			}
			System.out.println(row);
		}
		for (int i = 0; i < dMatrix[0].length; i++) {
			String row = "";
			for (int j = 0; j < dMatrix.length; j++) {
				row += String.format("%20d", dMatrix[j][i]);
			}
			System.out.println(row);
		}
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
