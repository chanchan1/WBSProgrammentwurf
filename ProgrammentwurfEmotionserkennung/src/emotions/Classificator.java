package emotions;

import java.util.ArrayList;

import dataTypes.Classification;
import dataTypes.Frame;


public class Classificator {
	
	ArrayList<Frame> allFrames;
	Frame currentFrame;
	int currentPosition;
	Classification classific = Classification.Anger;
	
	public Classificator(ArrayList<Frame> allFrames)
	{
		this.allFrames = allFrames;
	}
	
	/**
	 * classificate the frame in the array allFrames on the position currentPosition considering the plausibilities of 
	 * the emotions and the variation in time (the emotions of the three previous frames)
	 * @param allFrames 
	 * @param currentPosition in the array allFrames, which frame should be classicated
	 */
	public void classificate(Frame currentFrame)
	{
		
		this.currentFrame = currentFrame;
		this.currentPosition = allFrames.indexOf(currentFrame);		
		
		adjustPlausibility(currentFrame);
		currentFrame.sortEmotions();
		
		//for considering the variation in time of the classification always the three previous frames are considered
		//special cases: the first three frames (there are not the previous frames)
		switch(currentPosition)
		{
			case 0: classificFirstFrame(); break; 
			case 1: classificSecondFrame(); break;
			case 2: classificThirdFrame(); break;
			default: classficOtherFrames(); break;
		}	
		
		allFrames.get(currentPosition).setEmotionClassification(classific);
	}
	
	/**
	 * no other frames with classfications there, it's the emotions with the best plausibility
	 */
	public void classificFirstFrame()
	{
		setClassificCurrentFrame(0, false);
	}
	
	/**
	 * sets the classificaiton of the Frame which is to classficate (default most plausible one) and sets the attribute classfication changed if 
	 * the classfication has changed from the most plausible under considering the variation in time
	 * @param indexEmotion index of the emotion which is the classficaiton (e.g. default index = 0 -> most plausible one)
	 * @param changed boolean value if the classifcation is adapded from the most plausible one two another (true) or not (false)
	 */
	public void setClassificCurrentFrame(int indexEmotion, boolean changed)
	{
		classific = currentFrame.getEmotions().get(indexEmotion).getName();
		currentFrame.setClassificationChanged(changed);
	}
	
	/** 
	 * sets the classfication of a previous frame under considering the variation in time
	 * @param indexFrame position of the Frame of which the classification should be changed
	 * @param emotionClassification the emotion to which the emotion should be changed
	 */
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
			setClassificCurrentFrame(1, false);
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
	/**
	 * prove if the current emotion of the frame (most plausible one) must be changed to the emotion of the previous frame
	 * if the emotion of the previous frame is the second plausible one of the current frame 
	 * e.g. previous Frame Classification Anger; current Frame: 1. plausible Emotion: Surprise, 2. plausible: Anger
	 * @return true if current classification must be changed
	 */
	public boolean prooveCurrentEmotionForChange()
	{
		boolean changed = false;
		//second plausible emotion equals the emotions of the previous frame, then probably the second emotion is the same
			if  (emotionsEquals(1, currentPosition-1))		
			{
				setClassificCurrentFrame(1, true);
				changed = true;
			}
			else
			{
				//prove if emotion two and three have the same plausibility
				if(plausibilityEquals(1,2))
				{
					//two emotions on place 2. It must also the third emotion be considered.
					if  (emotionsEquals(2, currentPosition-1))		
					{
						setClassificCurrentFrame(2, true);
						changed = true;
					}
				}
				//Otherwise it's plausible that the emotions has changed
			}
			return changed;
	}
	
	public void classificThirdFrame()
	{
		boolean currentEqualsFirst = emotionsEquals(0, (currentPosition-2));
		boolean currentEqualsSecond = emotionsEquals(0,(currentPosition-1));
		boolean firstEqualsSecond = allFrames.get(0).getEmotionClassification().equals(allFrames.get(1).getEmotionClassification());
		//Default: emotion is most plausible one without consindering timevariant
		setClassificCurrentFrame(0, false);
		
		//1. Case: all frames have same emotion -> no changes
		if (currentEqualsFirst && currentEqualsSecond)
		{
			setClassificCurrentFrame(0, false);
			//three time same emotion, emotion is accepted and won't be updated any more
			fixEmotion(0);
			fixEmotion(1);
			fixEmotion(2);
		}
		//2. Case: Current Frame has other emotion
		 if(firstEqualsSecond && !(currentEqualsSecond))
		{
			prooveAdjustCurrentOrPreviousFrame();	
		}
		 
		//3. Case: second frame other emotion, first and third one the same
		if(currentEqualsFirst && ! firstEqualsSecond)
		{
			setClassificPreviousFrame(1, allFrames.get(0).getEmotionClassification());
			fixEmotion(0);
			fixEmotion(1);
			fixEmotion(2);
		}
		//4. Case: first frame other emotion
		if(currentEqualsSecond && !(firstEqualsSecond))
		{
			setClassificPreviousFrame(0, allFrames.get(1).getEmotionClassification());
		}	
	}
	
	/**
	 * if the current an previous Emotion differ -> prove which one should be changed
	 * changes the previous emotion, if this has the most plausible emotion as current frame, but was because of considering the variation in time changed
	 * 	if previous emotion must be changed also second previous emotion must be proved for change
	 * if previous emotion won't be changed, prove if current one has to be changed.
	 */
	public void prooveAdjustCurrentOrPreviousFrame()
	{
		//prove if previous emotions was changed
		if(allFrames.get(currentPosition-1).isClassificationChanged() == true)
		{
			//prove if before the change emotion map to the current most plausible emotion emotion
			if(currentFrame.getEmotions().get(0).getName().equals(allFrames.get(currentPosition-1).getEmotions().get(0).getName()))
			{
				//Change was wrong, reset the change
				setClassificPreviousFrame(currentPosition-1, currentFrame.getEmotions().get(0).getName());
				allFrames.get(currentPosition-1).setClassificationFixed(false);
				//prove frame at position-2 if emotion is fixed, no change, else adjust emotion to current one
				if(!allFrames.get(currentPosition-2).isClassificationFixed())
				{
					allFrames.get(currentPosition-2).setEmotionClassification(currentFrame.getEmotions().get(0).getName());
					allFrames.get(currentPosition-2).setClassificationChanged(true);
				}					
			}
			else
			{
				prooveCurrentEmotionForChange();
			}				
		}
	}
	
	public void classficOtherFrames()
	{
		boolean currentEqualsThird = emotionsEquals(0, (currentPosition-3));
		boolean currentEqualsSecond = emotionsEquals(0, (currentPosition-2));;
		boolean currentEqualsFirst = emotionsEquals(0, (currentPosition-1));;
		boolean thirdEqualsSecond = allFrames.get(currentPosition-3).getEmotionClassification().equals(allFrames.get(currentPosition-2).getEmotionClassification());
		boolean thirdEqualsFirst = allFrames.get(currentPosition-3).getEmotionClassification().equals(allFrames.get(currentPosition-1).getEmotionClassification());
		boolean secondEqualsFrist = allFrames.get(currentPosition-1).getEmotionClassification().equals(allFrames.get(currentPosition-2).getEmotionClassification());
		
		//Default: most plausible Emotion
		setClassificCurrentFrame(0, false);
		//1. Case: all same Emotion A,A,A, A
		if(secondEqualsFrist && thirdEqualsSecond && currentEqualsThird)
		{
			fixEmotion(3);
		}
		//2. Case: current frame other emotion, e.g. A, A, A  S
		if(secondEqualsFrist && thirdEqualsSecond &&!(currentEqualsSecond))
		{
			boolean changed = prooveCurrentEmotionForChange();
			if(changed ==true)
			{
				fixEmotion(currentPosition);
			}
		}
		// 3.Case third and second previous frame equal, first and current different from another and from third and second, e.g. J, J, S, A
		if(thirdEqualsSecond && !(secondEqualsFrist) && !(currentEqualsFirst))
		{
			prooveCurrentEmotionForChange();
		}
		if((secondEqualsFrist) && !(thirdEqualsSecond) &&!(currentEqualsFirst))
		{
			prooveAdjustCurrentOrPreviousFrame();
		}
		
		//4. Case current Frame same as first ,in between different e.g. A S S A -> assumption the classification in between where wrong - > update
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
	
	/**
	 * Set flag, that it emotion is pretty plausible and won't be changed any more (if there ware three frames with same emotion)
	 * @param positionFrameToFix position of the frame in the array allFrames
	 */
	public void fixEmotion(int positionFrameToFix)
	{
		allFrames.get(positionFrameToFix).setClassificationFixed(true);
		allFrames.get(positionFrameToFix).setClassificationChanged(false);
	}

	/**
	 * checks if one emotion of the current frame and of a previous frame equals
	 * @param indexEmotion of the emotion of the current frame 
	 * @param positionFrame of the previous frame 
	 * @return boolean true (if they equal), false (if they differ)
	 */
	public boolean emotionsEquals(int indexEmotion, int positionFrame)
	{
		Classification currentEmotion = currentFrame.getEmotions().get(indexEmotion).getName();
		Classification previousEmotion = allFrames.get(positionFrame).getEmotionClassification();
		return currentEmotion.equals(previousEmotion);
	}
	
	/**
	 * checks if the plausibilites of two emotions equals
	 * @param positionFirst of the first emotion
	 * @param positionSecond of the second emotion
	 * @return boolean true (if the plausibilies equals), false (if they differ)
	 */
	public boolean plausibilityEquals(int positionFirst, int positionSecond)
	{
		return currentFrame.getEmotions().get(positionFirst).getPlausibility() == currentFrame.getEmotions().get(positionSecond).getPlausibility();
	}
	
	/**
	 * adust plausibilites of the emotions in the array list with the plausibilities of the emotion attributes
	 * @param currentFrame
	 */
	public static void adjustPlausibility(Frame currentFrame)
	{
		currentFrame.getEmotions().get(0).setPlausibility(currentFrame.getAnger().getPlausibility());
		currentFrame.getEmotions().get(1).setPlausibility(currentFrame.getFear().getPlausibility());
		currentFrame.getEmotions().get(2).setPlausibility(currentFrame.getJoy().getPlausibility());
		currentFrame.getEmotions().get(3).setPlausibility(currentFrame.getSurprise().getPlausibility());
	}
	

}
