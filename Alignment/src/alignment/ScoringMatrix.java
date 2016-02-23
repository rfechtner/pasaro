package alignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class ScoringMatrix {
	private int[][] scoringMatrix;

	public ScoringMatrix(String file) {
		this.scoringMatrix = parseMat(file);
	}
	
	public static int[][] parseMat(String file){
		int[][] scoringMatrix = new int[26][26];
		
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
						int score = Integer.parseInt(fields[i].substring(0, fields[i].length() - 1));
						scoringMatrix[x][y] = score; 
						scoringMatrix[y][x] = score;
					}
					
					count++;
				}
			}
			
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return scoringMatrix;
	}
	
	public int getScore(char a, char b) {
		if(a == b) {
			return 3;
		}else {
			return -2;
		}
	}
	
	public int getScoreNew(char a, char b){
		return scoringMatrix[(int) a - 65][(int) b - 65];
	}

	@Override
	public String toString() {
		return "ScoringMatrix [scoringMatrix=" + Arrays.toString(scoringMatrix) + "]";
	}
	
}
