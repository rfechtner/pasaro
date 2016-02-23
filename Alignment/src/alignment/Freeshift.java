package alignment;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class Freeshift extends Alignment{

	public Freeshift(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
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
		backtrack(i,j);
	}
	
	public void backtrack(int i, int j) {
		String a = "";
		String b = "";
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
		while(i != 0 && j != 0) {
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
		System.out.println(a);
		System.out.println(b);
	}
}