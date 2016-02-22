package alignment;

public abstract class Alignment {
	private SequencePair[] sequences;
	private SequencePair[] finalAlignment;
	private int[] finalScores;
	private GapFunction gapFunction;
	private ScoringMatrix scoringMatrix;
	private AIDmatrices aidmatrices;

	public Alignment(SequencePair[] sequences, GapFunction gapFunction, ScoringMatrix scoringMatrix, AIDmatrices aidmatrices) {
		this.sequences = sequences;
		this.gapFunction = gapFunction;
		this.scoringMatrix = scoringMatrix;
		this.aidmatrices = aidmatrices;
	}

	public SequencePair[] getSequences() {
		return sequences;
	}

	public SequencePair[] getFinalAlignment() {
		return finalAlignment;
	}
	
	public int[] getFinalScores() {
		return finalScores;
	}


	public GapFunction getGapFunction() {
		return gapFunction;
	}


	public ScoringMatrix getScoringMatrix() {
		return scoringMatrix;
	}


	public AIDmatrices getAidmatrices() {
		return aidmatrices;
	}

	public void make() {
		initMatrix();
		fillMatrix();
		backtrack();
	}
	
	public void initMatrix() {
		
	}

	public void fillMatrix() {
	
	}

	public void backtrack() {
	
	}
}
