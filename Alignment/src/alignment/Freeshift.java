package alignment;

public class Freeshift extends Alignment{

	public Freeshift(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}
	
	public void make() {
		super.make();
		int lengthX = getaMatrix().length - 1;
		int lengthY = getaMatrix()[0].length - 1; 
		int i = lengthX;
		int j = lengthY;
		int maxScore = getaMatrix()[i][j]; 
		for (int x = lengthX - 1; x > 0; x--) {
			if(getaMatrix()[x][lengthY] > maxScore) {
				maxScore = getaMatrix()[x][lengthY];
				i = x;
				j = lengthY;
			}
		}
		for (int x = lengthY - 1; x > 0; x--) {
			if(getaMatrix()[lengthX][x] > maxScore) {
				maxScore = getaMatrix()[lengthX][x];
				i = lengthX;
				j = x;
			}
		}
		backtrack(i,j);
		printMatrix();
	}
	
	public void backtrack(int i, int j) {
		String a = "";
		String b = "";
		if(i != getSequence().getSequenceA().length) {
			for(int x = i; x < getSequence().getSequenceA().length; x++) {
				a += getSequence().getSequenceA()[x];
				b += "-";
			}
		}
		if(j != getSequence().getSequenceB().length) {
			for(int x = j; x < getSequence().getSequenceB().length; x++) {
				a += "-";
				b += getSequence().getSequenceB()[x];
			}
		}
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
		}else if (j != 0){
			for(int x = j-1; x >= 0; x--) {
				a = "-" + a;
				b = getSequence().getSequenceB()[x] + b;
			}
		}
		System.out.println(a);
		System.out.println(b);
	}
}