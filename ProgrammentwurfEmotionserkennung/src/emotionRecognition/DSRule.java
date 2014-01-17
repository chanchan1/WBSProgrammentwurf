package emotionRecognition;

import static emotionRecognition.Dempster.*;

import java.util.ArrayList;
import java.util.Arrays;

import dataTypes.Frame;
import emotions.*;

public class DSRule {

	private final static int				NUMBER_OF_ALTERNATIVES	= 4;
	private final static int[]				fear					= {1, 0, 1};
	private final static int[]				surprise				= {1, 0, 0};
	private final static int[]				anger					= {0, 2, 1};
	private final static int[]				joy						= {2, 1, 0};
	private final static ArrayList<int[]>	emotions				= new ArrayList<>(Arrays.asList(fear, surprise, anger, joy));

	/**
	 * calculates and sets the plausibility, belief and doubt of the given frame
	 * for the emotions fear, surprise, anger and joy.
	 * 
	 * @param frame
	 *            for which plausibility, belief and doubt are calculated and
	 *            set
	 */
	public static void calculatePlBD(Frame frame){

		BasicMeasure mLFB = new BasicMeasure();
		BasicMeasure mLML = new BasicMeasure();
		BasicMeasure mEL = new BasicMeasure();
		BasicMeasure res, res2;

		/* emotions and their alternatives */
		int furrowedBrow[] = generateSubsetArray(frame, 0);
		int marionetteLines[] = generateSubsetArray(frame, 1);
		int eyelid[] = generateSubsetArray(frame, 2);

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

		/* set plausibilities, belief and doubt */
		setPlBD(frame, res2);
		generateOutput(res2);

	}

	/**
	 * sets the the plausibility, belief and doubt of each emotion stated by the
	 * basic measure m for the given frame
	 * 
	 * @param frame
	 * @param m
	 */
	private static void setPlBD(Frame frame, BasicMeasure m){
		for (int i = 0; i < NUMBER_OF_ALTERNATIVES; i++) {
			switch (i) {
				case 0:
					System.out.print("-- fear \n");
					frame.setFear(new Fear(plausibility(m, i), singleBelief(m,
							i), singleDoubt(m, i)));
					break;
				case 1:
					System.out.print("-- surprise \n");
					frame.setSurprise(new Surprise(plausibility(m, i),
							singleBelief(m, i), singleDoubt(m, i)));
					break;
				case 2:
					System.out.print("-- anger \n");
					frame.setAnger(new Anger(plausibility(m, i), singleBelief(
							m, i), singleDoubt(m, i)));
					break;
				case 3:
					System.out.print("-- joy  \n");
					frame.setJoy(new Joy(plausibility(m, i),
							singleBelief(m, i), singleDoubt(m, i)));
					break;

			}
		}
	}

	/**
	 * generates an array that represents a subset of emotions which correspond
	 * to the frame's property. Is the frame's property set, are all emotions
	 * select which have the property set, too. If not, all emotions are
	 * selected that do not have that property. Unknown properties (indicated by
	 * 2) are selected in both cases.
	 * 
	 * @param frame
	 * @param property
	 * @return the generated array
	 */
	private static int[] generateSubsetArray(Frame frame, int property){
		int[] ret = new int[NUMBER_OF_ALTERNATIVES];
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

		for (int[] emotion : emotions) {
			int value = 0;
			// select the emotion, if the property is 2 or the same as the
			// frame's
			if (emotion[property] == 2 || emotion[property] == frameProperty) {
				value = 1;
			}
			ret[i] = value;
			i++;
		}

		return ret;
	}

	/**
	 * prints plausibility, belief and doubt of the measure to the console
	 * 
	 * @param m
	 */
	private static void generateOutput(BasicMeasure m){
		int i;
		System.out.println(" Nr : Pl(x)  |  B(x)   |  Z(x) ");

		for (i = 0; i < NUMBER_OF_ALTERNATIVES; i++) {

			System.out.format("[%d] : %5.3f  |  %5.3f  | %5.3f ", i,
					plausibility(m, i), singleBelief(m, i), singleDoubt(m, i));
		}
	}

}
