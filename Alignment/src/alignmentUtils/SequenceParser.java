package alignmentUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public class SequenceParser {
	public static ArrayList<SequencePair> parseSeq(String seqFile, String pairsFile){
		ArrayList<SequencePair> pairs = new ArrayList<SequencePair>();
		
		HashMap<String, String> sequences = new HashMap<String, String>();
		
		try {
			FileReader fr = new FileReader(seqFile);
			BufferedReader br = new BufferedReader(fr);
			
			String line = "";
			while((line = br.readLine()) != null){
				String[] fields = line.split(":");
				sequences.put(fields[0], fields[1]);
			}
			
			br.close();
			
			fr = new FileReader(pairsFile);
			br = new BufferedReader(fr);
			
			line = "";
			while((line = br.readLine()) != null){
				String[] fields = line.split(" ");
				SequencePair s = new SequencePair(sequences.get(fields[0]), sequences.get(fields[1]), fields[0], fields[1]);
				pairs.add(s);
			}
			br.close();
		} catch (IOException e) {
			System.err.println("Could not read " + seqFile + " and/or " + pairsFile);
		}
		
		return pairs;
	}
}
