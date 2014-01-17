import java.io.IOException;
import java.util.ArrayList;

import dataPreparation.CalculateBinaryValues;
import dataPreparation.ReadCSV;
import dataTypes.Frame;
import emotionRecognition.DSRule;
import emotions.Classificator;



public class Main 
{
	
	static ArrayList<Frame> allFrames = new ArrayList<Frame>();
	
	public static void main(String[] args){

		readcsvData();
		if (allFrames.size() > 0) {
			CalculateBinaryValues.calculateBinaryValues(allFrames);
			Classificator classificator = new Classificator(allFrames);
			for (Frame currentFrame : allFrames) {
				DSRule.calculatePlBD(currentFrame);
				classificator.classificate(currentFrame);
			}
			// output of the classification of all Frames
			generateOutput();
		}
	}

	private static void generateOutput(){
		for (Frame currentFrame: allFrames)
		{
			System.out.print(allFrames.indexOf(currentFrame)+1);
			System.out.print(". Frame:  ");
			System.out.println (currentFrame.getEmotionClassification());
		}
	}
	
	public static void readcsvData()
	{
		String csvFile = "testData/E_002_a.csv";
		//read pixel values from .csv file in framelist
		ReadCSV reader = new ReadCSV(csvFile);
		try {
			allFrames = reader.readCSVData();
		} catch (IOException e) {
			System.out.println("There was an error with "+csvFile);
		}
	}
	
	
	
	
	
	
}
