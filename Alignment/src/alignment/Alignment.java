package alignment;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public abstract class Alignment {
	protected SequencePair sequence;
	private SequencePair finalAlignment;
	private int finalScore;
	private GapFunction gapFunction;
	private ScoringMatrix scoringMatrix;
	private int[][] aMatrix;
	private int[][] iMatrix;
	private int[][] dMatrix;

	public Alignment(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		this.sequence = sequence;
		this.gapFunction = gapFunction;
		this.scoringMatrix = scoringMatrix;
		
//		iMatrix = new int[sequence.getSequenceA().length()+1][sequence.getSequenceB().length()+1];
//		dMatrix = new int[sequence.getSequenceA().length()+1][sequence.getSequenceB().length()+1];
	}

	public int[][] getaMatrix() {
		return aMatrix;
	}
	
	public int[][] getiMatrix() {
		return iMatrix;
	}
	
	public int[][] getdMatrix() {
		return dMatrix;
	}
	
	public SequencePair getSequence() {
		return sequence;
	}

	public SequencePair getFinalAlignment() {
		return finalAlignment;
	}
	
	public int getFinalScore() {
		return finalScore;
	}


	public GapFunction getGapFunction() {
		return gapFunction;
	}


	public ScoringMatrix getScoringMatrix() {
		return scoringMatrix;
	}

	public void make() {
		initMatrix();
		fillMatrix();
	}
	
	public int checkScore(int i, int j) {
		int gap1 = checkScore(i, j) + getGapFunction().calcPenalty(false);
		int gap2 = checkScore(i, j) + getGapFunction().calcPenalty(false);
		int match = checkScore(i, j) + getScoringMatrix().getScore(getSequence().getSequenceA()[i-1], getSequence().getSequenceB()[j-1]);
		return Math.max(match, Math.max(gap1, gap2));
	}
	
	public void printMatrix() {
		for (int i = 0; i < getaMatrix()[0].length; i++) {
			for (int j = 0; j < getaMatrix().length; j++) {
				System.out.print(" " + getaMatrix()[j][i]);
			}
			System.out.print("\n");
		}
	}
	
	public void initMatrix() {
		aMatrix = new int[sequence.getSequenceA().length + 1][sequence.getSequenceB().length + 1];
	}

	public void fillMatrix() { // weil ja für global und freeshift gleich, dann bei goto und lokal überschreiben
		for (int i = 1; i < getaMatrix().length; i++) {
			for (int j = 1; j < getaMatrix()[0].length; j++) {
				int gap1 = getaMatrix()[i][j-1] + getGapFunction().calcPenalty(false);
				int gap2 = getaMatrix()[i-1][j] + getGapFunction().calcPenalty(false);
				int match = getaMatrix()[i-1][j-1] + getScoringMatrix().getScore(getSequence().getSequenceA()[i-1], getSequence().getSequenceB()[j-1]);
				getaMatrix()[i][j] = Math.max(match, Math.max(gap1, gap2));
			}
		}
	}

	public void backtrack(int i, int j) {

	}
}
