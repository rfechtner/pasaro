package alignmentUtils;

public class GapFunction {
	private float gapOpen;
	private float gapExtend;

	public GapFunction(int gapOpen, int gapExtend) {
		this.gapOpen = gapOpen;
		this.gapExtend = gapExtend;
	}

	public float calcPenalty(int extend) {
			return (gapExtend * extend) + gapOpen;
		}
	}

