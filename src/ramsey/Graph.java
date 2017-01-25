package ramsey;

import java.util.BitSet;

import gnu.trove.list.array.TIntArrayList;

public class Graph {

	private BitSet graph;
	// Number of vertices
	private int n;
	// Maximum size of BitSet, because of how byte[] transform works
	private final int maxLen = 32;

	/*
	 * Constructor
	 */

	public Graph(int s, int n) {
		graph = toBits(s);
		this.n = n;
	}

	/*
	 * Public methods
	 */

	// Get degree of vertex i
	public int degree(int i) {
		int degree = 0;
		for (int j = 0; j < n; j++) {
			if (j == i) {
				continue;
			} else if (isConnected(i, j)) {
				degree++;
			}
		}
		return (degree);
	}

	// Get list of neighbors of vertex i
	public TIntArrayList neighbors(int i) {
		TIntArrayList neighbors = new TIntArrayList();
		for (int j = 0; j < n; j++) {
			if (j == i) {
				continue;
			} else if (isConnected(i, j)) {
				neighbors.add(j);
			}
		}
		return (neighbors);
	}

	public int getn() {
		return (n);
	}

	public String toString() {
		return (graph.toString() + ", n=" + n);
	}

	/*
	 * Private methods
	 */

	// Returns T if i,j have an edge
	private boolean isConnected(int i, int j) {
		// Swap if ordering is incorrect
		if (j < i) {
			int linearIndex = (n * (n - 1) / 2) - (n - j) * (n - j - 1) / 2 + i - j - 1;
			// Vertices are listed backwards, from 0 to maxLen-1
			return (graph.get(maxLen - 1 - linearIndex));
		} else {
			int linearIndex = (n * (n - 1) / 2) - (n - i) * (n - i - 1) / 2 + j - i - 1;
			// Vertices are listed backwards, from 0 to maxLen-1
			return (graph.get(maxLen - 1 - linearIndex));
		}
	}

	/*
	 * Static Helpers
	 */

	// Convert int value s into byte[], then load bits as BitSet
	private static BitSet toBits(int s) {
		return (BitSet.valueOf(toBytes(s)));
	}

	private static byte[] toBytes(int i) {
		byte[] result = new byte[4];

		// Java is big endian and I'd rather use with little endian
		i = Integer.reverseBytes(Integer.reverse(i));

		result[0] = (byte) (i >> 24);
		result[1] = (byte) (i >> 16);
		result[2] = (byte) (i >> 8);
		result[3] = (byte) (i);

		return result;
	}
}
