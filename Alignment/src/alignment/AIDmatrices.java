package alignment;

public class AIDmatrices {
	private int[][] aMatrix;
	private int[][] bMatrix;
	private int[][] cMatrix;
	
	public AIDmatrices(int[][] aMatrix, int[][] bMatrix, int[][] cMatrix) {
		super();
		this.aMatrix = aMatrix;
		this.bMatrix = bMatrix;
		this.cMatrix = cMatrix;
	}
	
	public int[][] getaMatrix() {
		return aMatrix;
	}
	
	public int[][] getbMatrix() {
		return bMatrix;
	}
	
	public int[][] getcMatrix() {
		return cMatrix;
	}	
}
