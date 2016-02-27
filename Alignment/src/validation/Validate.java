package validation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

//	used to create tsv files for validation statistics
	public static void formatFile(String path, String out, int i) throws IOException {
		File file = new File(path);
		File fileOut = new File(out);
		FileWriter fw = new FileWriter(fileOut);
		BufferedReader br = new BufferedReader(new FileReader(file));
		ArrayList<String[]> th = new ArrayList<String[]>();
		String line;
		while((line = br.readLine()) != null) {
			String[] par = line.split(" ");
			th.add(par);
		}
		fw.write(String.format("%.1f",Float.parseFloat(th.get(0)[0])) + "\t");
		fw.write(String.format("%.1f",Float.parseFloat(th.get(0)[1])) + "\t");
		fw.write(String.format("%.4f",(Float.parseFloat(th.get(0)[i]))/657) + "\n");
		for (int j = 1; j < th.size(); j++) {
			float a = Float.parseFloat(th.get(j)[i]);
			float b = Float.parseFloat(th.get(j-1)[i]);
			fw.write(String.format("%.1f",Float.parseFloat(th.get(j)[0])) + "\t");
			fw.write(String.format("%.1f",Float.parseFloat(th.get(j)[1])) + "\t");
			fw.write(String.format("%.4f",(a-b)/657) + "\n");
		}
		fw.close();
	}
	
//	used to validate various gap open adn gap extend settings against HOMSTRAD databank
	public static void paramTuning(String path) throws IOException {
		ArrayList<char[]> refs = getRefAlignments(path);
		float sens = 0;
		float spec = 0;
		float cov = 0;
		float mse = 0;
		float imse = 0;
		File file = new File("/home/b/beckerr/propra/gotoh_testset_tuning.txt");
		FileWriter fw;
		for (float j = 0; j > -10; j -= 0.1) {
			for (float j2 = -24; j2 < -4; j2 += 0.2) {
				for (int i = 0; i < refs.size(); i += 2) {
					String seqA = String.valueOf(refs.get(i));
					String seqB = String.valueOf(refs.get(i + 1));
					GotohGlobal a = new GotohGlobal(
							new SequencePair(seqA.replaceAll("-", ""),
									seqB.replaceAll("-", ""), "", ""),
							new GapFunction(j2, j),
							new ScoringMatrix(
									"/home/proj/biocluster/praktikum/bioprakt/Data/MATRICES/blosum62.mat"));
					a.make();
					float[] vals = calcValidation(refs.get(i), refs.get(i + 1),
							a.getFinalAlignment().getSequenceA(), a
									.getFinalAlignment().getSequenceB());
					sens += vals[0];
					spec += vals[1];
					cov += vals[2];
					mse += vals[3];
					imse += vals[4];
				}
				fw = new FileWriter(file, true);
				fw.write(j2 + " " + j + " " + sens + " " + spec + " " + cov + " " + mse + " " + imse + "\n");
				fw.close();
				sens = 0;
				spec = 0;
				cov = 0;
				mse = 0;
				imse = 0;
			}
		}		
	}

//	validates given file containing reference and candidate alignments
	public static void validateFile(String path) throws IOException {
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
			String head = line;
			if (line.startsWith(">")) {
				float[] val = null;
				String aR = br.readLine();
				String bR = br.readLine();
				String aP = br.readLine();
				String bP = br.readLine();
				val = calcValidation(aR.split(" ")[1].toCharArray(),
						bR.split(" ")[1].toCharArray(),
						aP.split(" ")[1].toCharArray(),
						bP.split(" ")[1].toCharArray());
				System.out.println(head + " " + String.format("%.4f", val[0])
						+ " " + String.format("%.4f", val[1]) + " "
						+ String.format("%.4f", val[2]) + " "
						+ String.format("%.4f", val[3]) + " "
						+ String.format("%.4f", val[4]));
				System.out.println(aR);
				System.out.println(bR);
				System.out.println(aP);
				System.out.println(bP);
			}
		}
		br.close();
	}

	public static ArrayList<char[]> getRefAlignments(String path)
			throws IOException {
		ArrayList<char[]> out = new ArrayList<char[]>();
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
			if (line.startsWith(">")) {
				out.add(br.readLine().toCharArray());
				out.add(br.readLine().toCharArray());
			}
		}
		br.close();
		return out;
	}

//	calculates validation values for given candidate and reference alignment
	public static float[] calcValidation(char[] aRef, char[] bRef, char[] aPre,
			char[] bPre) {
		float[] validateOut = new float[5];
		HashMap<Integer, Integer> refMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> preMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> invRefMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> invPreMap = new HashMap<Integer, Integer>();
		int aliCor = 0;
		int tem = 0;
		int tar = 0;
		int cov = 0;
		int invCov = 0;
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
			} else if (aRef[i] != '-') {
				tem++;
			} else if (bRef[i] != '-') {
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
			} else if (aPre[i] != '-') {
				tem++;
			} else if (bPre[i] != '-') {
				tar++;
			}
		}
		int aliPreTot = refMap.size();
		int aliRefTot = preMap.size();
		for (int key : refMap.keySet()) {
			if (preMap.containsKey(key)) {
				cov++;
				int a = preMap.get(key);
				int b = refMap.get(key);
				if (a == b) {
					aliCor++;
				} else {
					
					shift += Math.abs(a - b);
				}
			}
		}
		for (int key : invRefMap.keySet()) {
			if (invPreMap.containsKey(key)) {
				invCov++;
				int a = invPreMap.get(key);
				int b = invRefMap.get(key);	
				if(a != b) {
	
				invShift += Math.abs(a - b);
			}
		}
		}
		validateOut[0] = aliCor / (float) aliRefTot;
		validateOut[1] = aliCor / (float) aliPreTot;
		validateOut[2] = cov / (float) aliPreTot;
		if (cov == 0) {
			validateOut[3] = 0;
			validateOut[4] = 0;
		} else {
			validateOut[3] = shift / (float) cov;
			validateOut[4] = invShift / (float) invCov;
		}
		return validateOut;
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(new Locale("US"));
		// createValidation("/home/b/beckerr/propra/gotoh_testset.txt",
		// "/home/b/beckerr/Desktop/blosum62-10-2.txt");
//		 validateFile(args[1]);
//		paramTuning(args[1]);
		formatFile("/home/b/beckerr/propra/gotoh_testset_tuning.txt", "/home/b/beckerr/propra/imse.tsv", 6);
	}
}
