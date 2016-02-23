package alignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ScoringMatrix {
	private int[][] scoringMatrix;

	public ScoringMatrix(String file) {
		
	}
	
	public static int[][] parseMat(String file){
		int[][] scoringMatrix;
		
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String line = "";
			while( (line = br.readLine()) != null) {
				
			}
			
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return scoringMatrix;
	}
	
	public void fillMatrix(String name) {
		
	}
	
	public int getScore(char a, char b) {
		//zu testzwecken
		if(a == b) {
			return 3;
		}else {
			return -2;
		}
	}
}
