package alignmentUtils;

public class SequencePair {
	private char[] sequenceA;
	private char[] sequenceB;
	private String nameA;
	private String nameB;
	private float score;
	
	public SequencePair(String sequenceA, String sequenceB, String nameA, String nameB) {
		this.sequenceA = sequenceA.toCharArray();
		this.sequenceB = sequenceB.toCharArray(); 
		this.nameA = nameA;
		this.nameB = nameB;
	}
	
	public SequencePair(String sequenceA, String sequenceB, String nameA, String nameB, float score) {
		this.sequenceA = sequenceA.toCharArray();
		this.sequenceB = sequenceB.toCharArray();
		this.nameA = nameA;
		this.nameB = nameB;
		this.score = score;
	}

	public char[] getSequenceA() {
		return sequenceA;
	}
	
	public char[] getSequenceB() {
		return sequenceB;
	}
	
	public String getNameA() {
		return nameA;
	}
	
	public String getNameB() {
		return nameB;
	}

	@Override
	public String toString() {
		return "SequencePair [\t" + nameA + ":" + String.copyValueOf(sequenceA) + "\n\t\t" + nameB +":"
				+ String.copyValueOf(sequenceB) + "]";
	}

	public void setScore(int i) {
		// TODO Auto-generated method stub
		
	}

	public void setSequenceA(char[] charArray) {
		// TODO Auto-generated method stub
		
	}

	public void setSequenceB(char[] charArray) {
		// TODO Auto-generated method stub
		
	}
	
	public float getScore(){
		return score;
	}
	
}
