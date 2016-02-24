package runner;

import alignment.Alignment;
import alignment.Global;
import alignment.GotohFreeshift;
import alignment.GotohGlobal;
import alignment.GotohLocal;
import alignment.Local;
import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class Tester {
	public static void main(String[] args) {
		Alignment a = new GotohLocal(new SequencePair("HPICEVGTGTGHGHCCCCCCCCGHHTTGHTTH", "GRFCCCCCEILSSSSLSIIILILSLILIEKCEK", "SeqA", "SeqB"), new GapFunction(-1, -1), new ScoringMatrix("/home/proj/biocluster/praktikum/bioprakt/Data/MATRICES/blosum62.mat"));
		a.make();
	}
}
