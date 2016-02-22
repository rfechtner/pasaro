package alignment;

public class Tester {
	public static void main(String[] args) {
		Global g = new Global(new SequencePair("TATAAT", "TTACGTAAGC"), new GapFunction(-4, 0), new ScoringMatrix(""));
		g.make();
	}
}
