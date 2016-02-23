package alignmentUtils;

public class GapFunction {
	private float gapOpen;
	private float gapExtend;

	public GapFunction(float gapOpen, float gapExtend) {
		this.gapOpen = gapOpen;
		this.gapExtend = gapExtend;
	}

	public float calcPenalty(boolean extend) {
		if(extend) {
			return gapExtend;
		}
		else {
			return gapOpen;
		}
	}
}
