package alignment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import alignmentUtils.GapFunction;
import alignmentUtils.ScoringMatrix;
import alignmentUtils.SequencePair;

public class FixedPoint extends Alignment{
	private SequencePair lead;
	private SequencePair trail;
	private int a;
	private int b;
	public FixedPoint(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix, int a, int b) {
		super(sequence, gapFunction, scoringMatrix);
		lead = new SequencePair(String.valueOf(sequence.getSequenceA()).substring(0,a), String.valueOf(sequence.getSequenceB()).substring(0,b), "", "");
		trail = new SequencePair(String.valueOf(sequence.getSequenceA()).substring(a+1), String.valueOf(sequence.getSequenceB()).substring(b+1), "", "");
	}

	public void make() {
		GotohGlobal g1 = new GotohGlobal(lead, gapFunction, scoringMatrix);
		GotohGlobal g2 = new GotohGlobal(trail, gapFunction, scoringMatrix);
		g1.make();
		g2.make();
		char cA = sequence.getSequenceA()[a];
		char cB = sequence.getSequenceB()[b];
		float fixedScore = scoringMatrix.getScore(cA, cB); 
		this.finalAlignment = new SequencePair(String.valueOf(g1.finalAlignment.getSequenceA()) + cA + String.valueOf(g2.finalAlignment.getSequenceA()), 
				String.valueOf(g1.finalAlignment.getSequenceB()) + cB + String.valueOf(g2.finalAlignment.getSequenceB()), 
				sequence.getNameA(), sequence.getNameB(), fixedScore + g1.finalAlignment.getScore() + g2.finalAlignment.getScore());
	}
	
	public static void genFixedPointGraphic(SequencePair sequence, GapFunction gapFunction, ScoringMatrix scoringMatrix, String dir) throws IOException {
		float[][] scores = new float[sequence.getSequenceA().length][sequence.getSequenceB().length];
		float maxScore = Integer.MIN_VALUE;
		float minScore = Integer.MAX_VALUE;
		for (int i = 0; i < sequence.getSequenceA().length; i++) {
			for (int j = 0; j < sequence.getSequenceB().length; j++) {
				FixedPoint f = new FixedPoint(sequence, gapFunction, scoringMatrix, i, j);
				f.make();
				float score = f.finalAlignment.getScore();
				scores[i][j] = score;
				if(score > maxScore) maxScore = score;
				if(score < minScore) minScore = score;
			}
		}
		float scale = 1 / Math.abs(minScore - maxScore);
		String path = dir+sequence.getNameA()+"_"+sequence.getNameB()+ "_fp.png";
		File file = new File(path);
		file.createNewFile();
		BufferedImage image = new BufferedImage(sequence.getSequenceA().length, sequence.getSequenceB().length, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		for (int i = 0; i < scores[0].length; i++) {
			for (int j = 0; j < scores.length; j++) {
				g.setColor(Color.getHSBColor(0.66f, 0.96f - (scores[j][i] - minScore) * scale, (scores[j][i] - minScore) * scale));
				g.drawRect(j, i, 1, 1);
			}
		}
		g.dispose();
		ImageIO.write(image, "png", file);
	}
}
