package alignment;

public class Local extends Alignment{

	public Local(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix) {
		super(sequence, gapFunction, scoringMatrix);
	}

	public void fillMatrix() {
		for (int i = 1; i < getaMatrix().length; i++) {
			for (int j = 1; j < getaMatrix()[0].length; j++) {
				int gap1 = getaMatrix()[i][j-1] + getGapFunction().calcPenalty(false);
				int gap2 = getaMatrix()[i-1][j] + getGapFunction().calcPenalty(false);
				int match = getaMatrix()[i-1][j-1] + getScoringMatrix().getScore(getSequence().getSequenceA()[i-1], getSequence().getSequenceB()[j-1]);
				getaMatrix()[i][j] = Math.max(0, Math.max(match, Math.max(gap1, gap2)));
			}
		}
	}

	public int checkScore(int i, int j) {
		int gap1 = checkScore(i, j) + getGapFunction().calcPenalty(false);
		int gap2 = checkScore(i, j) + getGapFunction().calcPenalty(false);
		int match = checkScore(i, j) + getScoringMatrix().getScore(getSequence().getSequenceA()[i-1], getSequence().getSequenceB()[j-1]);
		return Math.max(0, Math.max(match, Math.max(gap1, gap2)));
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
	}
	
	public void backtrack(int i, int j) {
		String a = "";
		String b = "";
		while(getaMatrix()[i][j] != 0) {
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
