package emotions;

public abstract class Emotion {
	
	double plausibility;
	double belief;
	double doubt;
	
	
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
