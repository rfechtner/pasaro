package runner;

import alignment.Alignment;
import alignment.Global;
import alignment.GotohGlobal;
import alignment.Local;
import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class Tester {
	public static void main(String[] args) {
		Alignment a = new GotohGlobal(new SequencePair("HPICEV", "GRFEILEKNGKMMMMM", "SeqA", "SeqB"), new GapFunction(-11, -1), new ScoringMatrix("/home/proj/biocluster/praktikum/bioprakt/Data/MATRICES/blosum62.mat"));
		a.make();
	}
}
