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
		
	public Classification classificate(ArrayList<Frame> allFrames, int currentPosition)
	{
		this.allFrames = allFrames;
		currentFrame = allFrames.get(currentPosition);
		this.currentPosition = currentPosition;		
		
		//for considering the timevariant of the classification always the three previous frames are considered
		//special cases: the first three frames
		switch(currentPosition)
		{
			case 0: classificFirstFrame(); break; 
			case 1: classificSecondFrame(); break;
			case 2: classificThirdFrame(); break;
			default: classficOtherFrames(); break;
		}		
		return classific;
	}
	
	public void classificFirstFrame()
	{
		//no other frames with classfications there, it's the emotions with the best plausibility
		setClassific(1);
	}
	
	//TODO: Refactor to setClassificCurrentFrame
	public void setClassific(int indexEmotion)
	{
		classific = currentFrame.getEmotions().get(indexEmotion).getName();
	}
	
	public void setClassificPreviousFrame(int indexFrame, Classification emotionClassification)
	{
		allFrames.get(indexFrame).setEmotionClassification(emotionClassification);
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
			prooveCurrentEmotionForChange();			
		}
	}
	
	/*	if Current and Previous Frame are different, it's to proove:
		if one current frame the second most plausible emotion is the one of the previous frame, if it is so -> update classification, else -> do nothing
		example: previous emotion: Anger; Current Emotion: 1. plausible one Surprise 2. plausible Anger -> Classificaiton changed to Anger
	*/
	public void prooveCurrentEmotionForChange()
	{
		//secend plausibel emotion equals the emotions of the previous frame, then probably the second emotion is the same
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
	
	public void classificThirdFrame()
	{
		boolean currentEqualsFirst = emotionsEquals(1, (currentPosition-2));
		boolean currentEqualsSecond = emotionsEquals(1,(currentPosition-1));
		boolean firstEqualsSecond = allFrames.get(1).getEmotionClassifcation().equals(allFrames.get(2).getEmotionClassifcation());
		//Default: emotion is most plausible one without consindering timevariant
		setClassific(1);
		
		//1. Case: all frames have same emotion -> no changes
		if (currentEqualsFirst && currentEqualsSecond)
		{
			setClassific(1);
			//three time same emotion, emotion is accepted and won't be updated any more
			fixEmotion(1);
			fixEmotion(2);
			fixEmotion(3);
		}
		//2. Case: Current Frame has other emotion, change emotion as in second frame
		 if(firstEqualsSecond && !(currentEqualsSecond))
		{
			prooveCurrentEmotionForChange();
		}
		//3. Case: second frame other emotion, first and third one the same
		if(currentEqualsFirst && ! firstEqualsSecond)
		{
			setClassificPreviousFrame(2, allFrames.get(1).getEmotionClassifcation());
		}
		//4. Case: first frame other emotion
		if(currentEqualsSecond && !(firstEqualsSecond))
		{
			setClassificPreviousFrame(1, allFrames.get(2).getEmotionClassifcation());
		}	
	}
	
	public void classficOtherFrames()
	{
		boolean currentEqualsThird = emotionsEquals(1, (currentPosition-3));
		boolean currentEqualsSecond = emotionsEquals(1, (currentPosition-2));;
		boolean currentEqualsFirst = emotionsEquals(1, (currentPosition-1));;
		boolean thirdEqualsSecond =;
		boolean thirdEqualsFirst =;
		boolean secondEqualsFrist =;
		
		//Default most plausible Emotion
		setClassific(1);
		//1. Case: all same Emotion
		if(secondEqualsFirst && thirdEqualsSecond && currentEqualsThird)
		{
			fixEmotion(4);
		}
		//2. Case: current frame other emotion -> change as in second frame
		if(firstEqualsSecond && thirdEqualsSecond &&!(currentEqualsSecond))
		{
			prooveCurrentEmotionForChange();
		}
		
		//3. Case current Frame same as first ,in between different -> asumption the classification in between where wrong - > update
		if (currentEqualsFirst)
		{
			setClassificPreviousFrame(3, allFrames.get(1).getEmotionClassifcation());
			setClassificPreviousFrame(2, allFrames.get(1).getEmotionClassifcation());
			fixEmotion(1);
			fixEmotion(2);
			fixEmotion(3);
			fixEmotion(4);
		}
	}
	
	//Set flag, that it emotion is pretty plausible and won't be changed any more (if there ware three frames with same emotion)
	public void fixEmotion(int positionFrameToFix)
	{
		allFrames.get(positionFrameToFix).setClassificationFixed(true);
	}
	//true if Emotion of current Frame equals the emotion of a previous frame
	public boolean emotionsEquals(int positionEmotion, int positionFrame)
	{
		return currentFrame.getEmotions().get(positionEmotion).getName().equals(allFrames.get(positionFrame).getEmotionClassification());
	}
	
	//true if the plausibility of one emotion equals the plausibility of another emotion
	public boolean plausibilityEquals(int positionFirst, int positionSecond)
	{
		return currentFrame.getEmotions().get(positionFirst).getPlausibility() == currentFrame.getEmotions().get(positionSecond).getPlausibility();
	}
}
