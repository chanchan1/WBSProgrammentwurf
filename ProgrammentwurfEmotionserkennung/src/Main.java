import java.io.IOException;
import java.util.ArrayList;

import dataPreparation.CalculateBinarValues;
import dataPreparation.ReadCSV;
import dataTypes.Classification;
import dataTypes.Frame;



public class Main 
{
	
	static ArrayList<Frame> frames;
	
	public static void main(String[] args) 	
	{
		generateTestFrames();
		/*readcsvData();
		*/
		calculateBinarValues();
		int position = 0;
		for (Frame currentFrame: frames)
		{
			calculatePlBD(currentFrame)
			classificate ( allFrames, position);
			position ++;
		}
	}
	
	public static void readcsvData()
	{
		String csvFile = "/testData/E_002_a.csv";
		//read pixel values from .csv file in framelist
		ReadCSV reader = new ReadCSV(csvFile);
		try {
			frames = reader.readCSVData();
		} catch (IOException e) {
			System.out.println("There was an error with "+csvFile);
		}
	}
	
	public static void calculateBinarValues()
	{
		//calculate the binar values of the absolute ones from the .csv file
				CalculateBinarValues calculator = new CalculateBinarValues();
				
				for(Frame frame: frames)
				{
					frame.setBmarionettelines(calculator.calculatebinarMarionettelines(frame));
					frame.setBfurrowedbrow(calculator.CalculateBinarValuesFurrowedbrow(frame));
				}
	}
	
	public void generateTestFrames()
	{
		frames = new ArrayList<Frame>();
		Frame frame1 = new Frame();
		frame1.setAbs_furrowedbrow = 265;
		frame1.setAbs_marionettelines = 54;
		frame1.setbeyelid = true;
		frames.add(frame1);

		Frame frame2 = new Frame();
		frame2.setAbs_furrowedbrow = 478;
		frame2.setAbs_marionettelines = 34;
		frame2.setbeyelid = true;
		frames.add(frame2);
		
		Frame frame3 = new Frame();
		frame3.setAbs_furrowedbrow = 265;
		frame3.setAbs_marionettelines = 123;
		frame3.setbeyelid = true;
		frames.add(frame3);
		
		Frame frame4 = new Frame();
		frame4.setAbs_furrowedbrow = 567;
		frame4.setAbs_marionettelines = 125;
		frame4.setbeyelid = true;
		frames.add(frame4);
		
		Frame frame5 = new Frame();
		frame5.setAbs_furrowedbrow = 345;
		frame5.setAbs_marionettelines = 54;
		frame5.setbeyelid = true;
		frames.add(frame5);
	}
}
