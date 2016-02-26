package runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import alignment.GotohGlobal;
import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class Validate {

	public static void createValidation(String pathIn, String pathOut)
			throws IOException {
		File input = new File(pathIn);
		File output = new File(pathOut);
		FileWriter fw = new FileWriter(output);
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		while ((line = br.readLine()) != null) {
			if (line.startsWith(">")) {
				String[] ids = line.replace(">", "").split(" ");
				String seqA = br.readLine().replaceAll("/", "-");
				String seqB = br.readLine().replaceAll("/", "-");
				if (seqA.length() == seqB.length()) {				
					GotohGlobal g = new GotohGlobal(
							new SequencePair(seqA.replaceAll("-", ""),
									seqB.replaceAll("-", ""), "", ""),
							new GapFunction(-8, -1),
							new ScoringMatrix(
									"/home/proj/biocluster/praktikum/bioprakt/Data/MATRICES/blosum62.mat"));
					g.make();
					fw.write(line + "\n");
					fw.write(ids[0] + ": " + seqA + "\n");
					fw.write(ids[1] + ": " + seqB + "\n");
					fw.write(ids[0]
							+ ": "
							+ String.valueOf(g.getFinalAlignment()
									.getSequenceA()) + "\n");
					fw.write(ids[1]
							+ ": "
							+ String.valueOf(g.getFinalAlignment()
									.getSequenceB()) + "\n");
				}
			}
		}
		fw.close();
		br.close();
	}

	public static float[] calcValidation(char[] aRef, char[] bRef, char[] aPre, char[] bPre) {
		float[] validateOut = new float[4];
		int aliRefTot = 0;
		int aliPreTot = 0;
		int aliPreCor = 0;
		for (int i = 0; i < aRef.length; i++) {
			if(aRef[i] != '-' && bRef[i] != '-') {
				aliRefTot++;
			}
		}
		for (int i = 0; i < aPre.length; i++) {
			if(aPre[i] != '-' && bPre[i] != '-') {
				aliPreTot++;
			}
		}
		validateOut[0] = aliPreCor / (float) aliRefTot;
		validateOut[1] = aliPreCor / (float) aliPreTot;
//		validateOut[2] = ;
//		validateOut[3] = ;
		return validateOut;
	}
	
	public static void main(String[] args) throws IOException {
		createValidation("/home/b/beckerr/propra/gotoh_testset.txt",
				"/home/b/beckerr/Desktop/blosum62-10-2.txt");
	}
}
