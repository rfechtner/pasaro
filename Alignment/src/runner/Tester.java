package runner;

import java.io.IOException;

import enums.AlignmentFormat;
import enums.AlignmentType;

import alignment.Alignment;
import alignment.Freeshift;
import alignment.Global;
import alignment.GotohFreeshift;
import alignment.GotohGlobal;
import alignment.GotohLocal;
import alignment.Local;
import alignmentUtils.GapFunction;
import alignmentUtils.Output;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class Tester {
	public static void main(String[] args) throws IOException {
		Alignment a = new Freeshift(new SequencePair("MAUHHHHGDHFGHGGGGGGGGGGGGGGGGGGGGGGGGGGGGGDHGFHDGHFGGHHHS", "SUOOOPOPOPOAPSOAPFFFFFFFFFFFFFFFFFFFFFFFSPOOSOPAOSMA", "SeqA", "SeqB"), new GapFunction(-1, -1), new ScoringMatrix("/home/proj/biocluster/praktikum/bioprakt/Data/MATRICES/blosum62.mat"));
		a.make();
		a.printResult();
		System.out.println(a.checkScore());
		a.dpMatrices("/home/b/beckerr/Desktop/");
		
//		Output.genOutput(a.getFinalAlignment(), AlignmentFormat.html);
	}
}
