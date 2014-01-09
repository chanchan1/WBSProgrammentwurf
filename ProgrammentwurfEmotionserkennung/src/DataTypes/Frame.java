package DataTypes;

import Emotions.Anger;
import Emotions.Fear;
import Emotions.Joy;
import Emotions.Surprise;

public class Frame {
	
	//Features
	int abs_furrowedbrow;
	int abs_marionettelines;
	boolean bfurrowedbrow;
	boolean bmarionettelines;
	boolean beyelid;
	
	//Emotions
	Anger anger = new Anger();
	Fear fear = new Fear();
	Joy joy = new Joy();
	Surprise surprise = new Surprise();
	
	Classification emotionclassification;
	
	
	public Frame()
	{
		
	}


	public int getAbs_furrowedbrow() {
		return abs_furrowedbrow;
	}


	public void setAbs_furrowedbrow(int abs_furrowedbrow) {
		this.abs_furrowedbrow = abs_furrowedbrow;
	}


	public int getAbs_marionettelines() {
		return abs_marionettelines;
	}


	public void setAbs_marionettelines(int abs_marionettelines) {
		this.abs_marionettelines = abs_marionettelines;
	}






	public boolean isBfurrowedbrow() {
		return bfurrowedbrow;
	}


	public void setBfurrowedbrow(boolean burrowedbrow) {
		this.bfurrowedbrow = burrowedbrow;
	}


	public boolean isBmarionettelines() {
		return bmarionettelines;
	}


	public void setBmarionettelines(boolean bmarionettelines) {
		this.bmarionettelines = bmarionettelines;
	}


	public boolean isBeyelid() {
		return beyelid;
	}


	public void setBeyelid(boolean beyelid) {
		this.beyelid = beyelid;
	}


	public Anger getAnger() {
		return anger;
	}


	public void setAnger(Anger anger) {
		this.anger = anger;
	}


	public Fear getFear() {
		return fear;
	}


	public void setFear(Fear fear) {
		this.fear = fear;
	}


	public Joy getJoy() {
		return joy;
	}


	public void setJoy(Joy joy) {
		this.joy = joy;
	}


	public Surprise getSurprise() {
		return surprise;
	}


	public void setSurprise(Surprise surprise) {
		this.surprise = surprise;
	}


	public Classification getEmotionclassification() {
		return emotionclassification;
	}


	public void setEmotionclassification(Classification emotionclassification) {
		this.emotionclassification = emotionclassification;
	}
	
	


	
}
