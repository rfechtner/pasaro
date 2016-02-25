package alignment;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public abstract class Alignment {
	protected SequencePair sequence;
	protected SequencePair finalAlignment;
	protected GapFunction gapFunction;
	protected ScoringMatrix scoringMatrix;
	protected int[][] aMatrix = new int[0][0];
	protected int[][] iMatrix;
	protected int[][] dMatrix;
	protected ArrayList<Integer> path = new ArrayList<Integer>();

	public Alignment(SequencePair sequence, GapFunction gapFunction,
			ScoringMatrix scoringMatrix) {
		this.sequence = sequence;
		this.gapFunction = gapFunction;
		this.scoringMatrix = scoringMatrix;
	}

//	creates the alignment with given sequences
	public void make() {
		initMatrix();
		fillMatrix();
	}

//	creates image with dynamic programming matrix in specified folder
	public void dpMatrices(String dir) throws IOException {
		int sizeX = (sequence.getSequenceA().length+2) * 50;
		int sizeY = (sequence.getSequenceB().length+3) * 15;
		String path = dir+sequence.getNameA()+"_"+sequence.getNameB()+ ".png";
		File file = new File(path);
		file.createNewFile();
		BufferedImage image = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = image.createGraphics();
		g.setPaint(Color.white);
		g.fillRect ( 0, 0, image.getWidth(), image.getHeight() );
		g.setPaint(Color.black);
		g.setFont(new Font(Font.MONOSPACED, 0, 12));
		String row;
		row = String.format("%14s", "-");
		for (int i = 0; i < sequence.getSequenceA().length; i++) {
			row += String.format("%7s", sequence.getSequenceA()[i]);
		}
		g.drawString(row, 0, 15);
		for (int i = 0; i < aMatrix[0].length; i++) {

			if(i == 0) {
				row = String.format("%7s", "-");
			}else {
				row = String.format("%7s", sequence.getSequenceB()[i-1]);
			}
			for (int j = 0; j < aMatrix.length; j++) {
					row += String.format("%7s", String.format("%.2f",(aMatrix[j][i] / 1000f)));
			}
			g.drawString(row, 0, (i*15+30));
		}
		g.dispose();
		ImageIO.write(image, "png", file);
	}

//	calculates score of final alignment
	public float checkScore() {
		return 0;
	}

//	calculates score of global alignment with recursive method
	public float recScore(int i, int j) {
		float gap1;
		float gap2;
		float match;
		if (i == 0 && j == 0) {
			return 0;
		} else {
			if (i == 0) {
				gap1 = Integer.MIN_VALUE;
			} else {
				gap1 = recScore(i - 1, j) + gapFunction.calcPenalty(1);
			}
			if (j == 0) {
				gap2 = Integer.MIN_VALUE;
			} else {
				gap2 = recScore(i, j - 1) + gapFunction.calcPenalty(1);
			}
			if (i != 0 && j != 0) {
				match = recScore(i - 1, j - 1)
						+ scoringMatrix.getScore(
								sequence.getSequenceA()[i - 1],
								sequence.getSequenceB()[j - 1]);
			} else {
				match = Integer.MIN_VALUE;
			}
		}
		return Math.max(match, Math.max(gap1, gap2));
	}

//	calculates position i,j in dp-matrix for linear gap costs
	public void calcMatrix(int i, int j) {
		int gap1 = aMatrix[i][j - 1] + gapFunction.calcPenalty(1);
		int gap2 = aMatrix[i - 1][j] + gapFunction.calcPenalty(1);
		int match = aMatrix[i - 1][j - 1]
				+ scoringMatrix.getScore(
						sequence.getSequenceA()[i - 1],
						sequence.getSequenceB()[j - 1]);
		aMatrix[i][j] = Math.max(match, Math.max(gap1, gap2));
	}

//	calculates position i,j in dp-matrix for gotoh alignment
	public void calcGotoh(int i, int j) {
		int opI = aMatrix[i][j - 1] + gapFunction.calcPenalty(1);
		int extI = iMatrix[i][j - 1] + gapFunction.getGapExtend();
//		System.out.println(iMatrix[i][j - 1] + " " + gapFunction.getGapExtend() + " " + extI);
		iMatrix[i][j] = Math.max(opI, extI);
		int opD = aMatrix[i - 1][j] + gapFunction.calcPenalty(1);
		int extD = dMatrix[i - 1][j] + gapFunction.getGapExtend();
		dMatrix[i][j] = Math.max(opD, extD);
		int match = aMatrix[i - 1][j - 1]
				+ scoringMatrix.getScore(sequence.getSequenceA()[i - 1],
						sequence.getSequenceB()[j - 1]);
		int ins = iMatrix[i][j];
		int del = dMatrix[i][j];
		aMatrix[i][j] = Math.max(match, Math.max(ins, del));
	}

	public void printResult() {
		System.out.println(finalAlignment.getScore());
		System.out.println(finalAlignment.getSequenceA());
		System.out.println(finalAlignment.getSequenceB());
		for (int i = 0; i < path.size(); i++) {
			System.out.print(path.get(i) + " ");
		}
		System.out.print("\n");
	}

//	prints dp-matrix to standart out
	public void printMatrix() {
		for (int i = 0; i < aMatrix[0].length; i++) {
			String row = "";
			for (int j = 0; j < aMatrix.length; j++) {
				row += String.format("%20d", aMatrix[j][i]);
			}
			System.out.println(row);
		}
	}

//	initializes default values of dp-matrix
	public void initMatrix() {
		aMatrix = new int[sequence.getSequenceA().length + 1][sequence.getSequenceB().length + 1];
	}

//	initializes insertion and deletion matrix
	public void initID() {
		iMatrix = new int[sequence.getSequenceA().length + 1][sequence
				.getSequenceB().length + 1];
		dMatrix = new int[sequence.getSequenceA().length + 1][sequence
				.getSequenceB().length + 1];
		for (int i = 1; i < iMatrix.length; i++) {
			iMatrix[i][0] = Integer.MIN_VALUE / 2;
		}
		for (int j = 1; j < dMatrix[0].length; j++) {
			dMatrix[0][j] = Integer.MIN_VALUE / 2;
		}
	}

//	fills out dp-matrix with the dynamic programming method
	public void fillMatrix() {
		for (int i = 1; i < aMatrix.length; i++) {
			for (int j = 1; j < aMatrix[0].length; j++) {
				calcMatrix(i, j);
			}
		}
	}

//	does traceback for gotoh alignments
	public void gotohBacktrack(int i, int j, int end) {
		String a = "";
		String b = "";
		float finalScore = aMatrix[i][j] / 1000f;
		if(i != sequence.getSequenceA().length) {
			for(int x = i; x < sequence.getSequenceA().length; x++) {
				a += sequence.getSequenceA()[x];
				b += "-";
			}
		}
		if(j != sequence.getSequenceB().length) {
			for(int x = j; x < sequence.getSequenceB().length; x++) {
				a += "-";
				b += sequence.getSequenceB()[x];
			}
		}
		while(i != 0 && j != 0) {
			if(aMatrix[i][j] == end) {
				break;
			}
			path.add(i);
			path.add(j);
			if(aMatrix[i][j] == iMatrix[i][j]) {
				do {
					j--;
					a = "-" + a;
					b = sequence.getSequenceB()[j] + b;
				}while(aMatrix[i][j] + gapFunction.calcPenalty(1) != iMatrix[i][j+1]);
			}else if(aMatrix[i][j] == dMatrix[i][j]) {
				do {
					i--;
					a = sequence.getSequenceA()[i] + a;
					b = "-" + b;
				}while(aMatrix[i][j] + gapFunction.calcPenalty(1) != dMatrix[i+1][j]);
			}else {
				i = i-1;
				j = j-1;
				a = sequence.getSequenceA()[i] + a;
				b = sequence.getSequenceB()[j] + b;
			}
		}
		if(i != 0) {
			for(int x = i-1; x >= 0; x--) {
				a = sequence.getSequenceA()[x] + a;
				b = "-" + b;
			}
		}
		if (j != 0){
			for(int x = j-1; x >= 0; x--) {
				a = "-" + a;
				b = sequence.getSequenceB()[x] + b;
			}
		}
		SequencePair out = new SequencePair(a, b, sequence.getNameA(), sequence.getNameB(), String.format("%.4f", finalScore).replace(',', '.'));
		finalAlignment = out;
//		printResult();
	}

//	does traceback linear gap cost alignments
	public void backtrack(int i, int j, int end) {
		String a = "";
		String b = "";
		float finalScore = aMatrix[i][j] / 1000f;
		if(i != sequence.getSequenceA().length) {
			for(int x = i; x < sequence.getSequenceA().length; x++) {
				a += sequence.getSequenceA()[x];
				b += "-";
			}
		}
		if(j != sequence.getSequenceB().length) {
			for(int x = j; x < sequence.getSequenceB().length; x++) {
				a += "-";
				b += sequence.getSequenceB()[x];
			}
		}
		while(i != 0 && j != 0) {
			if(aMatrix[i][j] == end) {
				break;
			}
			path.add(i);
			path.add(j);
			if(aMatrix[i][j] == aMatrix[i-1][j] + gapFunction.calcPenalty(1)){
				i = i-1;
				a = sequence.getSequenceA()[i] + a;
				b = "-" + b;
			}else if(aMatrix[i][j] == aMatrix[i][j-1] + gapFunction.calcPenalty(1)) {
				j = j-1;
				a = "-" + a;
				b = sequence.getSequenceB()[j] + b;
			}else {
				i = i-1;
				j = j-1;
				a = sequence.getSequenceA()[i] + a;
				b = sequence.getSequenceB()[j] + b;
			}
		}
		if(i != 0) {
			for(int x = i-1; x >= 0; x--) {
				a = sequence.getSequenceA()[x] + a;
				b = "-" + b;
			}
		}
		if (j != 0){
			for(int x = j-1; x >= 0; x--) {
				a = "-" + a;
				b = sequence.getSequenceB()[x] + b;
			}
		}
		finalAlignment = new SequencePair(a, b, sequence.getNameA(), sequence.getNameB(), String.format("%.4f", finalScore).replace(',', '.'));
	}

	public SequencePair getFinalAlignment() {
		return finalAlignment;
	}
}
