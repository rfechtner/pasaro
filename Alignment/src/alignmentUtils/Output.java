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
		System.out.println(">" + alignment.getNameA() + " " + alignment.getNameB() + " " + alignment.getScore());
		System.out.println(alignment.getNameA() + ": " + String.copyValueOf(alignment.getSequenceA()));
		System.out.println(alignment.getNameB() + ": " + String.copyValueOf(alignment.getSequenceB()));
	}

	private static void genHtmlOutput(SequencePair alignment){
		String chars = "";
		
		for(int i = 0; i < alignment.getSequenceA().length; i++){
			String mat = "";
			if (alignment.getSequenceA()[i] == alignment.getSequenceB()[i]) { mat = "mat"; }
			else if (alignment.getSequenceA()[i] != '-' && alignment.getSequenceB()[i] != '-') { mat = "sim"; }
			
			chars += 	"\t<div class='char " + mat + "'> \n" +
							"\t\t<div class='SeqA'>" + alignment.getSequenceA()[i] + "</div>\n"+
							"\t\t<div class='SeqB'>" + alignment.getSequenceB()[i] + "</div>\n"+
						"\t</div>\n";
		}
		
		String html = 	"<div class='alignment'>\n" +
							"\t<div class='information'>\n" +
								alignment.getScore() +
							"\t</div>\n" +
							"\t<div class='sequences" + alignment.getNameA() + "_" + alignment.getNameB() + "'>\n" +
							chars +
							"\t</div>\n" +
						"</div>\n";
		
		System.out.println(html);
	}
}
