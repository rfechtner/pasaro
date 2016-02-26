package validation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import alignment.GotohGlobal;
import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class Validate {


//	public static void createValidation(String pathIn, String pathOut)
//			throws IOException {
//		File input = new File(pathIn);
//		File output = new File(pathOut);
//		FileWriter fw = new FileWriter(output);
//		BufferedReader br = new BufferedReader(new FileReader(input));
//		String line;
//		while ((line = br.readLine()) != null) {
//			if (line.startsWith(">")) {
//				String[] ids = line.replace(">", "").split(" ");
//				String seqA = br.readLine().replaceAll("/", "-");
//				String seqB = br.readLine().replaceAll("/", "-");
//				if (seqA.length() == seqB.length()) {
//					GotohGlobal g = new GotohGlobal(
//							new SequencePair(seqA.replaceAll("-", ""),
//									seqB.replaceAll("-", ""), "", ""),
//							new GapFunction(-8, -1),
//							new ScoringMatrix(
//									"/home/proj/biocluster/praktikum/bioprakt/Data/MATRICES/blosum62.mat"));
//					g.make();
//					fw.write(line + "\n");
//					fw.write(ids[0] + ": " + seqA + "\n");
//					fw.write(ids[1] + ": " + seqB + "\n");
//					fw.write(ids[0]
//							+ ": "
//							+ String.valueOf(g.getFinalAlignment()
//									.getSequenceA()) + "\n");
//					fw.write(ids[1]
//							+ ": "
//							+ String.valueOf(g.getFinalAlignment()
//									.getSequenceB()) + "\n");
//				}
//			}
//		}
//		fw.close();
//		br.close();
//	}

	public static void validateFile(String path) throws IOException {
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while((line = br.readLine()) != null) {
			String head = line;
			if(line.startsWith(">")) {
				float[] val = null;
				String aR = br.readLine();
				String bR = br.readLine();
				String aP = br.readLine();
				String bP = br.readLine();
				val = calcValidation(aR.split(" ")[1].toCharArray(), bR.split(" ")[1].toCharArray(), aP.split(" ")[1].toCharArray(), bP.split(" ")[1].toCharArray());
				System.out.println(head + " " + String.format("%.4f", val[0]) + " " + String.format("%.4f", val[1]) + " " + String.format("%.4f", val[2]) + " " + String.format("%.4f", val[3]) + " " + String.format("%.4f", val[4]));
				System.out.println(aR);
				System.out.println(bR);
				System.out.println(aP);
				System.out.println(bP);
			}
		}
		br.close();
	}
	
	public static ArrayList<char[]> getRefAlignments(String path) throws IOException {
		ArrayList<char[]> out = new ArrayList<char[]>();
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while((line = br.readLine()) != null) {
			if(line.startsWith(">")) {
				out.add(br.readLine().split(" ")[1].toCharArray());
				out.add(br.readLine().split(" ")[1].toCharArray());
			}
		}
		br.close();
		return out;
	}
	
	public static float[] calcValidation(char[] aRef, char[] bRef, char[] aPre,
			char[] bPre) {
		float[] validateOut = new float[5];
		HashMap<Integer, Integer> refMap= new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> preMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> invRefMap= new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> invPreMap = new HashMap<Integer, Integer>();
		int aliCor = 0;
		int tem = 0;
		int tar = 0;
		int cov = 0;
		int shiftCount = 0;
		int shift = 0;
		int invShiftCount = 0;
		int invShift = 0;
		for (int i = 0; i < aRef.length; i++) {
			if (aRef[i] != '-' && bRef[i] != '-') {
				refMap.put(tar, tem);
				invRefMap.put(tem, tar);
				tar++;
				tem++;
			}else if(aRef[i] != '-'){
				tem++;
			}else if(bRef[i] != '-') {
				tar++;
			}
		}
		tar = 0;
		tem = 0;
		for (int i = 0; i < aPre.length; i++) {
			if (aPre[i] != '-' && bPre[i] != '-') {
				preMap.put(tar, tem);
				invPreMap.put(tem, tar);
				tar++;
				tem++;
			}else if(aPre[i] != '-'){
				tem++;
			}else if(bPre[i] != '-') {
				tar++;
			}
		}
		int aliRefTot = refMap.size();
		int aliPreTot = preMap.size();
		for (int key : refMap.keySet()) {
			if(preMap.containsKey(key)) {
				cov++;
				if(preMap.get(key) == refMap.get(key)) {
					aliCor++;
				}else {
					shiftCount++;
					shift += Math.abs(preMap.get(key) - refMap.get(key));
				}
			}
		}
		for (int key : invRefMap.keySet()) {
			if(invPreMap.containsKey(key) && invPreMap.get(key) != invRefMap.get(key)) {
				invShiftCount++;
				invShift += Math.abs(invPreMap.get(key) - invRefMap.get(key));
			}
		}
		validateOut[0] = aliCor / (float) aliRefTot;
		validateOut[1] = aliCor / (float) aliPreTot;
		validateOut[2] = cov / (float) aliPreTot;
		validateOut[3] = shift / (float) shiftCount;
		validateOut[4] = invShift / (float) invShiftCount;
		return validateOut;
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(new Locale("US"));
		// createValidation("/home/b/beckerr/propra/gotoh_testset.txt",
		// "/home/b/beckerr/Desktop/blosum62-10-2.txt");
		validateFile(args[1]);
	}
}
