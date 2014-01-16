package emotions;

import dataTypes.Classification;

public class Surprise extends Emotion
{
	public Surprise(float plausibility, float singleBelief, float singleDoubt)
	{
		super(plausibility, singleBelief, singleDoubt);
		name = Classification.Surprise;
	}
	public Surprise() {
		// TODO Auto-generated constructor stub
		name = Classification.Surprise;
	}
}
