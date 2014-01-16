package emotions;

import dataTypes.Classification;

public class Fear extends Emotion
{
	public Fear(float plausibility, float singleBelief, float singleDoubt)
	{
		super(plausibility, singleBelief, singleDoubt);
		name = Classification.Fear;
	}
	public Fear() {
		// TODO Auto-generated constructor stub
		name = Classification.Fear;
	}

}
