package emotions;

import java.awt.List;
import java.util.ArrayList;

import dataTypes.Classification;
import dataTypes.Frame;


public class Classificator {
	
	ArrayList<Classification> allClassifactions;
	Frame frame;
	
	public Classificator()
	{
		
	}
	
	
	public Classification classifcate(ArrayList<Classification> allClassifactions, Frame frame, int position)
	{
		this.allClassifactions = allClassifactions;
		this.frame = frame;
		Classification classific = Classification.Anger;
		//TODO: im Moment wird nur Plausbilität mit einbezogen was ist mit Zweifel und Belief????
		double plausibil_anger = frame.getAnger().getPlausibility();
		double plausibil_joy = frame.getJoy().getPlausibility();
		double plausibil_fear = frame.getFear().getPlausibility();
		double plausibil_surprise = frame.getSurprise().getPlausibility();
		
		
		//TODO: Klassification von frame anhand der Plausibilität
		double[] plausibilities = new double[]{plausibil_anger, plausibil_fear,plausibil_joy, plausibil_surprise};
		java.util.Arrays.sort(plausibilities);
		
		
		//TODO: Überprüfen der Klassification anhand des Verlaufs
		switch(position)
		{
		case 0: break; //no other frames with classfications there
		case 1: classficSecondFrame(); break;
		default: classficOtherFrames(); break;
		}
		//first frame 
		
		
		return classific;
	}
	

	
	public void classficSecondFrame()
	{

	}
	
	public void classficOtherFrames()
	{
	
	}

}
