package ramsey;

import java.util.ArrayList;

import gnu.trove.list.array.TIntArrayList;

public class BKMaxClique {

	// Use Bron–Kerbosch algorithm to find the maximum clique
	// Maximum clique will be the largest maximal clique
	public static int maxCliqueSize(Graph graph) {

		ArrayList<TIntArrayList> maximalCliques = new ArrayList<TIntArrayList>();
		// Initialize P to be all vertices in graph
		TIntArrayList P = new TIntArrayList();
		for (int idx = 0; idx < graph.getn(); idx++) {
			P.add(idx);
		}

		BK(graph, new TIntArrayList(), P, new TIntArrayList(), maximalCliques);

		int maxSize = -1;
		for (TIntArrayList c : maximalCliques) {
			if (c.size() > maxSize) {
				maxSize = c.size();
			}
		}
		return (maxSize);
	}

	// Bron–Kerbosch algorithm for maximal cliques
	// Returns a list of the maximal cliques in the graph
	public static void BK(Graph graph, TIntArrayList R, TIntArrayList P, TIntArrayList X,
			ArrayList<TIntArrayList> maximalCliques) {

		if (P.size() == 0 && X.size() == 0) {
			// R is a maximal clique; add to list
			maximalCliques.add(R);
		} else {
			// Choose a pivot in P with maximal degree
			int vertex = maxDegreeVertex(graph, P);
			TIntArrayList neighbors = graph.neighbors(vertex);
			// Remove neighbors of pivot from P
			TIntArrayList PMinusNeighbors = removeVertices(P, neighbors);

			// Set up newX, newP for the loop
			TIntArrayList newX = new TIntArrayList(X);
			TIntArrayList newP = new TIntArrayList(P);

			// Try again recursively for each vertex in P\neighbors
			for (int idx = 0; idx < PMinusNeighbors.size(); idx++) {
				int testVertex = PMinusNeighbors.getQuick(idx);

				// Add testVertex to R
				TIntArrayList newR = new TIntArrayList(R);
				newR.add(testVertex);
				// Use P n neighbors, X n neighbors
				newX = intersectVertices(newX, neighbors);
				newP = intersectVertices(newP, neighbors);

				// Recur
				BK(graph, newR, newP, newX, maximalCliques);
				// Move testVertex from P to X
				newP.remove(testVertex);
				newX.add(testVertex);
			}
		}
	}

	// Get highest degree vertex in R subset of graph
	public static int maxDegreeVertex(Graph graph, TIntArrayList R) {
		int max = -1;
		int vertex = -1;
		for (int idx = 0; idx < R.size(); idx++) {
			int i = R.getQuick(idx);
			if (graph.degree(i) > max) {
				max = graph.degree(i);
				vertex = i;
			}
		}
		return (vertex);
	}

	// Return vertices P minus neighbors
	public static TIntArrayList removeVertices(TIntArrayList P, TIntArrayList neighbors) {
		TIntArrayList output = new TIntArrayList(P);
		for (int idx = 0; idx < neighbors.size(); idx++) {
			int v = neighbors.getQuick(idx);
			output.remove(v);
		}
		return (output);
	}

	// Return intersection of vertex sets one and two
	public static TIntArrayList intersectVertices(TIntArrayList one, TIntArrayList two) {
		TIntArrayList output = new TIntArrayList();
		for (int idx = 0; idx < one.size(); idx++) {
			int vertex = one.getQuick(idx);
			if (two.contains(vertex)) {
				output.add(vertex);
			}
		}
		return (output);
	}
}
