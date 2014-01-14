package emotions;

import dataTypes.Classification;

public class Fear extends Emotion
{
	Classification name = Classification.Fear;
	public Fear(float plausibility, float singleBelief, float singleDoubt)
	{
		super(plausibility, singleBelief, singleDoubt);
	}

}
