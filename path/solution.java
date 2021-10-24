import java.util.Arrays;
import java.util.PriorityQueue;
import CITS2200.Graph;
import CITS2200.Path;

public class PathImp implements Path{
	
	public int getMinSpanningTree(Graph g) {
		int numVertices = g.getNumberOfVertices();
		int[] seen = new int[numVertices];
		int[] distance = new int[numVertices];
 		int[][] weights = g.getEdgeMatrix();
		Arrays.fill(distance, -1);
		Arrays.fill(seen, 0);

 		PriorityQueue<Edge> queue = new PriorityQueue<Edge>();
 		queue.add(new Edge(0, 0));
		distance[0] = 0;
		
 		while (!queue.isEmpty()) {		
			int vertex = queue.remove().vertex;
			
			if (seen[vertex] != 0) {
				continue;
			}
			seen[vertex] = 1;
 					
			for (int i = 0; i < numVertices; ++i) { //check all possible paths from vertex
				int weight = weights[vertex][i];
				if (weight != 0 && seen[i] != 1) { //if there is a path and haven't already added i
					
					if (distance[i] == -1 || distance[i] > weight) { 
						distance[i] = weight;
						queue.add(new Edge(i, distance[i]));
					}
				}
			}
		}
 		int totalWeight = 0;
 		for (int i = 0; i < numVertices; i++){
 			if (distance[i] == -1){
 				return -1;
 			}
 			totalWeight += distance[i];
 		}
		return totalWeight;
	}
	
	public int[] getShortestPaths(Graph g, int startVertex) {
		int numVertices = g.getNumberOfVertices();
		int[] seen = new int[numVertices];
		int[] distance = new int[numVertices];
 		int[][] weights = g.getEdgeMatrix();
 		
		Arrays.fill(distance, -1);
		Arrays.fill(seen, 0);
 		PriorityQueue<Edge> queue = new PriorityQueue<Edge>();
 		queue.add(new Edge(startVertex, 0));
		distance[startVertex] = 0;
		
 		while (!queue.isEmpty()) {			
			int vertex = queue.remove().vertex;
			if (seen[vertex] != 0) {
				continue;
			}
			seen[vertex] = 1;

			for (int i = 0; i < numVertices; i++) {
				int weight = weights[vertex][i];
				if (weight != 0 && seen[i] != 1) {
					if (distance[i] == -1 || distance[i] > distance[vertex] + weight) {
						distance[i] = distance[vertex] + weight;
						queue.add(new Edge(i,distance[i]));
					}
				}
			}
		}
 		return distance;
	}
	
	private class Edge implements Comparable<Edge>{
		public int vertex;
		public int edgeWeight;
		
		public Edge(int vertex, int weight){
			this.vertex = vertex;
			edgeWeight = weight;
		}
		
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