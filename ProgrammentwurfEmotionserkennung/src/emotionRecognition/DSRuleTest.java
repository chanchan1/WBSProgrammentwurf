package emotionRecognition;

/* ---------------------------------------------------------------------- */
/* Test program for the dempster shafer rule                              */
/* ---------------------------------------------------------------------- */

import static emotionRecognition.Dempster.*;

public class DSRuleTest {

	public static void main(String[] args) {
		int i;
		BasicMeasure m = new BasicMeasure();
		BasicMeasure m2 = new BasicMeasure();
		BasicMeasure m3 = new BasicMeasure();
		BasicMeasure m4 = new BasicMeasure();
		BasicMeasure res, res2;

  /* exampleset of subgroups of alternatives */

		int entry1[] = {1, 0, 1, 1, 0, 0, 0};
		int entry2[] = {1, 0, 1, 1, 0, 0, 0};
		int entry3[] = {1, 0, 0, 1, 1, 1, 1};
		int entry4[] = {1, 0, 1, 0, 0, 0, 0};
		int entry5[] = {0, 1, 0, 0, 1, 0, 0};
		int entry6[] = {0, 0, 0, 0, 0, 0, 1};

		Set p1, p2, p3, p4, p5, p6;

  /* transforming into set structure used for alternatives */

		p1 = createAlternatives(entry1, 7);
		p2 = createAlternatives(entry2, 7);
		p3 = createAlternatives(entry3, 7);
		p4 = createAlternatives(entry4, 7);
		p5 = createAlternatives(entry5, 7);
		p6 = createAlternatives(entry6, 7);

  /* defining measure 1 */
		createBasicMeasure(m, 7);

  /* candidates 0,2,3 build a subgroup which is assigned
     the evidence value 0.7 */

		addMeasureEntry(m, p1.copy(), 0.7f);

  /* since no further entry is given, the evidence for
     omega is 0.3 */

		printBasicMeasure(m);

  /* defining measure 2 */
		createBasicMeasure(m2, 7);
		addMeasureEntry(m2, p3.copy(), 0.85f);
		printBasicMeasure(m2);

		System.out.println("accumulate ...");

  /* create new measure by accumulation */

		res = getAccumulatedMeasure(m, m2);
		printBasicMeasure(res);

  /* define measure 3 */

		createBasicMeasure(m3, 7);
		addMeasureEntry(m3, p4.copy(), 0.4f);
		addMeasureEntry(m3, p5.copy(), 0.23f);
		addMeasureEntry(m3, p6.copy(), 0.12f);

  /* this time 3 values are given. the evidence for
     omega ist 0.25 */

		printBasicMeasure(m3);
		System.out.println("accumulate ...");

  /* create new measure by accumulation */

		res2 = getAccumulatedMeasure(res, m3);
		printBasicMeasure(res2);

  /* plausibilities */

		System.out.println(" Nr : Pl(x)  |  B(x)   |  Z(x) ");

		for (i = 0; i < 7; i++) {
			System.out.format("[%d] : %5.3f  |  %5.3f  | %5.3f \n", i, plausibility(res2, i), singleBelief(res2, i), singleDoubt(res2, i));
		}

	}
}
/* ---------------------------------------------------------------------- */
/* END: Test program for the dempster shafer rule                         */
/* ---------------------------------------------------------------------- */
