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

		/* defining measure large marionette lines */
		createBasicMeasure(mLML, NUMBER_OF_ALTERNATIVES);
		addMeasureEntry(mLML, marionetteLinesSet.copy(), 0.8f);

		/* create new measure by accumulation */
		res = getAccumulatedMeasure(mLFB, mLML);

		/* define measure eye lid */
		createBasicMeasure(mEL, NUMBER_OF_ALTERNATIVES);
		addMeasureEntry(mEL, eyelidSet.copy(), 0.8f);

		/* create new measure by accumulation */
		res2 = getAccumulatedMeasure(res, mEL);

		/* set plausibilities, belief and doubt */
		setPlBD(frame, res2);

	}

	/**
	 * sets the the plausibility, belief and doubt of each emotion stated by the
	 * basic measure m for the given frame
	 * 
	 * @param frame
	 * @param m
	 */
	private static void setPlBD(Frame frame, BasicMeasure m){
		Emotion emotion = null;
		for (int i = 0; i < NUMBER_OF_ALTERNATIVES; i++) {
			switch (i) {
				case 0:
					emotion = frame.getFear();
					break;
				case 1:
					emotion = frame.getSurprise();
					break;
				case 2:
					emotion = frame.getAnger();
					break;
				case 3:
					emotion = frame.getJoy();
					break;

			}
			emotion.setPlausibility((double)plausibility(m, i));
			emotion.setBelief((double)singleBelief(m, i));
			emotion.setDoubt((double)singleDoubt(m, i));
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

	

}
