package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import dataPreparation.CalculateBinarValues;
import dataPreparation.ReadCSV;
import dataTypes.Frame;

public class BinarValusCalculatorTest
{
	ArrayList<Frame> allFrames = new ArrayList<Frame>();
	
	@Test
	public final void testBinarCalculationFurrowedbrowFalse() throws IOException
	{
		generatTestFrames();
		CalculateBinarValues binarCalculator = new CalculateBinarValues();
		binarCalculator.calculateBinarValues(allFrames);
		assertEquals(allFrames.get(1).isBfurrowedbrow(), false);
	}
	@Test
	public final void testBinarCalculationFurrowedbrowTrue() throws IOException
	{
		generatTestFrames();
		CalculateBinarValues binarCalculator = new CalculateBinarValues();
		binarCalculator.calculateBinarValues(allFrames);
		assertEquals(allFrames.get(4).isBfurrowedbrow(), true);
	}
	
	@Test
	public final void testBinarCalculationMarionettlinesTrue() throws IOException
	{
		generatTestFrames();
		CalculateBinarValues binarCalculator = new CalculateBinarValues();
		binarCalculator.calculateBinarValues(allFrames);
		assertEquals(allFrames.get(1).isBmarionettelines(), true);
	}
	@Test
	public final void testBinarCalculationMarionettlinesFalse() throws IOException
	{
		generatTestFrames();
		CalculateBinarValues binarCalculator = new CalculateBinarValues();
		binarCalculator.calculateBinarValues(allFrames);
		assertEquals(allFrames.get(5).isBmarionettelines(), false);
	}
	
	
	public void generatTestFrames()
	{
		allFrames = new ArrayList<Frame>();
		Frame frame1 = new Frame(241, 137, false);
		allFrames.add(frame1);

		Frame frame2 = new Frame(262, 113, false);
		allFrames.add(frame2);
		
		Frame frame3 = new Frame(310, 120, false);
		allFrames.add(frame3);
		
		Frame frame4 = new Frame(263,101,false);
		allFrames.add(frame4);
		
		Frame frame5 = new Frame(302,97,false);
		allFrames.add(frame5);
		Frame frame6 = new Frame(294,48,false);
		allFrames.add(frame6);
	}

}
