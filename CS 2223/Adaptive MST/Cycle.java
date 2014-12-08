// Name: Sai Kiran Vadlamudi  Username: svadlamudi  Section: B01
// Name: Marilda Bozdo        Username: mbozdo      Section: B01

/*************************************************************************
 *  Compilation:  javac Cycle.java
 *  Dependencies: Graph.java Stack.java
 *
 *  Identifies a cycle.
 *  Runs in O(E + V) time.
 *
 *  % java Cycle tinyG.txt
 *  3 4 5 3 
 * 
 *  % java Cycle mediumG.txt 
 *  15 0 225 15 
 * 
 *  % java Cycle largeG.txt 
 *  996673 762 840164 4619 785187 194717 996673 
 *
 *************************************************************************/

/**
 *  The <tt>Cycle</tt> class represents a data type for 
 *  determining whether an undirected graph has a cycle.
 *  The <em>hasCycle</em> operation determines whether the graph has
 *  a cycle and, if so, the <em>cycle</em> operation returns one.
 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>
 *  (in the worst case),
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Afterwards, the <em>hasCycle</em> operation takes constant time;
 *  the <em>cycle</em> operation takes time proportional
 *  to the length of the cycle.
 *  <p>
 *  For additional documentation, see <a href="/algs4/41graph">Section 4.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Cycle {
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;

    /**
     * Determines whether the undirected graph <tt>G</tt> has a cycle and, if so,
     * finds such a cycle.
     * @param G the graph
     */
    public Cycle(IGraph G) {
        if (hasSelfLoop(G)) return;
        if (hasParallelEdges(G)) return;
        marked = new boolean[G.numVertices()];
        edgeTo = new int[G.numVertices()];
        for (int v = 0; v < G.numVertices(); v++)
            if (!marked[v])
                dfs(G, -1, v);
    }


    // does this graph have a self loop?
    // side effect: initialize cycle to be self loop
    private boolean hasSelfLoop(IGraph g) {
        for (int v = 0; v < g.numVertices(); v++) {
        	for (Edge w : g.adjEdges(v)) {
                if (v == w.other(v)) {
                	cycle = new Stack<Integer>();
                    cycle.push(v);
                    cycle.push(v);
                    return true;
                }
            }
        }
        return false;
    }

    // does this graph have two parallel edges?
    // side effect: initialize cycle to be two parallel edges
    private boolean hasParallelEdges(IGraph g) {
        marked = new boolean[g.numVertices()];

        for (int v = 0; v < g.numVertices(); v++) {

            // check for parallel edges incident to v
            for (Edge w : g.adjEdges(v)) {
                if (marked[w.other(v)]) {
                    cycle = new Stack<Integer>();
                    cycle.push(v);
                    cycle.push(w.other(v));
                    cycle.push(v);
                    return true;
                }
                marked[w.other(v)] = true;
            }

            // reset so marked[v] = false for all v
            for (Edge w : g.adjEdges(v)) {
                marked[w.other(v)] = false;
            }
        }
        return false;
    }

    /**
     * Does the graph have a cycle?
     * @return <tt>true</tt> if the graph has a cycle, <tt>false</tt> otherwise
     */
    public boolean hasCycle() {
        return cycle != null;
    }

     /**
     * Returns a cycle if the graph has a cycle, and <tt>null</tt> otherwise.
     * @return a cycle (as an iterable) if the graph has a cycle,
     *    and <tt>null</tt> otherwise
     */
   public Iterable<Integer> cycle() {
        return cycle;
    }
   
   /**
    * 
    * Use dfs to find the path of the cycle if one is present.
    *
    * @param G
    * @param u
    * @param v
    */
    private void dfs(IGraph G, int u, int v) {
        marked[v] = true;
        for (Edge w : G.adjEdges(v)) {

            // short circuit if cycle already found
            if (cycle != null) return;

            if (!marked[w.other(v)]) {
                edgeTo[w.other(v)] = v;
                dfs(G, v, w.other(v));
            }

            // check for cycle (but disregard reverse of edge leading to v)
            else if (w.other(v) != u) {
                cycle = new Stack<Integer>();
                for (int x = v; x != w.other(v); x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w.other(v));
                cycle.push(v);
            }
        }
    }
    
    /**
     * 
     * Return true if vertex from is in the second MST.
     *
     * @param vertex
     * @return
     */
    public boolean hasVertex(int vertex) {
    	
    	if (cycle != null) {
	    	for (Integer v : cycle) {
	    		if (v.equals(vertex)) {
	    			return true;
	    		}
	    	}
    	}
    	
    	return false;
    }
    
}

