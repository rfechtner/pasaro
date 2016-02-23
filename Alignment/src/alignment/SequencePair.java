package alignment;

public class SequencePair {
	private char[] sequenceA;
	private char[] sequenceB;
	private String nameA;
	private String nameB;
	private int score;
	boolean alignment;
	
	public SequencePair(String sequenceA, String sequenceB) {
		this.sequenceA = sequenceA.toCharArray();
		this.sequenceB = sequenceB.toCharArray(); 
	}
	
	public SequencePair(String sequenceA, String sequenceB, String nameA, String nameB) {
		this.sequenceA = sequenceA.toCharArray();
		this.sequenceB = sequenceB.toCharArray();
		this.nameA = nameA;
		this.nameB = nameB;
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
	
	
}
