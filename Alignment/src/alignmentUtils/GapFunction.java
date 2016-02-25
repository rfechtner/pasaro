package alignmentUtils;

public class GapFunction {
	private int gapOpen;
	private int gapExtend;

	public GapFunction(float gapOpen, float gapExtend) {
		this.gapOpen = (int)(gapOpen * 1000);
		this.gapExtend = (int)(gapExtend * 1000);
	}

	public int calcPenalty(int extend) {
		return (gapExtend * extend) + gapOpen;

	}

	public int getGapOpen() {
		return gapOpen;
	}

	public int getGapExtend() {
		return gapExtend;
	}
}
