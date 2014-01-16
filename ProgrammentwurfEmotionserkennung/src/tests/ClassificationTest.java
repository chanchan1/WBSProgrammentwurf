package tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dataPreparation.CalculateBinarValues;
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
			System.out.println(currentFrame.getEmotionClassification());
		}
	}
	
	@Test
	public final void positiveTest() throws IOException{
		int i = 0;
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
