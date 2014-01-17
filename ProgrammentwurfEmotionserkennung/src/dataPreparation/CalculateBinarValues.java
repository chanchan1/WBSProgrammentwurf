package dataPreparation;

import java.util.ArrayList;

import dataTypes.Frame;

public class CalculateBinarValues {
	
	//average value for all test data for all emotions of the furrowedbrow and marionettelines
	double averagefurrowedbrow = 433.35;
	double averagemarionettelines = 88.41;
	
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
	
	
	public boolean calculateBinarValuesFurrowedbrow(Frame frame)
	{
		//procent of the deviation to the average
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
	
	public boolean calculatebinarMarionettelines(Frame frame)
	{
		//procent of the deviation to the average
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
