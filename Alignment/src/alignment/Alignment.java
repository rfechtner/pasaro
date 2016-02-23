package alignment;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public abstract class Alignment {
	protected SequencePair sequence;
	protected SequencePair finalAlignment;
	protected GapFunction gapFunction;
	protected ScoringMatrix scoringMatrix;
	protected float[][] aMatrix;
	protected float[][] iMatrix;
	protected float[][] dMatrix;

	public Alignment(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		this.sequence = sequence;
		this.gapFunction = gapFunction;
		this.scoringMatrix = scoringMatrix;
		
//		iMatrix = new int[sequence.getSequenceA().length()+1][sequence.getSequenceB().length()+1];
//		dMatrix = new int[sequence.getSequenceA().length()+1][sequence.getSequenceB().length()+1];
	}

	public void make() {
		initMatrix();
		fillMatrix();
	}
	
	public float checkScore(int i, int j) {
		float gap1 = checkScore(i, j) + gapFunction.calcPenalty(1);
		float gap2 = checkScore(i, j) + gapFunction.calcPenalty(1);
		float match = checkScore(i, j) + scoringMatrix.getScore(sequence.getSequenceA()[i-1], sequence.getSequenceB()[j-1]);
		return Math.max(match, Math.max(gap1, gap2));
	}
	
	public void printMatrix() {
		for (int i = 0; i < aMatrix[0].length; i++) {
			for (int j = 0; j < aMatrix.length; j++) {
				System.out.print(" " + aMatrix[j][i]);
			}
			System.out.print("\n");
		}
	}
	
	public void initMatrix() {
		aMatrix = new float[sequence.getSequenceA().length + 1][sequence.getSequenceB().length + 1];
	}

	public void initID() {
		iMatrix = new float[sequence.getSequenceA().length + 1][sequence.getSequenceB().length + 1];
		dMatrix = new float[sequence.getSequenceA().length + 1][sequence.getSequenceB().length + 1];
		for (int i = 1; i < iMatrix.length; i++) {
			iMatrix[i][0] = Integer.MIN_VALUE;
		}
		for (int j = 1; j < dMatrix[0].length; j++) {
			dMatrix[0][j] = Integer.MIN_VALUE;
		}
	}
	
	public void fillMatrix() { // weil ja für global und freeshift gleich, dann bei goto und lokal überschreiben
		for (int i = 1; i < aMatrix.length; i++) {
			for (int j = 1; j < aMatrix[0].length; j++) {
				float gap1 = aMatrix[i][j-1] + gapFunction.calcPenalty(1);
				float gap2 = aMatrix[i-1][j] + gapFunction.calcPenalty(1);
				float match = aMatrix[i-1][j-1] + scoringMatrix.getScore(sequence.getSequenceA()[i-1], sequence.getSequenceB()[j-1]);
				aMatrix[i][j] = Math.max(match, Math.max(gap1, gap2));
			}
		}
	}

	public void backtrack(int i, int j) {

	}
}
