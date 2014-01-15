import java.io.IOException;
import java.util.ArrayList;

import dataPreparation.CalculateBinarValues;
import dataPreparation.ReadCSV;
import dataTypes.Classification;
import dataTypes.Frame;
import emotionRecognition.DSRule;
import emotions.Classificator;



public class Main 
{
	
	static ArrayList<Frame> allFrames;
	
	public static void main(String[] args) 	
	{
		
		//readcsvData();
		CalculateBinarValues binarCalculator = new CalculateBinarValues();
		binarCalculator.calculateBinarValues(allFrames);
		DSRule rules = new DSRule();
		Classificator classicator = new Classificator();
		int position = 0;
		for (Frame currentFrame: allFrames)
		{
			rules.calculatePlBD(currentFrame);
			classicator.classificate ( allFrames, position);
			position ++;
		}
	}
	
	public static void readcsvData()
	{
		String csvFile = "/testData/E_002_a.csv";
		//read pixel values from .csv file in framelist
		ReadCSV reader = new ReadCSV(csvFile);
		try {
			allFrames = reader.readCSVData();
		} catch (IOException e) {
			System.out.println("There was an error with "+csvFile);
		}
	}
	
	
	
	
}
