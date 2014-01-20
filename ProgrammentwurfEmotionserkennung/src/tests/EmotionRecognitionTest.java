package tests;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import dataTypes.Frame;

import emotionRecognition.DSRule;

public class EmotionRecognitionTest {


	@Test
	public final void testCalculateRunningThrough() {
		
		Frame frame = new Frame();
		frame.setBfurrowedbrow(true);
		frame.setBmarionettelines(true);
		frame.setBeyelid(true);
		DSRule.calculatePlBD(frame);
		Assert.assertTrue(true);
		
	}
	
	@Test
	public final void testCalculateSurprise() {
		
		Frame frame = new Frame();
		frame.setBfurrowedbrow(true);
		frame.setBmarionettelines(false);
		frame.setBeyelid(false);
		DSRule.calculatePlBD(frame);
		Assert.assertEquals(frame.getSurprise().getPlausibility(),1.0d,0.000001d);
		
	}

}
