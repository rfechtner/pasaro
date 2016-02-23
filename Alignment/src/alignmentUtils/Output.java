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
		System.out.println(alignment.getNameA() + " " + alignment.getNameB() + " " + alignment.getScore());
	}
	
	private static void genAliOutput(SequencePair alignment){
		System.out.println(alignment.getNameA() + " " + alignment.getNameB() + " " + alignment.getScore());
		System.out.println(alignment.getNameA() + ": " + String.copyValueOf(alignment.getSequenceA()));
		System.out.println(alignment.getNameB() + ": " + String.copyValueOf(alignment.getSequenceB()));
	}

	private static void genHtmlOutput(SequencePair alignment){
		String html = 	"<div class='alignment'>\n" +
							"<div class='information'>\n" +
								"\n" +
							"</div>\n" +
						"</div>\n";
	}
}
