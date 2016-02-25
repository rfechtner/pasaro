package alignment;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class Freeshift extends Alignment{

	public Freeshift(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}
	
	public float checkScore() {
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
		return score / 1000f;
	}
	
	public void make() {
		super.make();
		int lengthX = aMatrix.length - 1;
		int lengthY = aMatrix[0].length - 1; 
		int i = lengthX;
		int j = lengthY;
		int maxScore = aMatrix[i][j]; 
		for (int x = lengthX; x > 0; x--) {
			if(aMatrix[x][lengthY] > maxScore) {
				maxScore = aMatrix[x][lengthY];
				i = x;
				j = lengthY;
			}
		}
		for (int x = lengthY; x > 0; x--) {
			if(aMatrix[lengthX][x] > maxScore) {
				maxScore = aMatrix[lengthX][x];
				i = lengthX;
				j = x;
			}
		}
		backtrack(i,j, Integer.MAX_VALUE);
	}
}