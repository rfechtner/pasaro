package alignment;

import java.util.ArrayList;

public class Runner {
	public static void main(String[] args) {
		Params p = new Params(Params.getParams(args));
		System.out.println(p.toString());
		
		ScoringMatrix sm = new ScoringMatrix(p.getM());
		GapFunction gf = new GapFunction(p.getGo(), p.getGe());
		ArrayList<SequencePair> sequencePairs = SequenceParser.parseSeq(p.getSeqlib(), p.getPairs());
		
		for ( SequencePair sp : sequencePairs ){
			Alignment al = null;
			if(p.isNw()) {
				switch (p.getMode()){
					case local: al = new Local(sp, gf, sm); break;
					case global: al = new Global(sp, gf, sm); break;
					case freeshift: al = new Freeshift(sp, gf, sm); break;
				}
			} else {
				switch (p.getMode()){
					case local: al = new GotohLocal(sp, gf, sm); break;
					case global: al = new GotohGlobal(sp, gf, sm); break;
					case freeshift: al = new GotohFreeshift(sp, gf, sm); break;
				}
			}
			
			al.make();
			
			//System.out.println("Alignment für " + sp.toString());
			//System.out.println("\t Alignment: " + al.getFinalAlignment().toString());
			//System.out.println("\t Score: " + al.getFinalScore());
		}
	}
}
