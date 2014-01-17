package tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dataPreparation.CalculateBinaryValues;
import dataPreparation.ReadCSV;
import dataTypes.Frame;
import emotionRecognition.DSRule;
import emotions.Classificator;

public class ClassificationTest {
	
	static ArrayList<Frame> allFrames = new ArrayList<Frame>();
	
	@Before
	public void prepare()
	{
		readcsvData();
		CalculateBinaryValues.calculateBinaryValues(allFrames);
		Classificator classicator = new Classificator(allFrames);
		for (Frame currentFrame: allFrames)
		{
			DSRule.calculatePlBD(currentFrame);
			classicator.classificate ( currentFrame);
			System.out.println(currentFrame.getEmotionClassification());
		}
	}
	
	
	

	
	public static void readcsvData()
	{
		String csvFile = "testData/E_002_a.csv";
		//read pixel values from .csv file in framelist
		ReadCSV reader = new ReadCSV(csvFile);
		try {
			allFrames= reader.readCSVData();
		} catch (IOException e) {
			System.out.println("There was an error with "+csvFile);
		}
	}

}
