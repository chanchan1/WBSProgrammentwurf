package tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import dataPreparation.ReadCSV;
import dataTypes.Frame;

public class ClassificationTest {
	
	ArrayList<Frame> allFrames = new ArrayList<Frame>();
	@Test
	public final void positiveTest() throws IOException{
		ReadCSV reader = new ReadCSV("testData/E_002_a.csv");
		ArrayList<Frame> frames = reader.readCSVData();
		assertTrue(frames!=null);
	}
	
	
	public void generatTestFrames()
	{
		allFrames = new ArrayList<Frame>();
		Frame frame1 = new Frame(1068, 87, true);
		allFrames.add(frame1);

		Frame frame2 = new Frame(1029, 43, true);
		allFrames.add(frame2);
		
		Frame frame3 = new Frame(912, 39, true);
		allFrames.add(frame3);
		
		Frame frame4 = new Frame(1031,52,true);
		allFrames.add(frame4);
		
		Frame frame5 = new Frame(855,47,true);
		allFrames.add(frame5);
	}

}
