package emotions;

import dataTypes.Classification;

public class Joy extends Emotion 
{
	public Joy(float plausibility, float singleBelief, float singleDoubt)
	{
		super(plausibility, singleBelief, singleDoubt);
		name = Classification.Joy;
	}
	public Joy() {
		// TODO Auto-generated constructor stub
		name = Classification.Joy;
	}
}
