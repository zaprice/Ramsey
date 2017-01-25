package ramsey;

import java.util.ArrayList;

import org.apache.commons.math3.util.CombinatoricsUtils;

public class Ramsey {

	public static void main(String[] args) {

	}

	// Generate all graphs on n vertices
	public static ArrayList<Graph> generateGraphs(int n) {
		int total = (int) Math.pow(2, CombinatoricsUtils.binomialCoefficient(n, 2));
		ArrayList<Graph> output = new ArrayList<Graph>(total);
		for (int s = 0; s < total; s++) {
			output.add(new Graph(s, n));
		}
		return (output);
	}

}
