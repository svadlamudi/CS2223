// Name: Sai Kiran Vadlamudi  Username: svadlamudi  Section: B01
// Name: Marilda Bozdo        Username: mbozdo      Section: B01

/**
 * Interface to be implemented by any graph type object such as EdgeWeightedGraph
 * and PrimMST.
 */
public interface IGraph {
	
	// Return the number of vertices in this graph.
	public int numVertices();
	
	// Return the number of edges in this graph.
	public int numEdges();
	
	// Return the edges adjacent to given vertex in this graph.
	public Iterable<Edge> adjEdges(int vertex);
	
}
