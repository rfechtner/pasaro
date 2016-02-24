package alignment;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class GotohGlobal extends Global {

	public GotohGlobal(SequencePair sequence, GapFunction gapFunction,
			ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}

	public void initMatrix() {
		super.initMatrix();
		super.initID();
	}

	public void printMatrix() {
		super.printMatrix();
		System.out.println("\n");
		for (int i = 0; i < aMatrix[0].length; i++) {
			for (int j = 0; j < aMatrix.length; j++) {
				System.out.print(" " + iMatrix[j][i]);
			}
			System.out.print("\n");
		}
		System.out.println("\n");
		for (int i = 0; i < aMatrix[0].length; i++) {
			for (int j = 0; j < aMatrix.length; j++) {
				System.out.print(" " + dMatrix[j][i]);
			}
			System.out.print("\n");
		}
	}

	public void fillMatrix() {
		for (int i = 1; i < aMatrix.length; i++) {
			for (int j = 1; j < aMatrix[0].length; j++) {
				calcGotoh(i, j);
			}
		}
		printMatrix();
	}

	public void backtrack() {

	}
}
