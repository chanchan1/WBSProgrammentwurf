package dataTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import emotions.Anger;
import emotions.Emotion;
import emotions.Fear;
import emotions.Joy;
import emotions.Surprise;

public class Frame {
	
	//Features
	int abs_furrowedbrow;
	int abs_marionettelines;
	boolean bfurrowedbrow;
	boolean bmarionettelines;
	boolean beyelid;
	
	boolean classificationFixed = false;
	boolean classificationChanged = false;
	
	public boolean isClassificationChanged() {
		return classificationChanged;
	}


	public void setClassificationChanged(boolean classificationChanged) {
		this.classificationChanged = classificationChanged;
	}


	//Emotions
	Anger anger = new Anger();
	Fear fear = new Fear();
	Joy joy = new Joy();
	Surprise surprise = new Surprise();
	
	//TODO: Mergen, dass Emotionen in der Liste eine Referenz auf die anderen
	private ArrayList<Emotion> emotions = new ArrayList<Emotion>(Arrays.asList(anger, fear, joy, surprise));

	
	Classification emotionClassification;
	
	
	
	public Frame()
	{
	}
	
	
	public Frame(int abs_furrowedbrow, int abs_marionettelines, boolean beyelid){
		super();
		this.abs_furrowedbrow = abs_furrowedbrow;
		this.abs_marionettelines = abs_marionettelines;
		this.beyelid = beyelid;
	}


	public void sortEmotions()
	{
		Collections.sort(emotions);
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


	public Classification getEmotionClassification() {
		return emotionClassification;
	}

	public void setEmotionClassification(Classification emotionClassification) {
		this.emotionClassification = emotionClassification;
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
	




	public ArrayList<Emotion> getEmotions() {
		return emotions;
	}


	public void setEmotions(ArrayList<Emotion> emotions) {
		this.emotions = emotions;
	}


	public boolean isClassificationFixed() {
		return classificationFixed;
	}


	public void setClassificationFixed(boolean classificationFixed) {
		this.classificationFixed = classificationFixed;
	}
	
	


	
}
