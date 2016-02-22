package alignment;

public class SequencePair {
	private String sequenceA;
	private String sequenceB;
	private String nameA;
	private String nameB;
	
	public SequencePair(String sequenceA, String sequenceB) {
		this.sequenceA = sequenceA;
		this.sequenceB = sequenceB; 
	}
	
	public SequencePair(String sequenceA, String sequenceB, String nameA, String nameB) {
		this.sequenceA = sequenceA;
		this.sequenceB = sequenceB;
		this.nameA = nameA;
		this.nameB = nameB;
	}

	public String getSequenceA() {
		return sequenceA;
	}
	
	public String getSequenceB() {
		return sequenceB;
	}
	
	public String getNameA() {
		return nameA;
	}
	
	public String getNameB() {
		return nameB;
	}
}
