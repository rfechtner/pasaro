package alignmentUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import parameterUtils.ParamException;

public class ScoringMatrix {
	private float[][] scoringMatrix;

	public ScoringMatrix(String file) {
		this.scoringMatrix = parseMat(file);
	}
	
	public static float[][] parseMat(String file){
		float[][] scoringMatrix = new float[26][26];
		
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String line = "";
			
			char[] rowindex = new char[26];
			char[] columnindex = new char[26];
			
			int count = 0;
			while( (line = br.readLine()) != null) {
				if (line.startsWith("ROWINDEX")) {
					String[] fields = line.split(" ");
					rowindex = fields[1].toCharArray();
				}
				
				if (line.startsWith("COLINDEX")) {
					String[] fields = line.split(" ");
					columnindex = fields[1].toCharArray();
				}
				
				if (line.startsWith("MATRIX")){
					String[] fields = line.split("[ \t]+");
					
					for(int i = 1; i < fields.length; i++){
						int x = (int) rowindex[count] - 65;
						int y = (int) columnindex[i-1] - 65;
						float score = Float.parseFloat(fields[i].substring(0, fields[i].length() - 1));
						scoringMatrix[x][y] = score; 
						scoringMatrix[y][x] = score;
					}
					
					count++;
				}
			}
			
			br.close();
			
		} catch (IOException e) {
			try {
				throw new ParamException();
			} catch (ParamException e1) {
				ParamException.printHelp();
			}
		}
		
		return scoringMatrix;
	}
		
	public float getScore(char a, char b){
		return scoringMatrix[(int) a - 65][(int) b - 65];
	}

	@Override
	public String toString() {
		return "ScoringMatrix [scoringMatrix=" + Arrays.toString(scoringMatrix) + "]";
	}
	
}
