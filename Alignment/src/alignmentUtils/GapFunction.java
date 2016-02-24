package alignmentUtils;

public class GapFunction {
	private float gapOpen;
	private float gapExtend;

	public GapFunction(float gapOpen, float gapExtend) {
		this.gapOpen = gapOpen;
		this.gapExtend = gapExtend;
	}

	public float calcPenalty(int extend) {
		return (gapExtend * extend) + gapOpen;

	}

	public float getGapOpen() {
		return gapOpen;
	}

	public float getGapExtend() {
		return gapExtend;
	}
}
