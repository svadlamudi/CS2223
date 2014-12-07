// Name: Sai Kiran Vadlamudi  Username: svadlamudi  Section: B01
// Name: Marilda Bozdo        Username: mbozdo      Section: B01

/*************************************************************************
 *  Compilation:  javac AdaptiveMST.java 
 *           or   javac-algs4  AdaptiveMST.java
 *  Execution:    java AdaptiveMST <filename>
 *          or    java-algs4  AdaptiveMST <filename>
 *  Dependencies: StdIn from the Sedgewick and Wayne stdlib library
 *                EdgeWeightedGraph 
 *
 *  uses code by  Robert Sedgewick and Kevin Wayne
 *
 * @author:  Dan Dougherty, for CS 2223 WPI,  November 2014 
 *
 * For project 3 your job is to write the method treeChanges, along with whatever 
 * auxilliary methods you'd like to write.
 *
 * The input/output in the the main procedure should be ready to use,
 * you needn't worry about changing that code.
 *
 *************************************************************************/

import java.io.*;
import java.util.*;

public class AdaptiveMST {
  
	public static void main(String[] args) throws IOException, IllegalArgumentException {
		
		// Read in and construct the original graph from a file
		In graph_in = new In(args[0]);
		EdgeWeightedGraph origGraph = new EdgeWeightedGraph(graph_in);
		
		// Compute an MST for the original graph
		PrimMST origMST = new PrimMST(origGraph);
		if (origMST.numEdges() <= 50) {
			origMST.printTree();
		}
		
		// Read the user input, line by line and decide appropriate modification to the MST
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String prompt = "Please enter a weighted edge, or a blank line to exit";
		String input_line;
		
		System.out.println(prompt);
		while ( (input_line = in.readLine()) != null && input_line.length() != 0 ) {
			
			try{
				Edge changingEdge = makeEdge(input_line);
				treeChanges(new EdgeWeightedGraph(origGraph), new PrimMST(origMST), changingEdge);
			}
			catch (IllegalArgumentException e) {
				System.out.println(e);
				System.out.println("Try again...");
			}
			System.out.println(prompt);
		}
		System.out.println("Bye");
	}

    /*
      REQUIRES: origMST is a minimum spanning tree for origGraph, changingEdge is an edge over the vertices of origGraph,
         interpreted as a modification of origGraph.
         other assumption are in force (see program spec)
      EFFECTS: writes to standard output the changes in origMST due to the modification changingEdge
    */
	public static void treeChanges(EdgeWeightedGraph origGraph, PrimMST origMST, Edge changingEdge) {
    	
    	boolean notInTree = true;
    	boolean notInGraph = true;
    	
    	// Check if given changing edge is in the graph.
    	for (Edge graphEdge : origGraph.adj(changingEdge.either())) {
    		if (changingEdge.equals(graphEdge)) {
    			// If edge is in the graph then check if the changing edge is in the tree.
    			for (Edge treeEdge : origMST.edges()) {
    				if (changingEdge.equals(treeEdge)) {
    					// Run if changing edge is in the tree and the change is decreasing the weight of 
    					// tree edge which would still make the edge the minimum crossing edge of its cut.
    					if (changingEdge.weight() <= treeEdge.weight()) {
    						// Edge is in the tree.
    						notInTree = false;
    						
    						// Print the output.
    						System.out.println("Decrease the weight of tree edge {" + changingEdge.either() + ", " + changingEdge.other(changingEdge.either()) + "} to be " + changingEdge.weight() + ": no change in the tree");
    						
    						// Found edge no need to keep checking.
    						break;
    					} 
    					// Run if the changing edge is in the tree and the change is increasing the weight of
    					// tree edge.
    					else if (changingEdge.weight() > treeEdge.weight()) {
    						// Edge is in the tree
    						notInTree = false;
    						
    						// Find and output the minimum crossing edge after increasing the weight of 
    						// the minimum crossing edge in the tree.
    						Edge minimumEdge = increaseTreeEdgeWeight(changingEdge, origGraph, origMST);
    						if (!minimumEdge.equals(changingEdge)) {
    							System.out.println("Increase the weight of tree edge {" + changingEdge.either() + ", " + changingEdge.other(changingEdge.either()) + "} to be " + changingEdge.weight() + ": the result is to remove tree edge {" + treeEdge.either() + ", " + treeEdge.other(treeEdge.either()) + "} and replace it by edge {" + minimumEdge.either() + ", " + minimumEdge.other(minimumEdge.either()) + "}");
    						} else {
    							System.out.println("Increase the weight of tree edge {" + changingEdge.either() + ", " + changingEdge.other(changingEdge.either()) + "} to be " + changingEdge.weight() + ": no change in the tree");
    						}
    						
    						// Found edge no need to keep checking
    						break;
    					}
    				}
    			}
    			// Changing edge is not in the tree but is in the graph.
    			if (notInTree) {
    				// Run if the change is increasing the weight of non-tree edge which would 
    				// still not make it the minimum crossing edge of its cut.
    				if (changingEdge.weight() >= graphEdge.weight()) {
    					// Edge is in the graph.
    					notInGraph = false;
    					
    					// Output the result.
    					System.out.println("Increase the weight of non-tree edge {" + changingEdge.either() + ", " + changingEdge.other(changingEdge.either()) + "} to be " + changingEdge.weight() + ": no change in the tree");
    					
    					// Found edge no need to keep checking.
    					break;
    				} 
    				// Run if the change is decreasing the edge weight of non-tree edge which
    				// could make it the minimum of the crossing edge of its cut.
    				else if (changingEdge.weight() < graphEdge.weight()) {
    					// Edge is in the graph
    					notInGraph = false;
    					
    					// Find the larger of the edge making a cycle.
    					Edge maxWeightEdge = decreaseNonTreeEdgeWeight(changingEdge, origMST);
    					if (!maxWeightEdge.equals(changingEdge)) {
    						System.out.println("Decrease the weight of non-tree edge {" + changingEdge.either() + ", " + changingEdge.other(changingEdge.either()) + "} to be " + changingEdge.weight() + ": the result is to remove tree edge {" + maxWeightEdge.either() + ", " + maxWeightEdge.other(maxWeightEdge.either()) + "} and replace it by edge {" + changingEdge.either() + ", " + changingEdge.other(changingEdge.either()) + "}");   						
    					} else {
    						System.out.println("Decrease the weight of non-tree edge {" + changingEdge.either() + ", " + changingEdge.other(changingEdge.either()) + "} to be " + changingEdge.weight() + ": no change in the tree");	
    					}
    					
    					// Found edge no need to keep checking.
    					break;
    				} 
				} 
    		}
    	}
    	// Edge not in graph or tree therefore new edge
    	if (notInGraph && notInTree) {
    		// Find and output the maximum edge in the cycle in case the new edge is 
    		// smaller than other crossing edges of its cut.
    		Edge maxWeightEdge = decreaseNonTreeEdgeWeight(changingEdge, origMST);
			if (!maxWeightEdge.equals(changingEdge)) {
				System.out.println("Add new edge {" + changingEdge.either() + ", " + changingEdge.other(changingEdge.either()) + "} to be " + changingEdge.weight() + ": the result is to remove tree edge {" + maxWeightEdge.either() + ", " + maxWeightEdge.other(maxWeightEdge.either()) + "} and replace it by edge {" + changingEdge.either() + ", " + changingEdge.other(changingEdge.either()) + "}");   						
			} else {
				System.out.println("Add new edge {" + changingEdge.either() + ", " + changingEdge.other(changingEdge.either()) + "} to be " + changingEdge.weight() + ": no change in the tree");	
			}
    	}
    }

    /*
      REQUIRES: a string consisting of 3 tokens representing two ints and a double.
      RETURNS:  a weighted edge built from this data.
      EFFECTS: in addition to number format exceptions, can throw an
          IllegalArgumentException if input is not 3 tokens,
    */
    public static Edge makeEdge(String inline) 
        throws IllegalArgumentException, NumberFormatException
    {
        // whitespace will separate strings; have to trim leading/trailing whitespace first
        String delims = "[ ]+";
        String[] input_strings = inline.trim().split(delims);

        if (input_strings.length != 3) 
            throw new IllegalArgumentException("Expect 3 numbers per input line");
        int x = Integer.parseInt(input_strings[0]);
        int y = Integer.parseInt(input_strings[1]);
        double weight = Double.parseDouble(input_strings[2]);
        return new Edge(x,y, weight);
    }
    
    /**
     * 
     * Given the new changed edge and the original minimum spanning tree calculate
     * and return the maximum weighted edge that is causing the cycle.
     *
     * @param changingEdge
     * @param origMST
     * @return
     */
    public static Edge decreaseNonTreeEdgeWeight(Edge changingEdge, PrimMST origMST) {
    	
    	// Add the changing edge to the tree to create a cycle with the minimum edge present in its place.
    	origMST.addEdge(changingEdge);
    	// Locate the edge(s) creating the cycle.
		Cycle treeCycle = new Cycle(origMST);
		HashMap<Integer, Edge> edgesVisited = new HashMap<Integer, Edge>();
		
		if (treeCycle != null) {
			Edge maxWeightEdge = null;
			if (treeCycle.cycle() != null) {
				// Check through all vertices in the cycle.
				for (Integer v : treeCycle.cycle()) {
					// Get edges from cycle vertices and find the largest edge in cycle.
					for (Edge e : origMST.adjEdges(v)) {
						// Check if edge is the largest so far and mark it as visited.
						if (maxWeightEdge != null && treeCycle.hasVertex(e.other(v)) && !edgesVisited.containsKey(e.hashCode())) {
							if (maxWeightEdge.weight() < e.weight()) {
								maxWeightEdge = e;
							}
							edgesVisited.put(e.hashCode(), e);
						} else if (maxWeightEdge == null) {
							maxWeightEdge = e;
						} else if (!edgesVisited.containsKey(e.hashCode())) {
							edgesVisited.put(e.hashCode(), e);
						}
					}
				}
				// Return the largest weight edge in the cycle.
				return maxWeightEdge;
			}
		}
		
		// Return given changing edge if adding edge to tree doesnt create a cycle which shouldn't happen.
		return changingEdge;
    }
    
    /**
     * 
     * Given the new changed edge as well as the original graph and minimum spanning tree
     * calculate and return the minimum crossing edge.
     *
     * @param changingEdge
     * @param origGraph
     * @param origMST
     * @return
     */
    public static Edge increaseTreeEdgeWeight(Edge changingEdge, EdgeWeightedGraph origGraph, PrimMST origMST) {
    	
    	// Remove increased weight edge from the tree.
    	origMST.deleteEdge(changingEdge);
    	// Retrieve the spanning connected forest.
    	CC minimumSpanningForest = new CC(origMST);
    	// Create a minimum priority queue of crossing edge to find the minimum crossing edge.
    	MinPQ<Edge> crossingEdges = new MinPQ<Edge>(new MinComparator());
		Queue<Integer>[] components = minimumSpanningForest.getConnectedTrees(origMST);
		
		// Find the crossing edges from tree one to tree two.
		for (Integer v : components[0]) {
			// Get edges from vertex to find crossing edge if there is one from v.
			for (Edge e : origGraph.adj(v)) {
				// Edge weights are final so if the edge is equal to changing then
				// create new edge with modified weight.
				if (e.equals(changingEdge)) {
					Edge f = new Edge(e.either(), e.other(e.either()), changingEdge.weight());
					crossingEdges.insert(f);
				} 
				// If edge is crossing to second tree then add to crossing edges.
				else if (minimumSpanningForest.isCrossing(e.other(v), components[1])) {
					crossingEdges.insert(e);
				}
			}
		}
		// Return the minimum crossing edge.
		return crossingEdges.min();
    }
    
}

