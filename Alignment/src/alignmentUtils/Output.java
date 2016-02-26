package alignmentUtils;

import java.util.ArrayList;

import alignment.Alignment;
import enums.AlignmentFormat;

public class Output {
	public static void genOutput(ArrayList<Alignment> alignments, AlignmentFormat format){
		switch(format) {
			case scores: genScoresOutput(alignments); break;
			case ali: genAliOutput(alignments); break;
			case html: genHtmlOutput(alignments); break;
			case json: getJsonOutput(alignments); break;
		}
	}
	
	private static void genScoresOutput(ArrayList<Alignment> alignments){
		for (Alignment al : alignments){
			System.out.println(al.getFinalAlignment().getNameA() + " " + al.getFinalAlignment().getNameB() + " " + al.getFinalAlignment().getScore());
		}	
	}
	
	private static void genAliOutput(ArrayList<Alignment> alignments){
		for (Alignment al : alignments){
			System.out.println(">" + al.getFinalAlignment().getNameA() + " " + al.getFinalAlignment().getNameB() + " " + al.getFinalAlignment().getScore());
			System.out.println(al.getFinalAlignment().getNameA() + ": " + String.copyValueOf(al.getFinalAlignment().getSequenceA()));
			System.out.println(al.getFinalAlignment().getNameB() + ": " + String.copyValueOf(al.getFinalAlignment().getSequenceB()));
		}
	}
	
	public static void genHtmlOutput(ArrayList<Alignment> alignments){
		String head = "<!DOCTYPE html>\n"
				+ "<head>\n"
				+ "<title>Alignment results</title>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "<table>\n";
		
		String body = "</table>\n"
				+ "</body>";
		String rows = "";
		
		for(Alignment al : alignments ){
			rows += "<tr>\n"
					+ "<td>" + al.getFinalAlignment().getNameA() + " vs " + al.getFinalAlignment().getNameB() + "</td>\n"
					+ "<td>" + String.copyValueOf(al.getFinalAlignment().getSequenceA()) + "<br/>" + String.copyValueOf(al.getFinalAlignment().getSequenceB()) + "</td>\n"
				    + "</tr>\n";
		}
		
		String html = head + rows + body;
		
		System.out.println(html);
	}
	
	private static void getJsonOutput(ArrayList<Alignment> alignments){
		String json = "[";
		
		int count = 0;
		for (Alignment al : alignments) {
			String jsonObj = "{\"id\":" + count + ","
					+ "\"nameA\":\"" + al.getFinalAlignment().getNameA() + "\","
					+ "\"nameB\":\"" + al.getFinalAlignment().getNameB() + "\","
					+ "\"seqA\":\"" + String.copyValueOf(al.getFinalAlignment().getSequenceA()) + "\","
					+ "\"seqB\":\"" + String.copyValueOf(al.getFinalAlignment().getSequenceB()) + "\"},\n";
			json += jsonObj;
			count++;
		}
		
		String jsonFinal = json.substring(0,json.length()-2) + "]";
		
		System.out.println(jsonFinal);
	}
}
