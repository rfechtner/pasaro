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

	public Alignment(SequencePair sequence, GapFunction gapFunction,
			ScoringMatrix scoringMatrix) {
		this.sequence = sequence;
		this.gapFunction = gapFunction;
		this.scoringMatrix = scoringMatrix;
	}

	public void make() {
		initMatrix();
		fillMatrix();
	}

	public float checkScore() {
		float score = 0;
		char[] check;
		for (int i = 0; i < finalAlignment.getSequenceA().length; i++) {
			if((check = finalAlignment.getSequenceA())[i] == '-'|| (check = finalAlignment.getSequenceB())[i] == '-') {
				int gaps = 1;		
				while(i+1 < check.length && check[i+1] == '-') { 
					i++;
					gaps++;
				}
				score += gapFunction.calcPenalty(gaps);
			}else {
				score += scoringMatrix.getScore(finalAlignment.getSequenceA()[i], finalAlignment.getSequenceB()[i]);
			}
		}
		return score;
	}
	
	public float recScore(int i, int j) {
		float gap1; 
		float gap2;
		float match;
		if(i == 0 && j == 0) {
			return 0;
		}else {
			if(i == 0) {
				gap1 = Integer.MIN_VALUE;
			}else{
				gap1 = recScore(i-1, j) + gapFunction.calcPenalty(1);
			}
			if(j == 0) {
				gap2 = Integer.MIN_VALUE;
			}else {
				gap2 = recScore(i, j-1) + gapFunction.calcPenalty(1);
			}
			if(i != 0 && j != 0) {
				match = recScore(i-1, j-1) + scoringMatrix.getScore(sequence.getSequenceA()[i-1], sequence.getSequenceB()[j-1]);
			} else {
				match = Integer.MIN_VALUE;
			}
		}
		return Math.max(match, Math.max(gap1, gap2));
	}

	public void calcGotoh(int i, int j) {
		float opI = aMatrix[i][j-1] + gapFunction.calcPenalty(1);
		float extI = iMatrix[i][j-1] + gapFunction.getGapExtend();
		iMatrix[i][j] = Math.max(opI, extI);
		float opD = aMatrix[i-1][j] + gapFunction.calcPenalty(1);
		float extD = dMatrix[i-1][j] + gapFunction.getGapExtend();
		dMatrix[i][j] = Math.max(opD, extD);
		float match = aMatrix[i-1][j-1] + scoringMatrix.getScore(sequence.getSequenceA()[i-1], sequence.getSequenceB()[j-1]);
		float ins = iMatrix[i][j];
		float del = dMatrix[i][j];
		aMatrix[i][j] = Math.max(match, Math.max(ins, del));
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
		aMatrix = new float[sequence.getSequenceA().length + 1][sequence
				.getSequenceB().length + 1];
	}

	public void initID() {
		iMatrix = new float[sequence.getSequenceA().length + 1][sequence
				.getSequenceB().length + 1];
		dMatrix = new float[sequence.getSequenceA().length + 1][sequence
				.getSequenceB().length + 1];
		for (int i = 1; i < iMatrix.length; i++) {
			iMatrix[i][0] = Integer.MIN_VALUE;
		}
		for (int j = 1; j < dMatrix[0].length; j++) {
			dMatrix[0][j] = Integer.MIN_VALUE;
		}
	}

	public void fillMatrix() {
		for (int i = 1; i < aMatrix.length; i++) {
			for (int j = 1; j < aMatrix[0].length; j++) {
				float gap1 = aMatrix[i][j - 1] + gapFunction.calcPenalty(1);
				float gap2 = aMatrix[i - 1][j] + gapFunction.calcPenalty(1);
				float match = aMatrix[i - 1][j - 1]
						+ scoringMatrix.getScore(
								sequence.getSequenceA()[i - 1],
								sequence.getSequenceB()[j - 1]);
				aMatrix[i][j] = Math.max(match, Math.max(gap1, gap2));
			}
		}
	}

	public SequencePair getFinalAlignment() {
		return finalAlignment;
	}
}
