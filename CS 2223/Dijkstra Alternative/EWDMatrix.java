
public class EWDMatrix {

	private final Integer V;
	private Integer E;
	private Double[][] adjMatrix; 
	
/* Constructors */
	
	/**
	 * 
	 * Initializes an empty edge-weighted digraph with V vertices and 0 edges.
	 * 
	 * @param V : number of vertices
	 * @throws java.lang.IllegalArgumentException if V < 0
	 */
	public EWDMatrix(Integer V) {
		if (V < 0) {
			throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
		} else {
			this.V = V;
			this.E = 0;
			adjMatrix = new Double[V][V];
		}
	}
	
	/**
	 * 
	 * Initializes an edge-weighted digraph from an input stream.
	 * The format is the number of vertices V,
	 * followed by the number of edges E,
	 * followed by E pairs of vertices and edge weights,
	 * with each entry separated by whitespace.
	 * 
	 * @param in : input stream
	 * @throws java.lang.IndexOutofBoundsException if the endpoints of any edge are not in prescribed range
	 * @throws java.lang.IllegalArgumentException if the number og vertices or edges is negative
	 */
	public EWDMatrix(In in) {
		this(in.readInt());
		int E = in.readInt();
		if (E < 0) {
			throw new IllegalArgumentException("Number of edges in a digraph must be nonnegative");
		}
		for (int i = 0; i < E; i++) {
			int v = in.readInt();
			int w = in.readInt();
			if (v < 0 || v >= V) throw new IndexOutOfBoundsException("Vertex: " + v + "is not between 0 and " + (V-1));
			if (w < 0 || w >= V) throw new IndexOutOfBoundsException("Vertex: " + w + "is not between 0 and " + (V-1));
			double weight = in.readDouble();
			addEdge(v, w, weight);
		}
	}
	
/* Accessors and Mutators */
	
	/**
	 * 
	 * Return the number of vertices in this edge weighted digraph.
	 *
	 * @return
	 */
	public Integer V() {
		return this.V;
	}
	
	/**
	 * 
	 * Return the number of edges in this edge weighted digraph.
	 *
	 * @return
	 */
	public Integer E() {
		return this.E;
	}
	
	/**
	 * 
	 * Return the adjacency matrix of this edge weighted digraph.
	 *
	 * @return
	 */
	public Double[][] adjMatrix() {
		return this.adjMatrix;
	}
	
/* Helper Methods */
	
	/**
	 * 
	 * Check if given vertex is valid. 
	 *
	 * @param v
	 * @throws java.lang.IndexOutofBoundsException if given vertex is not in prescribed range
	 * @return
	 */
	private Boolean validateVertex(Integer v) {
		if(v < 0 || v >= V) {
			throw new IndexOutOfBoundsException("Vertex: " + v + ", is not between 0 and " + (V-1));
		}
		return true;
	}
	
	/**
	 * 
	 * Add an edge to this edge weighted graph given that the endpoints are valid.
	 *
	 * @param head
	 * @param tail
	 * @param weight
	 */
	public void addEdge(Integer head, Integer tail, double weight) {
		validateVertex(head);
		validateVertex(tail);
		this.adjMatrix[head][tail] = weight;
		this.E++;
	}
	
	/**
	 * 
	 * Return all the edges in this edge weighted digraph.
	 *
	 * @return
	 */
	public Iterable<DirectedEdge> edges() {
		int v = 0, w = 0;
		Stack<DirectedEdge> edges = new Stack<DirectedEdge>();
		for(Double[] weights : adjMatrix) {
			for(Double weight : weights) {
				if(weight != null) {
					edges.push(new DirectedEdge(v, w, weight));
				}
				w++;
			}
			v++;
			w = 0;
		}
		return edges;
	}
	
	/**
	 * 
	 * Return all out edges from the given vertex iff it is valid.
	 *
	 * @param v
	 * @return
	 */
	public Iterable<DirectedEdge> adj(Integer v) {
		int w = 0;
		validateVertex(v);
		Stack<DirectedEdge> edges = new Stack<DirectedEdge>();
		for(Double weight : adjMatrix[v]) {
			if(weight != null) {
				edges.push(new DirectedEdge(v, w, weight));
			}
			w++;
		}
		return edges;
	}
	
	/**
	 * Return a string representation of this edge weighted digraph.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(Double[] edges : adjMatrix) {
			sb.append("| ");
			for (Double weight : edges) {
				if (weight != null) {
					sb.append(String.format("%.2f ", weight));
				} else {
					sb.append("0.00 ");
				}
			}
			sb.append("|\n");
		}
		
		return sb.toString();
	}
	
}
