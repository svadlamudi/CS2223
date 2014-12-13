/**
 * @author Sai Kiran Vadlamudi
 * @author Marilda Bozdo
 * 
 * Partnership:
 * 	Summary: a complementing, well made partnership that resulted in an equal distribution of work.
 * 	Sai V.: I helped implement the algorithms we came up with in Java.
 * 	Marilda B.: I helped come up with mathematically sound algorithms to be implemented in Java.
 * 
 * Path-finding runtime: O(n^2) where n = number of vertices.
 * Reason: Dijkstra's Shortest path visits each vertex relaxing
 * all the vertices adjacent to it so if the graph is in an adjacency
 * matrix representation then we need to iterate through all the
 * vertices and see if the current vertex has an edge to any of
 * the others and get them. Finding all edges in an adjacency
 * matrix requires O(n) time so for all n the overall time would
 * be O(n^2) because for each n time complexity is O(n).
 * 
 */
public class DijkstraAM {

	private Double[] distTo;
	private DirectedEdge[] edgeTo;
	private IndexMinPQ<Double> pq;
	
/* Constructors */
	
	/**
	 * 
	 * Computes a shortest paths tree from s to every other vertex
	 * in the edge weighted digraph G.
	 * 
	 * @param G : the edge weighted digraph
	 * @param s : the source vertex
	 * @throws java.lang.IllegalArgumentException if an edge weight is negative
	 * @throws java.lang.IllegalArgumentException unless 0 <= s <= V-1
	 */
	public DijkstraAM(EWDMatrix G, int s) {
		
		// Check for negative weights.
		for(DirectedEdge e : G.edges()) {
			if(e.weight() < 0.0) {
					throw new IllegalArgumentException("Edge: " + e + ", has negative weight");
			}
		}
		
		// Initialize distTo[] and edgeTo[]
		distTo = new Double[G.V()];
		edgeTo = new DirectedEdge[G.V()];
		for (int v = 0; v < G.V(); v++) {
			distTo[v] = Double.POSITIVE_INFINITY;
		}		
		
		pq = new IndexMinPQ<Double>(G.V());
		
		// Relax s
		distTo[s] = 0.0;
		pq.insert(s, distTo[s]);
		
		// Relax vertices in order from s.
		while(!pq.isEmpty()) {
			int v = pq.delMin();
			for(DirectedEdge e : G.adj(v)) {
				relax(e);
			}
		}
		
		// Check optimality conditions
		assert check(G, s);
	}
	
/* Helper Methods */
	
	/**
	 * 
	 * Relax edge e and update pq if changed.
	 *
	 * @param e
	 */
	private void relax(DirectedEdge e) {
		int v = e.from(), w = e.to();
		if(distTo[w] > distTo[v] + e.weight()) {
			distTo[w] = distTo[v] + e.weight();
			edgeTo[w] = e;
			if(pq.contains(w)) {
				pq.decreaseKey(w, distTo[w]);
			} else {
				pq.insert(w, distTo[w]);
			}
		}
	}
	
	/**
	 * 
	 * Returns the length of a shortest path from the source vertex s to vertex v.
	 *
	 * @param v : the destination vertex
	 * @return the length of shortest path from the source vertex s to vertex v;
	 * 			Double.POSITIVE_INFINITY if no such path
	 */
	public Double distTo(Integer v) {
		return distTo[v];
	}
	
	/**
	 * 
	 * Is there a path from the source vertex s to vertex v?
	 *
	 * @param v : the destination vertex
	 * @return true if there is a path from the source vertex
	 * 			s to vertex v, and false otherwise
	 */
	public Boolean hasPathTo(Integer v) {
		return distTo[v] < Double.POSITIVE_INFINITY;
	}
	
	/**
	 * 
	 * Returns a shortest path from the source vertex s to vertex v.
	 *
	 * @param v : the destination vertex
	 * @return a shortest path from the source vertex s to vertex v
	 * 			as an iterable of edges, and null if no such path
	 */
	public Iterable<DirectedEdge> pathTo(Integer v) {
		if(!hasPathTo(v)) {
			return null;
		}
		Stack<DirectedEdge> path = new Stack<DirectedEdge>();
		for(DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
			path.push(e);
		}
		return path;
	}
	
	/**
	 * 
	 * Check optimality conditions:
	 * (i)  for all edges e:			distTo[e.to()] <= distTo[e.from()] + e.weight()
	 * (ii) for all edges e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
	 *
	 * @param G
	 * @param s
	 * @return
	 */
	private Boolean check(EWDMatrix G, int s) {
		
		// Check that edge weights are nonnegative
		for(DirectedEdge e : G.edges()) {
			if(e.weight() < 0) {
				System.err.println("Negative edge weights detected");
				return false;
			}
		}
		
		// Check that distTo[v] and edgeTo[v] are consistent
		if(distTo[s] != 0.0 || edgeTo[s] != null) {
			System.err.println("distTo[] and edgeTo[] inconsistent");
			return false;
		}
		for(int v = 0; v < G.V(); v++) {
			if(v == s) {
				continue;
			}
			if(edgeTo[v] == null & distTo[v] != Double.POSITIVE_INFINITY) {
				System.err.println("distTo[] and edgeTo[] inconsistent");
				return false;
			}
		}
		
		// Check that all edges e = v -> w satisfy distTo[w] <= distTo[v] + e.weight()
		for(int v = 0; v < G.V(); v++) {
			for(DirectedEdge e : G.adj(v)) {
				int w = e.to();
				if(distTo[v] + e.weight() < distTo[w]) {
					System.err.println("Edge: " + e + ", not relaxed.");
					return false;
				}
			}
		}
		
		// Check that all edges e = v -> w on SPT satisfy distTo[w] == distTo[v] + e.weight()
		for(int w = 0; w < G.V(); w++) {
			if(edgeTo[w] == null) {
				continue;
			}
			DirectedEdge e = edgeTo[w];
			int v = e.from();
			if(w != e.to()) {
				return false;
			}
			if(distTo[v] + e.weight() != distTo[w]) {
				System.err.println("Edge: " + e + ", on shortest path not tight.");
				return false;
			}
		}
		return true;
	}

/* Main run-loop */
	
	/**
	 * 
	 * Read in the graph from a file and find all the paths from the given source
	 * vertex through.
	 * 
	 * Input: args[0] : edge weighted digraph
	 * 		  args[1] : source vertex
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Read the Graph.
		In in = new In(args[0]);
		
		// Make and print the Graph.
		System.out.println();
		EWDMatrix G = new EWDMatrix(in);
		System.out.println(G);
		
		// Read source vertex from argument provided during execution time.
		int s = 0;
		if(args.length >= 2 && args[1] != null) {
			s = Integer.parseInt(args[1]);
		}
		
		// Calculate the shortest paths.
		DijkstraAM sp = new DijkstraAM(G, s);
		
		// Print the shortest paths. 
		for(int t = 0; t < G.V(); t++) {
			if(sp.hasPathTo(t)) {
				System.out.println(String.format("%d to %d (%.2f)", s, t, sp.distTo[t]));
				if(t != s) {
					System.out.print("| ");
					for(DirectedEdge e : sp.pathTo(t)) {
						System.out.print(e + " | ");
					}
					System.out.println();
				}				
			} else {
				System.out.println(String.format("%d to %d    no path\n", s, t));
			}
		}
	}

}
