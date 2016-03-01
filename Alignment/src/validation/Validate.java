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

import alignment.Alignment;
import alignment.GotohGlobal;
import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class Validate {

	// used to get maximum validation value from tsv file
	public static void getMax(String path, boolean max) throws IOException {
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));
		float i = 0;
		float j = 0;
		float val;
		if (max)
			val = Integer.MIN_VALUE;
		else
			val = Integer.MAX_VALUE;
		String line;
		while ((line = br.readLine()) != null) {
			String[] vals = line.split("\t");
			float tem = Float.parseFloat(vals[2]);
			if (tem < val) {
				val = tem;
				i = Float.parseFloat(vals[0]);
				j = Float.parseFloat(vals[1]);
			}
		}
		br.close();
		System.out.println(i + " " + j + " " + val);
	}

	// used to get statistics for correlation between identity and validation
	public static void identCorrelations(String path, String out, int type)
			throws IOException {
		ArrayList<char[]> refs = getRefAlignments(path);
		File outFile = new File(out);
		FileWriter fw;
		for (int i = 0; i < refs.size(); i += 2) {
			char[] a = refs.get(i);
			char[] b = refs.get(i + 1);
			int idC = 0;
			String identity;
			for (int j = 0; j < b.length; j++) {
				if (a[j] == b[j])
					idC++;
			}
			identity = String.format("%.3f", idC / (float) a.length);
			Alignment al = new GotohGlobal(
					new SequencePair(String.valueOf(a).replaceAll("-", ""),
							String.valueOf(b).replaceAll("-", ""), "", ""),
					new GapFunction(-20, -4),
					new ScoringMatrix(
							"/home/proj/biocluster/praktikum/bioprakt/Data/MATRICES/blosum62.mat"));
			al.make();
			float[] val = calcValidation(a, b, al.getFinalAlignment()
					.getSequenceA(), al.getFinalAlignment().getSequenceB());
			fw = new FileWriter(outFile, true);
			fw.write(identity + "\t" + String.format("%.3f", val[type]) + "\n");
			fw.close();
		}
	}

	// used to get statistics for correlation between identity and validation
	public static void diffCorrelations(String path, String out, int type)
			throws IOException {
		ArrayList<char[]> refs = getRefAlignments(path);
		File outFile = new File(out);
		FileWriter fw;
		for (int i = 0; i < refs.size(); i += 2) {
			char[] a = refs.get(i);
			char[] b = refs.get(i + 1);
			int diff = Math.abs(String.valueOf(a).replaceAll("-", "").length()
					- String.valueOf(b).replaceAll("-", "").length());
			Alignment al = new GotohGlobal(
					new SequencePair(String.valueOf(a).replaceAll("-", ""),
							String.valueOf(b).replaceAll("-", ""), "", ""),
					new GapFunction(-20, -4),
					new ScoringMatrix(
							"/home/proj/biocluster/praktikum/bioprakt/Data/MATRICES/blosum62.mat"));
			al.make();
			float[] val = calcValidation(a, b, al.getFinalAlignment()
					.getSequenceA(), al.getFinalAlignment().getSequenceB());
			fw = new FileWriter(outFile, true);
			fw.write(diff + "\t" + String.format("%.3f", val[type]) + "\n");
			fw.close();
		}
	}

	// used to create tsv files for validation statistics
	public static void formatFile(String path, String out, int i)
			throws IOException {
		File file = new File(path);
		File fileOut = new File(out);
		FileWriter fw = new FileWriter(fileOut);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
			String[] par = line.split(" ");
			fw.write(String.format("%.1f", Float.parseFloat(par[0])) + "\t");
			fw.write(String.format("%.1f", Float.parseFloat(par[1])) + "\t");
			fw.write(String.format("%.4f", Float.parseFloat(par[i])) + "\n");
		}
		fw.close();
	}

	// used to validate various gap open and gap extend settings against
	// HOMSTRAD databank
	public static void paramTuning(String path, String mat, String out)
			throws IOException {
		ArrayList<char[]> refs = getRefAlignments(path);
		float sens = 0;
		float spec = 0;
		float cov = 0;
		float mse = 0;
		float imse = 0;
		File file = new File("/home/b/beckerr/propra/" + out);
		FileWriter fw;
		for (float j = 0; j > -10; j -= 0.1) {
			for (float j2 = -24; j2 < -4; j2 += 0.2) {
				for (int i = 0; i < refs.size(); i += 2) {
					String seqA = String.valueOf(refs.get(i));
					String seqB = String.valueOf(refs.get(i + 1));
					GotohGlobal a = new GotohGlobal(new SequencePair(
							seqA.replaceAll("-", ""), seqB.replaceAll("-", ""),
							"", ""), new GapFunction(j2, j), new ScoringMatrix(
							"/home/proj/biocluster/praktikum/bioprakt/Data/MATRICES/"
									+ mat));
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
				fw.write(String.format("%.1f", j2) + " "
						+ String.format("%.1f", j) + " "
						+ String.format("%.4f", sens / 657) + " "
						+ String.format("%.4f", spec / 657) + " "
						+ String.format("%.4f", cov / 657) + " "
						+ String.format("%.4f", mse / 657) + " "
						+ String.format("%.4f", imse / 657) + "\n");
				fw.close();
				sens = 0;
				spec = 0;
				cov = 0;
				mse = 0;
				imse = 0;
			}
		}
	}

	// validates given file containing reference and candidate alignments
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

	// takes file and creates list with reference alignments
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

	// calculates validation values for given candidate and reference alignment
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
		int shift = 0;
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
				if (a != b) {
					invShift += Math.abs(a - b);
				}
			}
		}
		validateOut[0] = aliCor / (float) aliRefTot;
		validateOut[1] = aliCor / (float) aliPreTot;
		validateOut[2] = cov / (float) aliPreTot;
		if (cov == 0) {
			validateOut[3] = 0;
		} else {
			validateOut[3] = shift / (float) cov;
		}
		if (invCov == 0) {
			validateOut[4] = 0;
		} else {
			validateOut[4] = invShift / (float) invCov;
		}
		return validateOut;
	}		
}
