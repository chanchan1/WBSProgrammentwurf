package emotionRecognition;

/* ---------------------------------------------------------------------- */
/* Module  : evidences / dempster shafer                                  */
/* ---------------------------------------------------------------------- */
/* Filename        : dempster.c                                           */
/* Version History : 1.0 - 4.7.2004 uncommented running version           */
/*                   1.1 - 13.11.04 minor refinements                     */
/* Last Changes    : 13.11.04 Comments and refinements                    */
/* Author          : Dr. Dirk Reichardt / Berufsakademie Stuttgart        */
/* Created         : May 2004                                             */
/* ---------------------------------------------------------------------- */
/* Compiler Variables :   -                                               */
/* ---------------------------------------------------------------------- */
/* Description :                                                          */
/* This modules provides functions to handle evidences and accumulate     */
/* measures using the dempster shafer rule.                               */
/* ---------------------------------------------------------------------- */
public class Dempster {
	

/* ---------------------------------------------------------------------- */
/* Function name    : createBasicMeasure                                  */
/* ---------------------------------------------------------------------- */
/* input parameters : BasicMeasure *m  - pointer to an empty basic measure*/
/*                                       which is to be initialized       */
/*                    int size         - number of alternatives           */
/* output parameters: BasicMeasure *m  - pointer to the filled structure  */
/* return value     : -                                                   */
/* used routines    : -                                                   */
/* error handling   : -                                                   */
/* ---------------------------------------------------------------------- */

	public static void createBasicMeasure(BasicMeasure m, int size) {
		int i;

		m.a.size = size;         /* setting the alternatives in the structure */
		m.value = 1.0f;         /* default evidence for the alternative Set  */

  /* all alternatives are marked by 1 - signifying the complete Set of    */
  /* alternatives (A).                                                    */

		for (i = 0; i < m.a.size; i++) {
			m.a.alt[i] = 1;
		}

  /* no further elements belong to the core of the measurement            */

		m.next = null;
	}


/* ---------------------------------------------------------------------- */
/* Function name    : createAlternatives                                  */
/* ---------------------------------------------------------------------- */
/* input parameters : int *a           - array of membership flags        */
/*                    int size         - number of alternatives           */
/* output parameters: -                                                   */
/* return value     : Set *            - a filled (and allocated) Set     */
/*                                       structure                        */
/* used routines    : -                                                   */
/* error handling   : corrects values of a beyond (0,1) and limits size   */
/*                    to the valid borders [0,MAX_ALTERNATIVES]           */
/* ---------------------------------------------------------------------- */
/* Description :                                                          */
/* Transforms an array of membership flags to a Set representation.       */
/* ---------------------------------------------------------------------- */

	public static Set createAlternatives(int a[], int size) {
		Set ret;
		int i;

		ret = new Set(); 

		ret.size = size;

  /* checking for invalid values of size parameter                        */

		if (size > Set.MAX_ALTERNATIVES)
			ret.size = Set.MAX_ALTERNATIVES;
		if (size < 0)
			ret.size = 0;

		for (i = 0; i < ret.size; i++) {
	/* correcting invalid flags: if >= 1 assume 1 else assume 0           */
			if (a[i] >= 1)
				ret.alt[i] = 1;
				else ret.alt[i] = 0;
		}

		return ret;
	}


	/* ---------------------------------------------------------------------- */
	/* Predicate Prototypes                                                   */
	/* ---------------------------------------------------------------------- */

	/* ---------------------------------------------------------------------- */
	/* Predicate name : completeAlternatives                                  */
	/* 	---------------------------------------------------------------------- */
	/* 	Description :                                                          */
	/* 	Returns true if the parameter consists of a complete set of alternatives  */
	/* 	(representing A), else false.                                              */
	/* 	---------------------------------------------------------------------- */
	public static boolean completeAlternatives(Set p) {
		int i;

		for (i = 0; i < p.size; i++) {
			if(p.alt[i]!=1)return false;
		}

		return true;
	}

	/* ---------------------------------------------------------------------- */
	/* Predicate name : emptyBasicMeasure                                     */
	/* ---------------------------------------------------------------------- */
	/* Description :                                                          */
	/* Returns 1 if the measure represents "no information" / "no evidence"   */
	/* represented by a complete set of alternatives (A).                     */
	/* ---------------------------------------------------------------------- */
	public static boolean emptyBasicMeasure(BasicMeasure m) {
		return completeAlternatives(m.a);
	}

	private static boolean equalAlternatives(Set m1, Set m2) {
		int i;
		int ret = 1;
		for (i = 0; i < m1.size; i++) {
			ret = ret * ((m1.alt[i] == m2.alt[i]) ? 1 : 0);
		}

		return (ret != 0);
	}

/* ---------------------------------------------------------------------- */
/* Function name    : getBasicMeasure                                     */
/* ---------------------------------------------------------------------- */
/* input parameters : BasicMeasure *m  - a basic measure in which to look */
/*                    Set *p           - the alternative Set to look up   */
/* output parameters: -                                                   */
/* return value     : float            - the evidence for the Set p       */
/* used routines    : emptyBasicMeasure, completeAlternatives             */
/* error handling   : -                                                   */
/* ---------------------------------------------------------------------- */
/* Description :                                                          */
/* the function looks up the evidence for a given alternative Set p in a  */
/* basic measure.                                                         */
/* ---------------------------------------------------------------------- */

	public static float getBasicMeasure(BasicMeasure m, Set p) {
		float ret;

		if (emptyBasicMeasure(m)) {
			if (completeAlternatives(p)) {
				ret = m.value;
			} else
				ret = 0.0f;
		} else {
			if (equalAlternatives(m.a, p)) {
				ret = m.value;
			} else {
				ret = getBasicMeasure(m.next, p);
			}
		}
		return ret;
	}

/* ---------------------------------------------------------------------- */
/* Function name    : addMeasureEntry                                     */
/* ---------------------------------------------------------------------- */
/* input parameters : BasicMeasure *m  - a basic measure to extend        */
/*                    Set *p           - the alternative Set enter        */
/*                    float v          - the evidence for this Set        */
/* output parameters: BasicMeasure *m  - the extended basic measure       */
/* return value     : -                                                   */
/* used routines    : emptyBasicMeasure, equalAlternatives                */
/* error handling   : -                                                   */
/* ---------------------------------------------------------------------- */
/* Description :                                                          */
/* an evidence value is assigned to a subset of alternatives within a     */
/* given measure.                                                         */
/* ---------------------------------------------------------------------- */

	public static void addMeasureEntry(BasicMeasure m, Set p /* passbyvalue */, float v) {
		BasicMeasure tmp;
		int i;

		tmp = m;

  /* go through until alternative Set is found or it is not included */

		while ((!(emptyBasicMeasure(tmp))) && (!(equalAlternatives(tmp.a, p)))) {
			tmp = tmp.next;
		}

		if (equalAlternatives(tmp.a, p)) {
    /* it was already included */
			tmp.value += v;
		} else if (emptyBasicMeasure(tmp)) {
    /* it is not yet included ...*/
			tmp = m.next;

    /* allocate memory for the new entry */
			m.next = new BasicMeasure(); //malloc

    /* move the first entry to the new element */
			m.next.value = m.value;
			m.next.next = tmp;
			m.next.a = m.a.copy(); //byvalue assignment

    /* move the new entry to the first field */
			m.value = v;
			m.a = p.copy(); //byvalue assignment
		}

  /* update assignment to "A" */

		tmp = m;

		while (!(emptyBasicMeasure(tmp))) {
			tmp = tmp.next;
		}

		tmp.value -= v;
	}


	private static boolean emptySet(Set p) {
		int i;
		int ret = 0;
		for (i = 0; i < p.size; i++) {
			ret += p.alt[i];
		}

		return (ret == 0);
	}

	private static Set getIntersection(Set p1, Set p2) {
		int i;
		Set ret = new Set();

		ret.size = p1.size;

		for (i = 0; i < ret.size; i++) {
			ret.alt[i] = ((p1.alt[i] != 0) && (p2.alt[i] != 0)) ? 1 : 0;
		}

		return ret;
	}

	private static float getConflict(BasicMeasure m1, BasicMeasure m2) {
		float conflict = 0.0f;
		BasicMeasure tmp1, tmp2;

		tmp1 = m1;

		while (!emptyBasicMeasure(tmp1)) {
			tmp2 = m2;
			while (!(emptyBasicMeasure(tmp2))) {
				if (emptySet(getIntersection(tmp1.a, tmp2.a))) {
					conflict += tmp1.value * tmp2.value;
				}
				tmp2 = tmp2.next;
			}
			tmp1 = tmp1.next;
		}

		return conflict;
	}

	/* ---------------------------------------------------------------------- */
	/* Function name    : getAccumulatedMeasure                               */
	/* ---------------------------------------------------------------------- */
	/* input parameters : basicMeasure *m1 - a basic measure to accumulate    */
	/*                    basicMeasure *m2 - a basic measure to accumulate    */
	/* output parameters: -                                                   */
	/* return value     : basicMeasure *   - the accumulated measure          */
	/* used routines    : createBasicMeasure,addMeasureEntry,getIntersection  */
	/* error handling   : -                                                   */
	/* ---------------------------------------------------------------------- */
	/* Description :                                                          */
	/* applies the dempster shafer rule to accumulate two measures.           */
	/* ---------------------------------------------------------------------- */
	public static BasicMeasure getAccumulatedMeasure(BasicMeasure m1, BasicMeasure m2) {
		BasicMeasure ret;
		BasicMeasure tmp1, tmp2;
		Set p = new Set();
		float val;
		float conflict;
		float correction;

		ret = new BasicMeasure(); //malloc

		conflict = getConflict(m1, m2);

		if (conflict <= 0.99f) {
			correction = 1.0f / (1.0f - conflict);

			createBasicMeasure(ret, m1.a.size);

			tmp1 = m1;
			while (!(tmp1 == null)) {
				tmp2 = m2;
				while (!(tmp2 == null)) {
					p = getIntersection(tmp1.a, tmp2.a);

					val = tmp1.value * tmp2.value * correction;

					if ((!emptySet(p)) && (val > 0.0f))
						addMeasureEntry(ret, p.copy(), val);

					tmp2 = tmp2.next;
				}
				tmp1 = tmp1.next;
			}
		}

		return ret;
	}

/* ---------------------------------------------------------------------- */
/* Function name : plausibility                                           */
/* ---------------------------------------------------------------------- */
/* Description :                                                          */
/* Determines the plausibility value for an alternative "index" for a     */
/* given basic measure m.                                                 */
/* ---------------------------------------------------------------------- */

	public static float plausibility(BasicMeasure m, int index) {
		float p = 0.0f;
		BasicMeasure tmp;

		tmp = m;

		if (!((index < 0) || (index > m.a.size - 1))) {
			while (!(tmp == null)) {
				if (tmp.a.alt[index] == 1) {
					p += tmp.value;
				}

				tmp = tmp.next;
			}
		}

		return p;
	}

/* ---------------------------------------------------------------------- */
/* Function name : generateAlternatives                                   */
/* ---------------------------------------------------------------------- */
/* Description :                                                          */
/* generates an alternative Set by a binary coded number. for example:    */
/* index 6 results in an alternative Set 0110.                            */
/* ---------------------------------------------------------------------- */

	public static Set generateAlternatives(int size, int index) {
		Set p = new Set();
		int i, ind;
		int base = 1;

		p.size = size;

		base = (int) Math.pow(2, size - 1);

		ind = index;
		for (i = 0; i < size; i++) {
			p.alt[i] = (ind / base);
			ind = ind % base;
			base = base / 2;
		}
		return p;
	}

	private static boolean isSubset(Set p, Set q) {
		int i;
		int ret = 1;

		for (i = 0; i < p.size; i++) {
			if ((q.alt[i] == 0) &&
					(p.alt[i] == 1))
				ret = 0;
		}
		return (ret != 0);
	}

	private static Set invert(Set p) {
		Set ret = new Set();
		int i;

		ret.size = p.size;
		for (i = 0; i < p.size; i++)
			ret.alt[i] = (p.alt[i] == 0) ? 1 : 0;

		return ret;
	}

	private static float belief(BasicMeasure m, Set p) {
		int i, lim;
		Set pt = new Set();
		float res = 0.0f;

		pt.size = p.size;

		lim = (int) Math.pow(2, p.size);

		for (i = 0; i < lim; i++) {
			pt = generateAlternatives(p.size, i);
			if (isSubset(pt, p)) {
				res += getBasicMeasure(m, pt);
			}
		}

		return res;
	}

	/* ---------------------------------------------------------------------- */
	/* Function name : singleBelief                                           */
	/* ---------------------------------------------------------------------- */
	/* Description :                                                          */
	/* computes the belief in a specific alternative (determined by "index")  */
	/* ---------------------------------------------------------------------- */
	public static float singleBelief(BasicMeasure m, int index) {
		Set p = new Set();
		int i;

		for (i = 0; i < m.a.size; i++) {
			p.alt[i] = 0;
		}
		p.alt[index] = 1;

		p.size = m.a.size;

		return belief(m, p);
	}

	/* ---------------------------------------------------------------------- */
	/* Function name : singleDoubt                                            */
	/* ---------------------------------------------------------------------- */
	/* Description :                                                          */
	/* computes the doubt in a specific alternative (determined by "index")   */
	/* ---------------------------------------------------------------------- */
	public static float singleDoubt(BasicMeasure m, int index) {
		Set p = new Set();
		int i;

		for (i = 0; i < m.a.size; i++) {
			p.alt[i] = 1;
		}
		p.alt[index] = 0;

		p.size = m.a.size;

		return belief(m, p);
	}

/* ---------------------------------------------------------------------- */
/* Function name : deleteBasicMeasure                                     */
/* ---------------------------------------------------------------------- */
/* Description :                                                          */
/* deallocates the dynamically allocated memory for the basic measure m.  */
/* ---------------------------------------------------------------------- */

	public static void deleteBasicMeasure(BasicMeasure m) {
		if (m.next == null) {
			//free(m);
			m.a = null;
		} else {
			deleteBasicMeasure(m.next);
			//free(m);
			m.a = null;
		}
	}

	/* ---------------------------------------------------------------------- */
	/* Function name : printBasicMeasure                                      */
	/* ---------------------------------------------------------------------- */
	/* Description :                                                          */
	/* Output of a printed version of the measurement (screen)                */
	/* ---------------------------------------------------------------------- */
	public static void printBasicMeasure(BasicMeasure m) {
		BasicMeasure tmp;
		int i;

		System.out.println("Basic Measure:");
		tmp = m;
		while (tmp != null) {
			System.out.print("m([");
			for (i = 0; i < tmp.a.size; i++)
				System.out.format("%1d", tmp.a.alt[i]);
			System.out.format("]) = %7.3f\n", tmp.value);
			tmp = tmp.next;
		}
	}


}
