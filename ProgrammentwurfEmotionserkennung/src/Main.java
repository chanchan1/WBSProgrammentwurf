import java.io.IOException;
import java.util.ArrayList;

import dataPreparation.CalculateBinarValues;
import dataPreparation.ReadCSV;
import dataTypes.Classification;
import dataTypes.Frame;



public class Main {
	
	static ArrayList<Frame> frames;
	static ArrayList<Classification> emotions;
	
	public static void main(String[] args) 	
	{
		readcsvData();
		calculateBinarValues();
		calculatePBZ();
		
		
		
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
		//calculate the binar values
				CalculateBinarValues calculator = new CalculateBinarValues();
				
				for(Frame frame: frames)
				{
					frame.setBmarionettelines(calculator.calculatebinarMarionettelines(frame));
					frame.setBfurrowedbrow(calculator.CalculateBinarValuesFurrowedbrow(frame));
				}
	}
	
	
	public static void calculatePBZ()
	{
		//use existing code for calculation plausibility, belief, doubt for the emtions anger, fear, joy, surprise
		//save the results in the objects anger.plausibility etc.
	}

}
