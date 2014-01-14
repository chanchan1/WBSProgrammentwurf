package emotions;

import java.awt.List;
import java.util.ArrayList;

import dataTypes.Classification;
import dataTypes.Frame;


public class Classificator {
	
	ArrayList<Frame> allFrames;
	Frame currentFrame;
	int currentPosition;
	Classification classific = Classification.Anger;
	
	public Classificator()
	{
		
	}
	
	
	public Classification classifcate(ArrayList<Frame> allFrames, int currentPosition)
	{
		this.allFrames = allFrames;
		currentFrame = allFrames.get(currentPosition);
		this.currentPosition = currentPosition;
		//sort emotions according to there plausibility
		//TODO: Sort überschreiben, dass er nach plausibilität sortiert
		
		//TODO: im Moment wird nur Plausbilität mit einbezogen was ist mit  Belief????
		/*double plausibil_anger = frame.getAnger().getPlausibility();
		double plausibil_joy = frame.getJoy().getPlausibility();
		double plausibil_fear = frame.getFear().getPlausibility();
		double plausibil_surprise = frame.getSurprise().getPlausibility();*/
		
		
		//TODO: Klassification von frame anhand der Plausibilität
		/*double[] plausibilities = new double[]{plausibil_anger, plausibil_fear,plausibil_joy, plausibil_surprise};
		java.util.Arrays.sort(plausibilities);*/
		
		
		//TODO: Überprüfen der Klassification anhand des Verlaufs
		switch(currentPosition)
		{
		case 0: classificFirstFrame(); break; 
		case 1: classificSecondFrame(); break;
		case 2: classificThirdFrame(); break;
		case 3: classificFourthFrame(); break;
		default: classficOtherFrames(); break;
		}
		
		
		return classific;
	}
	

	
	public void classificFirstFrame()
	{
		//no other frames with classfications there, it's the emotions with the best plausibility
		setClassific(1);
	}
	
	public void setClassific(int indexEmotion)
	{
		classific = currentFrame.getEmotions().get(indexEmotion).getName();
	}
	
	public void classificSecondFrame()
	{
		//In case most plausible emotions of the second frame is the same as of the first frame
		if  (emotionsEquals(1, (currentPosition-1)))
		{
			setClassific(1);
		}
		//If the emotions of the first two frames are different
		if (!(emotionsEquals(1, (currentPosition-1))))
		{
			//secend plausibel emotion equals the emotions of the first frame, then probably the second emotion is the same
			if  (emotionsEquals(2, currentPosition-1))		
			{
				setClassific(2);
			}
			else
			{
				//proove if emotion two and three have the same plausibility
				if(plausibilityEquals(2,3))
				{
					//two emotions on place 2. It must also the third emotion be considered.
					if  (emotionsEquals(3, currentPosition-1))		
					{
						setClassific(3);
					}
				}
				//Otherwise it's plausible that the emotions has changed
			}
		}
	}
	
	public void classificThirdFrame()
	{
		//1. Case: all frames have same emotion
		if (emotionsEquals(1, (currentPosition-1))&& emotionsEquals(1,(currentPosition-2)))
		{
			
		}
		//2. Case: Current Frame has other emotion, change emotion has in second frame
		//3. Case: second frame other emotion, first and third one the same
		//4. Case: first frame other emotion
		//Others: most plausible one
	}
	public void classificFourthFrame()
	{
	}
	
	public void classficOtherFrames()
	{
	
	}

	public boolean emotionsEquals(int positionEmotion, int positionFrame)
	{
		return currentFrame.getEmotions().get(positionEmotion).getName().equals(allFrames.get(positionFrame).getEmotionClassification());
	}
	
	public boolean plausibilityEquals(int positionFirst, int positionSecond)
	{
		return currentFrame.getEmotions().get(positionFirst).getPlausibility() == currentFrame.getEmotions().get(positionSecond).getPlausibility();
	}
	
	

}
