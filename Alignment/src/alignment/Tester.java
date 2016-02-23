package alignment;

public class Tester {
	public static void main(String[] args) {
		Alignment a = new Freeshift(new SequencePair("TTTTGGGGAAAAAAAGGTTAAAAAA", "AAAACCCCAAAAAAATTGGC"), new GapFunction(-4, 0), new ScoringMatrix("/home/proj/biocluster/praktikum/bioprakt/Data/MATRICES/blosum62.mat"));
		a.make();
	}
}
