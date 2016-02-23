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
		getaMatrix()[0][0] = 0;
		for (int i = 1; i < getaMatrix().length; i++) {
			getaMatrix()[i][0] = getaMatrix()[i-1][0] + getGapFunction().calcPenalty(false); 
		}
		for (int i = 1; i < getaMatrix()[0].length; i++) {
			getaMatrix()[0][i] = getaMatrix()[0][i-1] + getGapFunction().calcPenalty(false);
		}
	}	
	
	public void make() {
		super.make();
		backtrack(getaMatrix().length - 1, getaMatrix()[0].length - 1);
	}
	
	public void backtrack(int i, int j) {
		String a = "";
		String b = "";
		while(i != 0 && j != 0) {
			if(getaMatrix()[i][j] == getaMatrix()[i-1][j] + getGapFunction().calcPenalty(false)){
				i = i-1; 
				a = getSequence().getSequenceA()[i] + a;
				b = "-" + b;
			}else if(getaMatrix()[i][j] == getaMatrix()[i][j-1] + getGapFunction().calcPenalty(false)) {
				j = j-1;
				a = "-" + a;
				b = getSequence().getSequenceB()[j] + b;
			}else {
				i = i-1;
				j = j-1;
				a = getSequence().getSequenceA()[i] + a;
				b = getSequence().getSequenceB()[j] + b;
			}
		}
		if(i != 0) {
			for(int x = i-1; x >= 0; x--) {
				a = getSequence().getSequenceA()[x] + a;
				b = "-" + b;
			}
		}
		if (j != 0){
			for(int x = j-1; x >= 0; x--) {
				a = "-" + a;
				b = getSequence().getSequenceB()[x] + b;
			}
		}
		System.out.println(a);
		System.out.println(b);
	}
}
