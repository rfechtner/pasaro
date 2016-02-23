package alignment;

public class Tester {
	public static void main(String[] args) {
		Alignment a = new Global(new SequencePair("AAAAAAAGGG", "CCGGGCCCCCCCCCCCCCC"), new GapFunction(-4, 0), new ScoringMatrix(""));
		a.make();
	}
}
