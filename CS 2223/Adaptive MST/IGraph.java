// Name: Sai Kiran Vadlamudi  Username: svadlamudi  Section: B01
// Name: Marilda Bozdo        Username: mbozdo      Section: B01

public interface IGraph {

	public int numVertices();
	
	public int numEdges();
	
	public Iterable<Edge> adjEdges(int vertex);
	
}
