package emotions;

import dataTypes.Classification;

public class Joy extends Emotion 
{
	Classification name = Classification.Joy;
	public Joy(float plausibility, float singleBelief, float singleDoubt)
	{
		super(plausibility, singleBelief, singleDoubt);
	}
}
