package alignment;

public class Tester {
	public static void main(String[] args) {
		Alignment a = new Local(new SequencePair("TTTTGGGGAAAAAAAGGTTAAAAAA", "AAAACCCCAAAAAAATTGGC"), new GapFunction(-1, 0), new ScoringMatrix(""));
		a.make();
	}
}
