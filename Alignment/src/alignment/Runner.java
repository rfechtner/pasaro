package alignment;

import java.util.ArrayList;

public class Runner {
	public static void main(String[] args) {
		Params p = new Params(Params.getParams(args));
		System.out.println(p.toString());
		
		ScoringMatrix sm = new ScoringMatrix(p.getM());
		GapFunction gf = new GapFunction(p.getGo(), p.getGe());
		
		ArrayList<SequencePair> sequencePairs = SequenceParser.parseSeq(p.getSeqlib(), p.getPairs());
		
		//for ( SequencePair sp : sequencePairs ){
		//	System.out.println(sp.toString());
		//}
	}
	
	
}
