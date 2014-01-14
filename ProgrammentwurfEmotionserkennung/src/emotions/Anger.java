package emotions;

import dataTypes.Classification;

public class Anger extends Emotion
{
	Classification name = Classification.Anger;
	public Anger(float plausibility, float singleBelief, float singleDoubt)
	{
		super(plausibility, singleBelief, singleDoubt);
	}
}
