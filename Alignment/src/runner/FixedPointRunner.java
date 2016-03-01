package runner;

import java.io.IOException;

import alignment.FixedPoint;
import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class FixedPointRunner {
//	file name, sequence 1, sequence 2, gap open, gap extend, matrix path 
	public static void main(String[] args) throws IOException {
		FixedPoint.genFixedPointGraphic(new SequencePair(args[1], args[2], "", ""), new GapFunction(Float.parseFloat(args[3]), Float.parseFloat(args[4])), new ScoringMatrix(args[5]), args[0]);
	}
}
