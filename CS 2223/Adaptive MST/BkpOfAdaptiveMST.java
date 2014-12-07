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
//import java.util.*;

public class BkpOfAdaptiveMST {
  
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
				treeChanges(origGraph, origMST, changingEdge);
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
      REQUIRES: T is a minimum spaning tree for G, e is an edge over the vertices of G,
         interpreted as a modification of G.
         other assumption are in force (see program spec)
      EFFECTS: writes to standard output the changes in T due to the modifiation e
    */

    // STUB VERSION:  right now this doesn't do anything but echo the input.
    //  Get rid of these printlns !
    // ALSO note that the type of parameter T will probably change in your code, 
    // depending on what you use to compute your original MST (eg T  might be a
    // PrimMST object, or whatever.
    public static void treeChanges(EdgeWeightedGraph origGraph, PrimMST origMST, Edge changingEdge) {
    	
    	boolean notInTree = true;
    	EdgeWeightedGraph origGraphBkp = origGraph;
    	PrimMST origMSTBkp = origMST;
    	
    	for (Edge graphEdge : origGraph.adj(changingEdge.either())) {
    		if (changingEdge.equals(graphEdge)) {
    			for (Edge treeEdge : origMST.edges()) {
    				if (changingEdge.equals(treeEdge)) {
    					if (changingEdge.weight() <= treeEdge.weight()) {
    						notInTree = false;
    						System.out.println("Decrease the weight of tree edge {" + changingEdge.either() + ", " + changingEdge.other(changingEdge.either()) + "} to be " + changingEdge.weight() + ": no change in the tree");
    					} else if (changingEdge.weight() > treeEdge.weight()) {
    						notInTree = false;
    						origMST.deleteEdge(treeEdge);
    						CC forest = new CC(origMST);
    						/*MinPQ<Edge> crossingEdges = new MinPQ<Edge>(new MinComparator());
    						Queue<Integer>[] components = forest.getConnectedTrees(origMST);
    						int treeOneSize = components[0].size();
    						int treeTwoSize = components[1].size(); 
    						for (int i = 0; i < treeOneSize; i++) {
    							int v = components[0].dequeue();
    							for (Edge e : origGraph.adj(v)) {
    								for (int k = 0; k < treeTwoSize; k++) {
    									int w = components[1].dequeue();
    									if (e.equals(changingEdge)) {
    										e.setWeight(changingEdge.weight());
    									}
    									if (e.other(v) == w) {
    										crossingEdges.insert(e);
    									}
    									components[1].enqueue(w);
    								}
    							}
    						}
    						Edge minimumEdge = crossingEdges.min();*/
    						Edge minimumEdge = increaseTreeEdgeWeight(changingEdge, origGraph, origMST, forest);
    						System.out.println("Increase the weight of tree edge {" + changingEdge.either() + ", " + changingEdge.other(changingEdge.either()) + "} to be " + changingEdge.weight() + ": the result is to remove tree edge {" + treeEdge.either() + ", " + treeEdge.other(treeEdge.either()) + "} and replace it by edge {" + minimumEdge.either() + ", " + minimumEdge.other(minimumEdge.either()) + "}");
    					}
    				}
    			}
    			if (notInTree) {
    				if (changingEdge.weight() >= graphEdge.weight()) {
    					System.out.println("Increase the weight of non-tree edge {" + changingEdge.either() + ", " + changingEdge.other(changingEdge.either()) + "} to be " + changingEdge.weight() + ": no change in the tree");
    				} else if (changingEdge.weight() < graphEdge.weight()) {
    					origMST.addEdge(changingEdge);
    					Cycle treeCycle = new Cycle(origMST);
    					if (treeCycle != null) {
    						Edge maxWeightEdge = null;
    						if (treeCycle.cycle() != null) {
		    					for (Integer v : treeCycle.cycle()) {
		    						for (Edge e : origMST.adjEdges(v)) {
		    							if (maxWeightEdge != null) {
		    								if (maxWeightEdge.weight() < e.weight()) {
		    									maxWeightEdge = e;
		    								}
		    							} else {
		    								maxWeightEdge = e;
		    							}
		    						}
		    					}
		    					System.out.println("Decrease the weight of non-tree edge {" + changingEdge.either() + ", " + changingEdge.other(changingEdge.either()) + "} to be " + changingEdge.weight() + ": the result is to remove tree edge {" + maxWeightEdge.either() + ", " + maxWeightEdge.other(maxWeightEdge.either()) + "} and replace it by edge {" + changingEdge.either() + ", " + changingEdge.other(changingEdge.either()) + "}");
    						}
    					} 
    				}
    			}
    		}
    	}
    	
    	origGraph = origGraphBkp;
    	origMST = origMSTBkp;
    	
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
    
    public static void decreaseNonTreeEdgeWeight(Edge changingEdge) {
    	
    }
    
    public static Edge increaseTreeEdgeWeight(Edge changingEdge, EdgeWeightedGraph origGraph, PrimMST origMST, CC MSF) {
    	MinPQ<Edge> crossingEdges = new MinPQ<Edge>(new MinComparator());
		Queue<Integer>[] components = MSF.getConnectedTrees(origMST);
		for (int i = 0; i < components[0].size(); i++) {
			int v = components[0].dequeue();
			for (Edge e : origGraph.adj(v)) {
				for (int j = 0; j < components[1].size(); j++) {
					int w = components[1].dequeue();
					
					if (e.equals(changingEdge)) {
						//e.setWeight(changingEdge.weight());
						crossingEdges.insert(e);
					} else if (e.other(v) == w) {
						crossingEdges.insert(e);
					}
					components[1].enqueue(w);
				}
			}
			components[0].enqueue(v);
		}
		return crossingEdges.min();
    }
    
}
