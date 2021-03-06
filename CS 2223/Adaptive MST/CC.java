// Name: Sai Kiran Vadlamudi  Username: svadlamudi  Section: B01
// Name: Marilda Bozdo        Username: mbozdo      Section: B01

/*************************************************************************
 *  Compilation:  javac CC.java
 *  Execution:    java CC filename.txt
 *  Dependencies: Graph.java StdOut.java Queue.java
 *  Data files:   http://algs4.cs.princeton.edu/41undirected/tinyG.txt
 *
 *  Compute connected components using depth first search.
 *  Runs in O(E + V) time.
 *
 *  % java CC tinyG.txt
 *  3 components
 *  0 1 2 3 4 5 6
 *  7 8 
 *  9 10 11 12
 *
 *  % java CC mediumG.txt 
 *  1 components
 *  0 1 2 3 4 5 6 7 8 9 10 ...
 *
 *  % java -Xss50m CC largeG.txt 
 *  1 components
 *  0 1 2 3 4 5 6 7 8 9 10 ...
 *
 *************************************************************************/

/**
 *  The <tt>CC</tt> class represents a data type for 
 *  determining the connected components in an undirected graph.
 *  The <em>id</em> operation determines in which connected component
 *  a given vertex lies; the <em>connected</em> operation
 *  determines whether two vertices are in the same connected component;
 *  the <em>count</em> operation determines the number of connected
 *  components; and the <em>size</em> operation determines the number
 *  of vertices in the connect component containing a given vertex.

 *  The <em>component identifier</em> of a connected component is one of the
 *  vertices in the connected component: two vertices have the same component
 *  identifier if and only if they are in the same connected component.

 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>
 *  (in the worst case),
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Afterwards, the <em>id</em>, <em>count</em>, <em>connected</em>,
 *  and <em>size</em> operations take constant time.
 *  <p>
 *  For additional documentation, see <a href="/algs4/41graph">Section 4.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class CC {
    private boolean[] marked;   // marked[v] = has vertex v been marked?
    private int[] id;           // id[v] = id of connected component containing v
    private int[] size;         // size[id] = number of vertices in given component
    private int count;          // number of connected components
    private Queue<Integer>[] components;

    /**
     * Computes the connected components of the undirected graph <tt>G</tt>.
     * @param G the graph
     */
    public CC(IGraph G) {
        marked = new boolean[G.numVertices()];
        id = new int[G.numVertices()];
        size = new int[G.numVertices()];
        for (int v = 0; v < G.numVertices(); v++) {
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    }

    // depth-first search
    private void dfs(IGraph G, int v) {
        marked[v] = true;
        id[v] = count;
        size[count]++;
        for (Edge w : G.adjEdges(v)) {
            if (!marked[w.other(v)]) {
                dfs(G, w.other(v));
            }
        }
    }

    /**
     * Returns the component id of the connected component containing vertex <tt>v</tt>.
     * @param v the vertex
     * @return the component id of the connected component containing vertex <tt>v</tt>
     */
    public int id(int v) {
        return id[v];
    }

    /**
     * Returns the number of vertices in the connected component containing vertex <tt>v</tt>.
     * @param v the vertex
     * @return the number of vertices in the connected component containing vertex <tt>v</tt>
     */
    public int size(int v) {
        return size[id[v]];
    }

    /**
     * Returns the number of connected components.
     * @return the number of connected components
     */
    public int count() {
        return count;
    }

    /**
     * Are vertices <tt>v</tt> and <tt>w</tt> in the same connected component?
     * @param v one vertex
     * @param w the other vertex
     * @return <tt>true</tt> if vertices <tt>v</tt> and <tt>w</tt> are in the same
     *     connected component, and <tt>false</tt> otherwise
     */
    public boolean connected(int v, int w) {
        return id(v) == id(w);
    }
    
    /**
     * 
     * Returns the separate spanning trees in the spanning forest when an edge is
     * removed from the original minimum spanning tree.
     *
     * @param graph
     * @return
     */
    public Queue<Integer>[] getConnectedTrees(IGraph graph) {
    	Queue<Integer>[] components = (Queue<Integer>[]) new Queue[this.count];
        
    	for (int i = 0; i < this.count; i++) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < graph.numVertices(); v++) {
            components[this.id(v)].enqueue(v);
        }
        
        this.components = components;
        return components;
    }
    
    /**
     * 
     * Print this connected components of the spanning forest.
     *
     */
    public void printCC() {
    	for (int i = 0; i < this.count; i++) {
            for (int v : components[i]) {
                System.out.println(v + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Are vertices <tt>v</tt> and <tt>w</tt> in the same connected component?
     * @param v one vertex
     * @param w the other vertex
     * @return <tt>true</tt> if vertices <tt>v</tt> and <tt>w</tt> are in the same
     *     connected component, and <tt>false</tt> otherwise
     * @deprecated Use connected(v, w) instead.
     */
    public boolean areConnected(int v, int w) {
        return id(v) == id(w);
    }

    /**
     * 
     * Return true if the given vertex is in tree two.
     *
     * @param v
     * @param T2
     * @return
     */
    public boolean isCrossing(int v, Queue<Integer> T2) {
    	for (Integer w : T2) {
    		if (v == w) {
    			return true;
    		}
    	}    	
    	return false;
    }
}
