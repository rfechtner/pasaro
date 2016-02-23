package alignment;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class Local extends Alignment{

	public Local(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}

	public void fillMatrix() {
		for (int i = 1; i < aMatrix.length; i++) {
			for (int j = 1; j < aMatrix[0].length; j++) {
				int gap1 = aMatrix[i][j-1] + gapFunction.calcPenalty(false);
				int gap2 = aMatrix[i-1][j] + gapFunction.calcPenalty(false);
				int match = aMatrix[i-1][j-1] + scoringMatrix.getScore(sequence.getSequenceA()[i-1], sequence.getSequenceB()[j-1]);
				aMatrix[i][j] = Math.max(0, Math.max(match, Math.max(gap1, gap2)));
			}
		}
	}

	public int checkScore(int i, int j) {
		int gap1 = checkScore(i, j) + gapFunction.calcPenalty(false);
		int gap2 = checkScore(i, j) + gapFunction.calcPenalty(false);
		int match = checkScore(i, j) + scoringMatrix.getScore(sequence.getSequenceA()[i-1], sequence.getSequenceB()[j-1]);
		return Math.max(0, Math.max(match, Math.max(gap1, gap2)));
	}
	
	public void make() {
		super.make();
		int lengthX = aMatrix.length - 1;
		int lengthY = aMatrix[0].length - 1; 
		int i = lengthX;
		int j = lengthY;
		int maxScore = aMatrix[0][0]; 		
		for (int x = 0; x < lengthX+1; x++) {
			for (int y = 0; y < lengthY+1; y++) {
				if(aMatrix[x][y] > maxScore) {
					maxScore = aMatrix[x][y];
					i = x;
					j = y;
				}
			}
		}
		System.out.println(maxScore);
//		System.out.println(checkScore(lengthX,lengthY));
		backtrack(i,j);
//		printMatrix();

	}
	
	public void backtrack(int i, int j) { 
		char[] seqA = sequence.getSequenceA();
		String a = "";
		String b = "";
		sequence.setScore(aMatrix[i][j]);
		if(i != sequence.getSequenceA().length) {
			for(int x = i; x < sequence.getSequenceA().length; x++) {
				a += sequence.getSequenceA()[x];
				b += "-";
			}
		}
		if(j != sequence.getSequenceB().length) {
			for(int x = j; x < sequence.getSequenceB().length; x++) {
				a += "-";
				b += sequence.getSequenceB()[x];
			}
		}
		while(aMatrix[i][j] != 0) {
			if(aMatrix[i][j] == aMatrix[i-1][j] + gapFunction.calcPenalty(false)){
				i = i-1; 
				a = sequence.getSequenceA()[i] + a;
				b = "-" + b;
			}else if(aMatrix[i][j] == aMatrix[i][j-1] + gapFunction.calcPenalty(false)) {
				j = j-1;
				a = "-" + a;
				b = sequence.getSequenceB()[j] + b;
			}else {
				i = i-1;
				j = j-1;
				a = sequence.getSequenceA()[i] + a;
				b = sequence.getSequenceB()[j] + b;
			}
		}
		if(i != 0) {
			for(int x = i-1; x >= 0; x--) {
				a = sequence.getSequenceA()[x] + a;
				b = "-" + b;
			}
		}
		if (j != 0){
			for(int x = j-1; x >= 0; x--) {
				a = "-" + a;
				b = sequence.getSequenceB()[x] + b;
			}
		}
		sequence.setSequenceA(a.toCharArray());
		sequence.setSequenceB(b.toCharArray());
	}
}
