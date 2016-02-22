package alignment;

public class GapFunction {
	private int gapOpen;
	private int gapExtend;

	public GapFunction(int gapOpen, int gapExtend) {
		this.gapOpen = gapOpen;
		this.gapExtend = gapExtend;
	}

	public int calcPenalty(boolean extend) {
		return 0;
	}
}
