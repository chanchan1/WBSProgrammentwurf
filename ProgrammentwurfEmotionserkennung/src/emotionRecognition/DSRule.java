package emotionRecognition;

/* ---------------------------------------------------------------------- */
/* Test program for the dempster shafer rule                              */
/* ---------------------------------------------------------------------- */

import static emotionRecognition.Dempster.*;

import java.util.ArrayList;
import java.util.Arrays;

import dataTypes.Frame;
import emotions.*;
//TODO: exceptionhandling mit einbauen
//TODO: clean up code
public class DSRule {
	private final static int [] fear = {1,0,1};
	private final static int [] surprise = {1,0,0};
	private final static int [] anger = {0,2,1};
	private final static int [] joy = {2,1,0};
	private final static ArrayList<int[]> emotions = new ArrayList<>(Arrays.asList(fear,surprise,anger,joy));
	
	public static void calculatePlBD(Frame	frame){

		final int NUMBER_OF_ALTERNATIVES = 4;
		BasicMeasure mLFB = new BasicMeasure();
		BasicMeasure mLML = new BasicMeasure();
		BasicMeasure mEL = new BasicMeasure();
		BasicMeasure res, res2;
		
		/* emotions and their alternatives */
		//TODO: possibility: take 2-alternatives into lesser consideration
		int furrowedBrow[] = generateArray(frame, 0,NUMBER_OF_ALTERNATIVES);
		int marionetteLines[] = generateArray(frame, 1,NUMBER_OF_ALTERNATIVES);
		int eyelid[] = generateArray(frame, 2,NUMBER_OF_ALTERNATIVES);

		Set furrowedBrowSet, marionetteLinesSet, eyelidSet;

		/* transforming into set structure used for alternatives */

		furrowedBrowSet = createAlternatives(furrowedBrow,
				NUMBER_OF_ALTERNATIVES);
		marionetteLinesSet = createAlternatives(marionetteLines,
				NUMBER_OF_ALTERNATIVES);
		eyelidSet = createAlternatives(eyelid, NUMBER_OF_ALTERNATIVES);

		/* defining measure large furrowed brow */
		createBasicMeasure(mLFB, NUMBER_OF_ALTERNATIVES);
		addMeasureEntry(mLFB, furrowedBrowSet.copy(), 0.8f);
		printBasicMeasure(mLFB);

		/* defining measure large marionette lines */
		createBasicMeasure(mLML, NUMBER_OF_ALTERNATIVES);
		addMeasureEntry(mLML, marionetteLinesSet.copy(), 0.8f);
		printBasicMeasure(mLML);

		System.out.println("accumulate ...");
		/* create new measure by accumulation */
		res = getAccumulatedMeasure(mLFB, mLML);
		printBasicMeasure(res);

		/* define measure eye lid */
		createBasicMeasure(mEL, NUMBER_OF_ALTERNATIVES);
		addMeasureEntry(mEL, eyelidSet.copy(), 0.8f);
		printBasicMeasure(mEL);

		System.out.println("accumulate ...");
		/* create new measure by accumulation */
		res2 = getAccumulatedMeasure(res, mEL);
		printBasicMeasure(res2);

		/* plausibilities */
		generateOutputAndSetPlausibilities(frame, NUMBER_OF_ALTERNATIVES, res2);
		

	}

	private static int[] generateArray(Frame	frame, int property, int NUMBER_OF_ALTERNATIVES){
		int[] ret = new int [NUMBER_OF_ALTERNATIVES];
		int i = 0;
		int frameProperty = 0;
		
		switch (property) {
			case 0:
				frameProperty = frame.isBfurrowedbrow() ? 1 : 0;
				break;
			case 1:
				frameProperty = frame.isBmarionettelines() ? 1 : 0;
				break;
			case 2:
				frameProperty = frame.isBeyelid() ? 1 : 0;
				break;
		}
		
		for(int[] emotion : emotions){
			int value = 0;
			if(emotion[property]==2||emotion[property]==frameProperty ){
				value=1;
			}
			ret[i]=value;
			i++;
		}
		
		return ret;
	}

	private static void generateOutputAndSetPlausibilities(Frame frame, final int NUMBER_OF_ALTERNATIVES,
			BasicMeasure res2){
		int i;
		System.out.println(" Nr : Pl(x)  |  B(x)   |  Z(x) ");

		for (i = 0; i < NUMBER_OF_ALTERNATIVES; i++) {

			System.out.format("[%d] : %5.3f  |  %5.3f  | %5.3f ", i,
					plausibility(res2, i), singleBelief(res2, i),
					singleDoubt(res2, i));
			switch (i) {
				case 0:
					System.out.print("-- fear \n");
					frame.setFear(new Fear(plausibility(res2, i), singleBelief(res2, i),
							singleDoubt(res2, i)));
					break;
				case 1:
					System.out.print("-- surprise \n");
					frame.setSurprise(new Surprise(plausibility(res2, i), singleBelief(res2, i),
							singleDoubt(res2, i)));
					break;
				case 2:
					System.out.print("-- anger \n");
					frame.setAnger(new Anger(plausibility(res2, i), singleBelief(res2, i),
							singleDoubt(res2, i)));
					break;
				case 3:
					System.out.print("-- joy  \n");
					frame.setJoy(new Joy(plausibility(res2, i), singleBelief(res2, i),
							singleDoubt(res2, i)));
					break;
			}
		}
	}

}
