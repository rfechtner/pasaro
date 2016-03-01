package alignmentUtils;

import java.util.ArrayList;
import java.util.Locale;

import alignment.Alignment;
import enums.AlignmentFormat;
import enums.AlignmentType;

public class Output {
	private static int jsonCount = 0;
	private static int jsonMax = 0;
	
	public static void genOutput(Alignment alignment, AlignmentFormat format, AlignmentType type){
		switch(format) {
			case scores: genScoresOutput(alignment); break;
			case ali: genAliOutput(alignment); break;
			case html: genHtmlOutput(alignment, type); break;
			case json: getJsonOutput(alignment, type); break;
		}
	}
	
	private static void genScoresOutput(Alignment al){
		//for (Alignment al : alignments){
			System.out.println(al.getFinalAlignment().getNameA() + " " + al.getFinalAlignment().getNameB() + " " + String.format(Locale.US, "%.4f", al.getFinalAlignment().getScore()));
		//}	
	}
	
	private static void genAliOutput(Alignment al){
		//for (Alignment al : alignments){
			System.out.println(">" + al.getFinalAlignment().getNameA() + " " + al.getFinalAlignment().getNameB() + " " + String.format(Locale.US, "%.4f", al.getFinalAlignment().getScore()));
			System.out.println(al.getFinalAlignment().getNameA() + ": " + String.copyValueOf(al.getFinalAlignment().getSequenceA()));
			System.out.println(al.getFinalAlignment().getNameB() + ": " + String.copyValueOf(al.getFinalAlignment().getSequenceB()));
		//}
	}
	
	public static void printHtmlHeader(){
		String head = "<!DOCTYPE html>\n"
				+ "<head>\n"
				+ "<title>Alignment results</title>\n"
				+ "<style>table td{width: 200px; display: inline-block; white-space: nowrap;} .double { overflow-x: auto; width: 400px !important;}</style>"
				+ "</head>\n"
				+ "<body>\n"
				+ "<table>\n"
				+ "<tr>\n"
				+ "<th></th>\n"
				+ "<th>Sequence A</th>\n"
				+ "<th>Sequence B</th>\n"
				+ "</tr>";
		
		System.out.print(head);
	}
	
	public static void printHtmlEnd(){
		String end = "</table>\n"
				+ "</body>";
		
		System.out.print(end);
	}
	
	// Statt ArrayList<Alignment> nurnoch ein ALignment
	public static void genHtmlOutput(Alignment al, AlignmentType type){
		
		// Obsolete
//		String head = "<!DOCTYPE html>\n"
//				+ "<head>\n"
//				+ "<title>Alignment results</title>\n"
//				+ "<style>table td{width: 200px; display: inline-block; white-space: nowrap;} .double { overflow-x: auto; width: 400px !important;}</style>"
//				+ "</head>\n"
//				+ "<body>\n"
//				+ "<table>\n"
//				+ "<tr>\n"
//				+ "<th></th>\n"
//				+ "<th>Sequence A</th>\n"
//				+ "<th>Sequence B</th>\n"
//				+ "</tr>";
		
//		String body = "</table>\n"
//				+ "</body>";
		
		String row = "";
		
		//for(Alignment al : alignments ){
			int matches = 0;
			int alignmentLength = 0;
			ArrayList<Integer> path = al.getPath();
			
			for(int i = 0; i < path.size() / 2; i++){
				int x = path.get(i*2);
				int y = path.get(i*2 + 1);
				
				if(al.getSequence().getSequenceA()[x-1] == al.getSequence().getSequenceB()[y-1]) matches++;
				alignmentLength++;
			}
			
			if (type == AlignmentType.global) alignmentLength = al.getFinalAlignment().getSequenceA().length;
			
			int seqLengthA = al.getSequence().getSequenceA().length;
			int seqLengthB = al.getSequence().getSequenceB().length;
			
			double id = matches / (double) alignmentLength;
			double identity = id * 100;
			
			Locale.setDefault(new Locale("US"));
			
			row += "<tr>\n"
					+ "<td>Sequence names</td>"
					+ "<td>" + al.getFinalAlignment().getNameA() + "</td>\n"
					+ "<td>" + al.getFinalAlignment().getNameA() + "</td>\n"
				    + "</tr>\n";
			
			row += "<tr>\n"
					+ "<td>Sequence lenghts</td>"
					+ "<td>" + seqLengthA + "</td>\n"
					+ "<td>" + seqLengthB + "</td>\n"
				    + "</tr>\n";
			
			row += "<tr>\n"
					+ "<td>Alignment length</td>"
					+ "<td colspan='2' class='double'>" + alignmentLength + "</td>\n"
				    + "</tr>\n";
			
			row += "<tr>\n"
					+ "<td>No. of matches</td>"
					+ "<td colspan='2' class='double'>" + matches + "</td>\n"
				    + "</tr>\n";
			
			row += "<tr>\n"
					+ "<td>% Identity</td>"
					+ "<td colspan='2' class='double'>" + identity + "</td>\n"
				    + "</tr>\n";
			
			row += "<tr>\n"
					+ "<td>Alignment</td>"
					+ "<td colspan='2' class='double' style='height: 50px;'>" + String.copyValueOf(al.getFinalAlignment().getSequenceA()) + "<br/>" + String.copyValueOf(al.getFinalAlignment().getSequenceB()) + "</td>\n"
				    + "</tr>\n";
			
			row += "<tr>\n"
					+ "<td colspan='3'> - </td>\n"
				    + "</tr>\n";
			
		//}

		//String html = head + rows + body;
		
		//System.out.println(html);
		
		System.out.print(row);
	}
	
	public static void printJsonStart(int count){
		System.out.print("[");
		jsonMax = count;
	}
	
	private static void getJsonOutput(Alignment al, AlignmentType type){
		StringBuilder json = new StringBuilder();
		
		//json.append("[");
		
		//int count = 0;
		//for (Alignment al : alignments) {
			int matches = 0;
			int alignmentLength = 0;
			ArrayList<Integer> path = al.getPath();
			
			for(int i = 0; i < path.size() / 2; i++){
				int x = path.get(i*2);
				int y = path.get(i*2 + 1);
				
				if(al.getSequence().getSequenceA()[x-1] == al.getSequence().getSequenceB()[y-1]) matches++;
				alignmentLength++;
			}
			
			if (type == AlignmentType.global) alignmentLength = al.getFinalAlignment().getSequenceA().length;
			
			int seqLengthA = al.getSequence().getSequenceA().length;
			int seqLengthB = al.getSequence().getSequenceB().length;
			
			double id = matches / (double) alignmentLength;
			double identity = id * 100;
			
			Locale.setDefault(new Locale("US"));
			String ident = String.format("%.2f", identity);
	
			StringBuilder sb = new StringBuilder();
			sb.append("{\"id\":");
			sb.append(jsonCount);
			sb.append(",\"matches\":");
			sb.append(matches);
			sb.append(",\"score\":");
			sb.append(al.getFinalAlignment().getScore());
			sb.append(",\"identity\":");
			sb.append(ident);
			sb.append(",\"nameA\":\"");
			sb.append(al.getFinalAlignment().getNameA());
			sb.append("\",\"nameB\":\"");
			sb.append(al.getFinalAlignment().getNameB());
			sb.append("\",\"lengthA\":\"");
			sb.append(seqLengthA);
			sb.append("\",\"lengthB\":");
			sb.append(seqLengthB);
			sb.append(",\"seqA\":\"");
			sb.append(String.copyValueOf(al.getFinalAlignment().getSequenceA()));
			sb.append("\",\"seqB\":\"");
			sb.append(String.copyValueOf(al.getFinalAlignment().getSequenceB()));
			sb.append("\",\"seqAoriginal\":\"");
			sb.append(String.copyValueOf(al.getSequence().getSequenceA()));
			sb.append("\",\"seqBoriginal\":\"");
			sb.append(String.copyValueOf(al.getSequence().getSequenceB()));
			sb.append("\",\"lengthAli\":");
			sb.append(alignmentLength);
			sb.append("},\n");
			
			json.append(sb);
			jsonCount++;
		//}
		String jsonFinal;
		
		if (jsonCount == jsonMax) {
			jsonFinal = json.substring(0,json.length()-2) + "]";
		} else {
			jsonFinal = json.toString();
		}
		
		System.out.print(jsonFinal);
	}
}
