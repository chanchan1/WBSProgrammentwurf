package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import dataPreparation.ReadCSV;
import dataTypes.Frame;

public class ReadCSVTest {

	@Test
	public final void positiveTest() throws IOException{
		ReadCSV reader = new ReadCSV("testData/E_002_a.csv");
		ArrayList<Frame> frames = reader.readCSVData();
		assertTrue(frames!=null);
	}

}
