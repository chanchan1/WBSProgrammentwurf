package dataPreparation;

import java.util.ArrayList;

import dataTypes.Frame;

public class CalculateBinarValues {
	
	//average value for all test data for all emotions of the furrowedbrow and marionettelines
	double averagefurrowedbrow = 433.35;
	double averagemarionettelines = 88.41;
	
	/**
	 * Calculate from the absolute Value of the furrowed brow and marionette lines the binary values (big = 1, little = 0)
	 * concidering the relation between the value of the marionette lines and the furrowed brow
	 * Calculation for each Frame and is setting the binary attributes
	 * @param Array allFrames, Calculation for each Frame in this array 
	 */
	public void calculateBinarValues(ArrayList<Frame> allFrames)
	{
		//calculate the binar values of the absolute ones from the .csv file
				
				for(Frame frame: allFrames)
				{
					boolean bmarionettelines = calculatebinarMarionettelines(frame);
					boolean bfurrowedbrow = calculateBinarValuesFurrowedbrow(frame);
					frame.setBmarionettelines(bmarionettelines);
					frame.setBfurrowedbrow(bfurrowedbrow);
				}
	}
	
	/**
	 * Change the absolute Value of the furrowed brow of one frame into a binary value (big, little)
	 * Scales the marionette lines to the average value of the marionette lines and scales furrowed brow also
	 * if the scaled furrowed brow are smaller the average of the furrowed brow, binary value is false (little) else, true(big)
	 * @param frame from which the binary value of the furrowed brow is calculated
	 * @return the boolean value of the furrowed brow (true = big, false = little)
	 */
	public boolean calculateBinarValuesFurrowedbrow(Frame frame)
	{
		//percentage of the deviation to the average
		double procent = averagemarionettelines / frame.getAbs_marionettelines();
		double scaledfurrowedbrow = frame.getAbs_furrowedbrow()*procent;
		boolean binarFurrowedbrow;
		
		if(scaledfurrowedbrow< averagefurrowedbrow)
		{
			binarFurrowedbrow = false;
		}
		else
		{
			binarFurrowedbrow = true;
		}
		return binarFurrowedbrow;
	}
	
	
	/**
	 * Change the absolute Value of the marionette lines  of one frame into a binary value (big, little)
	 * Scales thefurrowed brow to the average value of the furrowed brow and scales furrowed brow also
	 * if the scaled marionette lines are smaller the average of the marionette lines, binary value is false (little) else, true(big)
	 * @param frame from which the binary value of the marionette lines is calculated
	 * @return the boolean value of the marionette lines (true = big, false = little)
	 */
	public boolean calculatebinarMarionettelines(Frame frame)
	{
		//percentage of the deviation to the average
				double procent = averagefurrowedbrow / frame.getAbs_furrowedbrow();
				double scaledMarionettelines = frame.getAbs_marionettelines()*procent;
				boolean binarMarionettelines;
				
				if(scaledMarionettelines< averagemarionettelines)
				{
					binarMarionettelines = false;
				}
				else
				{
					binarMarionettelines = true;
				}
				return binarMarionettelines;
	}

}
