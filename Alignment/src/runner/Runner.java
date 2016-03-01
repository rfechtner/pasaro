package runner;

import java.util.ArrayList;
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
import alignmentUtils.SequenceParser;
import enums.AlignmentFormat;
import parameterUtils.ParamException;
import parameterUtils.Params;

public class Runner {
	public static void main(String[] args) {
		Params p = new Params(Params.getParams(args));
		
		
		ScoringMatrix sm = new ScoringMatrix(p.getM());
		GapFunction gf = new GapFunction(p.getGo(), p.getGe());
		ArrayList<SequencePair> sequencePairs = SequenceParser.parseSeq(p.getSeqlib(), p.getPairs());
		
		int count;
		// Aufgrung von Memory out of Bounce Exception herausgenommen
		// ArrayList<Alignment> alignments = new ArrayList<Alignment>();
		
		// Musste hinzugefügt werden um den "header" für html und Json outputs zu genieren
		switch(p.getFormat()){
			case html: Output.printHtmlHeader(); break;
			case json: count = sequencePairs.size(); Output.printJsonStart(count); break;
			default: break;
		}
		
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
			
			// Moved in here
			if(p.getDpmatrices() != null){
				try {
					al.dpMatrices(p.getDpmatrices());
				} catch (Exception e) {
					ParamException.printHelp();
				}
			}
			
			if(p.isCheck()) {
				if(!(al.getFinalAlignment().getScore() == al.checkScore())) {
					Output.genOutput(al, p.getFormat(), p.getMode());
					//alignments.add(al);
				}
			}else {
				Output.genOutput(al, p.getFormat(), p.getMode());
				//alignments.add(al);
			}
		}
		
		// Moved
//		if(p.getDpmatrices() != null){
//			for(Alignment al : alignments){
//				try {
//					al.dpMatrices(p.getDpmatrices());
//				} catch (Exception e) {
//					ParamException.printHelp();
//				}
//			}
//		}
		
		//Output.genOutput(alignments, p.getFormat(), p.getMode());

		if(p.getFormat() == AlignmentFormat.html) Output.printHtmlEnd();
	}
}
