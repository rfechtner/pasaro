package alignment;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class GotohFreeshift extends Freeshift{

	public GotohFreeshift(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}

	public void initMatrix() { 
		super.initMatrix();
		initID();
	}
	
	public void fillMatrix() {
		for (int i = 1; i < aMatrix.length; i++) {
			for (int j = 1; j < aMatrix[0].length; j++) {
				calcGotoh(i, j);
			}
		}
	}

	public void backtrack(int i, int j) {
		String a = "";
		String b = "";
		float finalScore = aMatrix[i][j];	
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
			if(aMatrix[i][j] == iMatrix[i][j]) {
				do {
					j--;
					a = "-" + a;
					b = sequence.getSequenceB()[j] + b;					
				}while(aMatrix[i][j] + gapFunction.calcPenalty(1) != iMatrix[i][j+1]);
			}else if(aMatrix[i][j] == dMatrix[i][j]) {
				do {
					i--;
					a = sequence.getSequenceA()[i] + a;
					b = "-" + b;
				}while(aMatrix[i][j] + gapFunction.calcPenalty(1) != dMatrix[i+1][j]);
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
		SequencePair out = new SequencePair(a, b, sequence.getNameA(), sequence.getNameB(), finalScore);
		finalAlignment = out;
	}
}
