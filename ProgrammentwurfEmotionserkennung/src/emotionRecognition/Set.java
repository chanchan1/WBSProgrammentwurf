package emotionRecognition;

import java.util.Arrays;

/* A simple set representation by marking an alternative as present (1)   */
public class Set {
	/* The maximum number of alternatives is set by a system constant     */
	public static int MAX_ALTERNATIVES = 20;

	public int alt[] = new int[MAX_ALTERNATIVES];
	public int size;

	public Set copy() {
		Set out = new Set();
		out.alt = Arrays.copyOf(alt,alt.length);
		out.size = size;
		return out;
	}
}
