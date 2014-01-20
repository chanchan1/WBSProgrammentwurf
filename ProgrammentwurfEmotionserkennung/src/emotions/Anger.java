package emotions;

import dataTypes.Classification;

public class Anger extends Emotion
{
	
	public Anger(float plausibility, float singleBelief, float singleDoubt)
	{
		super(plausibility, singleBelief, singleDoubt);
		name = Classification.Anger;
	}
	public Anger() {
		name = Classification.Anger;
	}
}
