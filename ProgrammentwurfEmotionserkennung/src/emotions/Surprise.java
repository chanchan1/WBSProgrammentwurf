package emotions;

import dataTypes.Classification;

public class Surprise extends Emotion
{
	Classification name = Classification.Surprise;
	public Surprise(float plausibility, float singleBelief, float singleDoubt)
	{
		super(plausibility, singleBelief, singleDoubt);
	}
}
