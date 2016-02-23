package alignment;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public abstract class Alignment {
	protected SequencePair sequence;
	protected SequencePair finalAlignment;
	protected int finalScore;
	protected GapFunction gapFunction;
	protected ScoringMatrix scoringMatrix;
	protected int[][] aMatrix;
	protected int[][] iMatrix;
	protected int[][] dMatrix;

	public Alignment(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		this.sequence = sequence;
		this.gapFunction = gapFunction;
		this.scoringMatrix = scoringMatrix;
		
//		iMatrix = new int[sequence.getSequenceA().length()+1][sequence.getSequenceB().length()+1];
//		dMatrix = new int[sequence.getSequenceA().length()+1][sequence.getSequenceB().length()+1];
	}

//	public int[][] aMatrix {
//		return aMatrix;
//	}
//	
//	public int[][] getiMatrix() {
//		return iMatrix;
//	}
//	
//	public int[][] getdMatrix() {
//		return dMatrix;
//	}
//	
//	public SequencePair getSequence() {
//		return sequence;
//	}
//
//	public SequencePair getFinalAlignment() {
//		return finalAlignment;
//	}
//	
//	public int getFinalScore() {
//		return finalScore;
//	}
//
//
//	public GapFunction getGapFunction() {
//		return gapFunction;
//	}
//
//
//	public ScoringMatrix getScoringMatrix() {
//		return scoringMatrix;
//	}

	public void make() {
		initMatrix();
		fillMatrix();
	}
	
	public int checkScore(int i, int j) {
		int gap1 = checkScore(i, j) + gapFunction.calcPenalty(false);
		int gap2 = checkScore(i, j) + gapFunction.calcPenalty(false);
		int match = checkScore(i, j) + scoringMatrix.getScore(sequence.getSequenceA()[i-1], sequence.getSequenceB()[j-1]);
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
		aMatrix = new int[sequence.getSequenceA().length + 1][sequence.getSequenceB().length + 1];
	}

	public void fillMatrix() { // weil ja für global und freeshift gleich, dann bei goto und lokal überschreiben
		for (int i = 1; i < aMatrix.length; i++) {
			for (int j = 1; j < aMatrix[0].length; j++) {
				int gap1 = aMatrix[i][j-1] + gapFunction.calcPenalty(false);
				int gap2 = aMatrix[i-1][j] + gapFunction.calcPenalty(false);
				int match = aMatrix[i-1][j-1] + scoringMatrix.getScore(sequence.getSequenceA()[i-1], sequence.getSequenceB()[j-1]);
				aMatrix[i][j] = Math.max(match, Math.max(gap1, gap2));
			}
		}
	}

	public void backtrack(int i, int j) {

	}
}
