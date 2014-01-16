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
		
	public void classificate(ArrayList<Frame> allFrames, int currentPosition)
	{
		this.allFrames = allFrames;
		currentFrame = allFrames.get(currentPosition);
		this.currentPosition = currentPosition;		
		currentFrame.sortEmotions();
		//for considering the timevariant of the classification always the three previous frames are considered
		//special cases: the first three frames
		switch(currentPosition)
		{
			case 0: classificFirstFrame(); break; 
			case 1: classificSecondFrame(); break;
			case 2: classificThirdFrame(); break;
			default: classficOtherFrames(); break;
		}	
		
		allFrames.get(currentPosition).setEmotionClassification(classific);
	}
	
	public void classificFirstFrame()
	{
		//no other frames with classfications there, it's the emotions with the best plausibility
		setClassificCurrentFrame(0);
	}
	
	public void setClassificCurrentFrame(int indexEmotion)
	{
		classific = currentFrame.getEmotions().get(indexEmotion).getName();
	}
	
	public void setClassificPreviousFrame(int indexFrame, Classification emotionClassification)
	{
		allFrames.get(indexFrame).setEmotionClassification(emotionClassification);
		allFrames.get(indexFrame).setClassificationChanged(true);
	}
	
	public void classificSecondFrame()
	{
		//In case most plausible emotions of the second frame is the same as of the first frame
		if  (emotionsEquals(0, (currentPosition-1)))
		{
			setClassificCurrentFrame(1);
		}
		//If the emotions of the first two frames are different
		if (!(emotionsEquals(0, (currentPosition-1))))
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
			if  (emotionsEquals(1, currentPosition-1))		
			{
				setClassificCurrentFrame(1);
			}
			else
			{
				//proove if emotion two and three have the same plausibility
				if(plausibilityEquals(1,2))
				{
					//two emotions on place 2. It must also the third emotion be considered.
					if  (emotionsEquals(2, currentPosition-1))		
					{
						setClassificCurrentFrame(2);
					}
				}
				//Otherwise it's plausible that the emotions has changed
			}
	}
	
	public void classificThirdFrame()
	{
		boolean currentEqualsFirst = emotionsEquals(0, (currentPosition-2));
		boolean currentEqualsSecond = emotionsEquals(0,(currentPosition-1));
		boolean firstEqualsSecond = allFrames.get(0).getEmotionClassification().equals(allFrames.get(1).getEmotionClassification());
		//Default: emotion is most plausible one without consindering timevariant
		setClassificCurrentFrame(0);
		
		//1. Case: all frames have same emotion -> no changes
		if (currentEqualsFirst && currentEqualsSecond)
		{
			setClassificCurrentFrame(0);
			//three time same emotion, emotion is accepted and won't be updated any more
			fixEmotion(0);
			fixEmotion(1);
			fixEmotion(2);
		}
		//2. Case: Current Frame has other emotion, change emotion as in second frame
		 if(firstEqualsSecond && !(currentEqualsSecond))
		{
			 //TODO:
			if(allFrames.get(currentPosition-1).isClassificationChanged() == true)
			{
				if(currentFrame.getEmotions().get(0).getName().equals(allFrames.get(currentPosition-1).getEmotions().get(0).getName()))
				{
					//Change was wrong, reset the change
					setClassificPreviousFrame(currentPosition-1, currentFrame.getEmotions().get(0).getName());
					allFrames.get(currentPosition-1).setClassificationChanged(false);
					//proove frame
					
				}
				else
				{
					prooveCurrentEmotionForChange();
				}
			}
			
			//TODO: nur wenn frame vorher nicht angepasst wurde
			
		}
		//3. Case: second frame other emotion, first and third one the same
		if(currentEqualsFirst && ! firstEqualsSecond)
		{
			setClassificPreviousFrame(1, allFrames.get(0).getEmotionClassification());
		}
		//4. Case: first frame other emotion
		if(currentEqualsSecond && !(firstEqualsSecond))
		{
			setClassificPreviousFrame(0, allFrames.get(1).getEmotionClassification());
		}	
	}
	
	public void classficOtherFrames()
	{
		boolean currentEqualsThird = emotionsEquals(0, (currentPosition-3));
		boolean currentEqualsSecond = emotionsEquals(0, (currentPosition-2));;
		boolean currentEqualsFirst = emotionsEquals(0, (currentPosition-1));;
		boolean thirdEqualsSecond = allFrames.get(2).getEmotionClassification().equals(allFrames.get(1).getEmotionClassification());
		boolean thirdEqualsFirst = allFrames.get(2).getEmotionClassification().equals(allFrames.get(0).getEmotionClassification());
		boolean secondEqualsFrist = allFrames.get(0).getEmotionClassification().equals(allFrames.get(1).getEmotionClassification());
		
		//Default most plausible Emotion
		setClassificCurrentFrame(0);
		//1. Case: all same Emotion
		if(secondEqualsFrist && thirdEqualsSecond && currentEqualsThird)
		{
			fixEmotion(3);
		}
		//2. Case: current frame other emotion -> change as in second frame
		if(secondEqualsFrist && thirdEqualsSecond &&!(currentEqualsSecond))
		{
			prooveCurrentEmotionForChange();
		}
		
		//3. Case current Frame same as first ,in between different -> asumption the classification in between where wrong - > update
		if (currentEqualsFirst)
		{
			setClassificPreviousFrame(2, allFrames.get(0).getEmotionClassification());
			setClassificPreviousFrame(1, allFrames.get(0).getEmotionClassification());
			fixEmotion(0);
			fixEmotion(1);
			fixEmotion(2);
			fixEmotion(3);
			
		}
	}
	
	//Set flag, that it emotion is pretty plausible and won't be changed any more (if there ware three frames with same emotion)
	public void fixEmotion(int positionFrameToFix)
	{
		allFrames.get(positionFrameToFix).setClassificationFixed(true);
		allFrames.get(positionFrameToFix).setClassificationChanged(false);
	}
	//true if Emotion of current Frame equals the emotion of a previous frame
	public boolean emotionsEquals(int indexEmotion, int positionFrame)
	{
		Classification currentEmotion = currentFrame.getEmotions().get(indexEmotion).getName();
		Classification previousEmotion = allFrames.get(positionFrame).getEmotionClassification();
		return currentEmotion.equals(previousEmotion);
	}
	
	//true if the plausibility of one emotion equals the plausibility of another emotion
	public boolean plausibilityEquals(int positionFirst, int positionSecond)
	{
		return currentFrame.getEmotions().get(positionFirst).getPlausibility() == currentFrame.getEmotions().get(positionSecond).getPlausibility();
	}
}
