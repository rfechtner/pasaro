package alignmentUtils;

import enums.AlignmentFormat;

public class Output {
	public static void genOutput(SequencePair alignment, AlignmentFormat format){
		switch(format) {
			case scores: genScoresOutput(alignment); break;
			case ali: genAliOutput(alignment); break;
			case html: genHtmlOutput(alignment); break;
		}
	}
	
	private static void genScoresOutput(SequencePair alignment){
		System.out.println(alignment.getNameA() + " " + alignment.getNameB() + " " + alignment.);
	}
	
	private static void genAliOutput(SequencePair alignment){
		
	}

	private static void genHtmlOutput(SequencePair alignment){
	
	}
}
