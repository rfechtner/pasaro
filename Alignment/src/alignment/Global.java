package alignment;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class Global extends Alignment{

	
    public Global(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}

	public void initMatrix() {
		super.initMatrix();
		aMatrix[0][0] = 0;
		for (int i = 1; i < aMatrix.length; i++) {
			aMatrix[i][0] = aMatrix[i-1][0] + gapFunction.calcPenalty(false); 
		}
		for (int i = 1; i < aMatrix[0].length; i++) {
			aMatrix[0][i] = aMatrix[0][i-1] + gapFunction.calcPenalty(false);
		}
	}	
	
	public void make() {
		super.make();
		backtrack(aMatrix.length - 1, aMatrix[0].length - 1);
	}
	
	public void backtrack(int i, int j) {
		String a = "";
		String b = "";
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
		
	}
}
