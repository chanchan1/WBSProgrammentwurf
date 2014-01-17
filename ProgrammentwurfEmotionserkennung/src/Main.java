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
	
	static ArrayList<Frame> allFrames = new ArrayList<Frame>();
	
	public static void main(String[] args) 	
	{
		
		readcsvData();
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
		
		position = 1;
		for (Frame currentFrame: allFrames)
		{
			System.out.print(position);
			System.out.print("  ");
			System.out.println (currentFrame.getEmotionClassification());
			position ++;
		}
		classicator.checkClassicication(allFrames);
	}
	
	public static void readcsvData()
	{
		String csvFile = "testData/E_002.csv";
		//read pixel values from .csv file in framelist
		ReadCSV reader = new ReadCSV(csvFile);
		try {
			allFrames = reader.readCSVData();
		} catch (IOException e) {
			System.out.println("There was an error with "+csvFile);
		}
	}
	
	
	
	
	
	
}
