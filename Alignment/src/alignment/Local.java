package alignment;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class Local extends Alignment{

	public Local(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}

	public void calcMatrix(int i, int j) {		
				int gap1 = aMatrix[i][j-1] + gapFunction.calcPenalty(1);
				int gap2 = aMatrix[i-1][j] + gapFunction.calcPenalty(1);
				int match = aMatrix[i-1][j-1] + scoringMatrix.getScore(sequence.getSequenceA()[i-1], sequence.getSequenceB()[j-1]);
				aMatrix[i][j] = Math.max(0, Math.max(match, Math.max(gap1, gap2)));
		
	}
	
	public String checkScore() {
		float score = 0;
		int start = 0;
		int stop = finalAlignment.getSequenceA().length;
		char[] check;
		for (int i = 0; i < stop; i++) {
			if(finalAlignment.getSequenceA()[i] == '-'
					|| finalAlignment.getSequenceB()[i] == '-') {
						start++;
					}else {
						break;
					}
		}
		for (int i = stop-1; i > 0; i--) {
			if(finalAlignment.getSequenceA()[i] == '-'
					|| finalAlignment.getSequenceB()[i] == '-') {
						stop--;
					}else {
						break;
					}
		}
		for (int i = start; i < stop; i++) {
			if ((check = finalAlignment.getSequenceA())[i] == '-'
					|| (check = finalAlignment.getSequenceB())[i] == '-') {
				int gaps = 1;
				while (i + 1 < check.length && check[i + 1] == '-') {
					i++;
					gaps++;
				}
				score += gapFunction.calcPenalty(gaps);
			} else {
				score += scoringMatrix.getScore(
						finalAlignment.getSequenceA()[i],
						finalAlignment.getSequenceB()[i]);
			}
		}
		return String.format("%.4f", (score / 1000f));
	}
	
	public void make() {
		super.make();
		int lengthX = aMatrix.length - 1;
		int lengthY = aMatrix[0].length - 1; 
		int i = lengthX;
		int j = lengthY;
		float maxScore = aMatrix[0][0]; 			
		for (int x = 0; x < lengthX+1; x++) {
			for (int y = 0; y < lengthY+1; y++) {
				if(aMatrix[x][y] > maxScore) {
					maxScore = aMatrix[x][y];
					i = x;
					j = y;
				}
			}
		}
		backtrack(i ,j, 0);
	}
}
