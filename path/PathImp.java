import java.util.Arrays;
import java.util.PriorityQueue;
import CITS2200.Graph;
import CITS2200.Path;

/**
 * Implement to algorithm for weighted graph to find the minimum spinning tree and the quickest path from a single source.
 * getMinSpanningTree: Apply Prim's algorithm to find a minimum spanning tree of an undirected, weighted graph. 
 * getShortestPaths: Apply Dijkstra's algorithm to find the shortest paths from a source vertex in a directed, weighted graph.  
 * @author Wei Yang 21220208
 *
 */
public class PathImp implements Path{

	/**
	 *  Prim's algorithm to find the total weight of the minimum spanning tree, or -1 if no minimum spanning tree can be found
	 *  @param  g  the graph given to detect the minimum spanning tree.
	 *  @return the total weight of the minimum spanning tree or -1 if there is no minimum spanning tree
	 */
	public int getMinSpanningTree(Graph g) {
		int numOfVertices = g.getNumberOfVertices();
		Boolean[] detected = new Boolean [numOfVertices];
		int[] distance = new int[numOfVertices];
 		int[][] weights = g.getEdgeMatrix();
		Arrays.fill(distance, -1);
		Arrays.fill(detected, false);

 		PriorityQueue<Edge> queue = new PriorityQueue<Edge>();
 		queue.add(new Edge(0, 0));
		distance[0] = 0;
		
 		while (!queue.isEmpty()) {		
			int vertex = queue.remove().vertex;
			
			if (detected[vertex]) {
				continue;
			}
			detected[vertex] = true;
 					
			for (int i = 0; i < numOfVertices; i++) { 
				int weight = weights[vertex][i];
				if (weight != 0 && !detected[i]) { 
					
					if (distance[i] == -1 || distance[i] > weight) { 
						distance[i] = weight;
						queue.add(new Edge(i, distance[i]));
					}
				}
			}
		}
 		int totalWeight = 0;
 		for (int i = 0; i < numOfVertices; i++){
 			if (distance[i] == -1){
 				return -1;
 			}
 			totalWeight += distance[i];
 		}
		return totalWeight;
	}
	
	
	/**
	 * 
	 *  Use Djikstra's algorithm to find  the shortest distances from the specified start vertex to each of the vertices in the graph
	 *  @param  g  the graph given to detect the shortest path.
	 *  @param startVertex the index of vertex used as the starting point to search other vertex as destination.
	 *  @return  an array of the distances from the specified start vertex to each of the vertices in the graph
	 *  
	 */
	
	public int[] getShortestPaths(Graph g, int startVertex) {
		int numOfVertices = g.getNumberOfVertices();
		Boolean[] detected = new Boolean[numOfVertices];
		int[] distance = new int[numOfVertices];
 		int[][] weights = g.getEdgeMatrix();
 		
		Arrays.fill(distance, -1);
		Arrays.fill(detected, false);
 		PriorityQueue<Edge> queue = new PriorityQueue<Edge>();
 		queue.add(new Edge(startVertex, 0));
		distance[startVertex] = 0;
		
 		while (!queue.isEmpty()) {			
			int vertex = queue.remove().vertex;
			if (detected[vertex]) {
				continue;
			}
			detected[vertex] = true;

			for (int i = 0; i <numOfVertices; i++) {
				int weight = weights[vertex][i];
				if (weight != 0 && !detected[i]) {
					if (distance[i] == -1 || distance[i] > distance[vertex] + weight) {
						distance[i] = distance[vertex] + weight;
						queue.add(new Edge(i,distance[i]));
					}
				}
			}
		}
 		return distance;
	}
	
	/**
	 * Implement  Weighter edge and  a method to compare the current and 
	 * @author WEI
	 *
	 */
	private class Edge implements Comparable<Edge>{
		public int vertex;
		public int edgeWeight;
		
		/**
		 * Constructor of new Edge
		 * @param vertex  the index of a given vertex.
		 * @param weight  the Weight of a given edge
		 */
		
		public Edge(int vertex, int weight){
			this.vertex = vertex;
			edgeWeight = weight;
		}
		
		/**
		 * Compare the new and current edge 
		 * @param  current edge from the previous vertex
		 */
		
		public int compareTo(Edge current) {
			int currentWeight = current.edgeWeight;
			
			if(edgeWeight < currentWeight){
				return -1;
			}
			else if(edgeWeight > currentWeight){
				return 1;
			}
			else return 0;
		}
	}
}