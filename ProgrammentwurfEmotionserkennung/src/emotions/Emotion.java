package emotions;

import dataTypes.Classification;

public abstract class Emotion implements Comparable<Emotion>{
	
	double plausibility;
	double belief;
	double doubt;
	protected Classification name;
	
	
	
	public Classification getName() {
		return name;
	}


	public void setName(Classification name) {
		this.name = name;
	}


	@Override
	public int compareTo(Emotion b) {
		// TODO Auto-generated method stub
		if(this.getPlausibility()<b.getPlausibility())
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		boolean equals;
		if (((Emotion)obj).name.equals(name))
		{
			equals = true;
		}
		else
		{
			equals = false;
		}
		return equals;
	}




	public double getPlausibility() {
		return plausibility;
	}


	public void setPlausibility(double plausibility) {
		this.plausibility = plausibility;
	}


	public double getBelief() {
		return belief;
	}


	public void setBelief(double belief) {
		this.belief = belief;
	}


	public double getDoubt() {
		return doubt;
	}


	public void setDoubt(double doubt) {
		this.doubt = doubt;
	}


	public Emotion(double plausibility, double belief, double doubt){
		super();
		this.plausibility = plausibility;
		this.belief = belief;
		this.doubt = doubt;
	}


	public Emotion()
	{
	
	}

}
