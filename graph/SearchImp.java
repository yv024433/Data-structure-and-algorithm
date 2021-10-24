
import CITS2200.Graph;
import CITS2200.Link;
import CITS2200.Search;
import CITS2200.Underflow;
import CITS2200.Queue;
/**
 * Use the Graph, class provided on the unit web-page, to write a breadth-first search algorithm 
 * and depth first search algorithm for directed graphs.
 * Write a class SearchImp implements CITS2200.Search which contains 3 methods: getDistances,getConnetedTree and getTimes.
 * @author WEI 21220208
 *
 */
public class SearchImp implements Search {
	  
	 /**
     * An unvisited node in a search
     */
	private static final int WHITE = 0;
	/**
     * A visited, but unprocessed node in a search
     */
	    private static final int GREY = 1;
	    /**
	     * A processed node in a search
	     */
	    private static final int BLACK = 2;
	    /**
	     * The colours of vertexes used in getTimes
	     */
	    private int[] colours;
	    /**
	     * A 2-dimensional array storing the start and end times of each vertex, used in getTimes
	     */
	    private int[][] times;
	    /**
	     * The current time during the process of calling getTimes
	     */
	    private int time;

	/**
	 * specifying the parent vertex for each vertex 
	 * @param G the graph provided. 
	 * @param S the source vertex or the root of breadth-first tree.
	 * @return parent vertex for each vertex (or -1 if there is no parent)
	 */
	public int[] getConnectedTree(Graph G, int S) {
		 int[][] edgeMatrix = G.getEdgeMatrix();

	        int[] colours = new int[G.getNumberOfVertices()];
	        int[] parents = new int[G.getNumberOfVertices()];

	        for(int num = 0; num < G.getNumberOfVertices(); num++) {
	            colours[num] = WHITE;
	            parents[num] = -1;
	        }

	        QueueLinked queue = new QueueLinked();

	        colours[S] = GREY;
	        queue.enqueue(S);

	        while(!queue.isEmpty()) {
	            int vertex = (Integer) queue.dequeue();

	            int[] edges = edgeMatrix[vertex];
	            for(int toVertex = 0; toVertex < edges.length; toVertex++) {
	                if(edges[toVertex] == 0)
	                    continue;

	                if(colours[toVertex] == WHITE) {
	                	parents[toVertex] = vertex;
	                	colours[toVertex] = GREY;
	                    queue.enqueue(toVertex);
	                }
	            }

	            colours[vertex] = BLACK;
	        }

	        return parents;
	    }
	

	/**
	 * specifying the distance each vertex is from the starting vertex
	 * @param G the graph provided. 
	 * @param S the source vertex or the root of breadth-first tree.
	 * @return distance each vertex is from the starting vertex, or -1 if it is not reachable
	 */
	public int[] getDistances(Graph G, int S) {
		 int[][] edgeMatrix = G.getEdgeMatrix();

	        int[] colours = new int[G.getNumberOfVertices()];
	        int[] distances = new int[G.getNumberOfVertices()];

	        for(int index = 0; index < G.getNumberOfVertices(); index++) {
	            colours[index] = WHITE;
	            distances[index] = -1;
	        }

	        QueueLinked queue = new QueueLinked();

	        colours[S] = GREY;
	        distances[S] = 0;
	        queue.enqueue(S);

	        while(!queue.isEmpty()) {
	            int vertex = (Integer) queue.dequeue();

	            int[] edges = edgeMatrix[vertex];
	            for(int toVertex = 0; toVertex < edges.length; toVertex++) {
	                if(edges[toVertex] == 0)
	                    continue;

	                if(colours[toVertex] == WHITE) {
	                	distances[toVertex] = distances[vertex] + 1;
	                	colours[toVertex] = GREY;	            
	                    queue.enqueue(toVertex);
	                }
	            }

	            colours[vertex] = BLACK;
	        }

	        return distances;
	    }

	

	/**
	 * A  A recursive depth first search, output the start and finish times for each vertex.  
	 * @param G the graph provided. 
	 * @param S the next vertex to be processed in the depth-first search
	 * @return a 2-dimensional array, where each sub-array has two elements:
     *         the first is the start time and the second is the finish time
	 */
	public int[][] getTimes(Graph G, int S) {
	    this.colours = new int[G.getNumberOfVertices()];
	    this.times = new int[G.getNumberOfVertices()][2];
	    this.time = 0;

	    colours[S] = GREY;
	    times[S][0] = time;
	    time = time+1;
	    int[][] edgeMatrix = G.getEdgeMatrix();
	    int[] edges = edgeMatrix[S];
	    for(int toVertex = 0; toVertex < edges.length; toVertex++) {
	        if(edges[toVertex] == 0)
	            continue;

	        if(colours[toVertex] == WHITE) {
	        	getTimes(G, toVertex);
	        }
	    }

	    colours[S] = BLACK;
	    times[S][1] = time;
	    time = time+1;

	    return times;
	}


	
    /**
     * First in First out queue implement
     * @author WEI
     *
     */
	public class QueueLinked implements Queue{

		private Link first;
		private Link last;
		public QueueLinked() {
		 first=null;
		 last=null;
		}
	/**
	 * delete an object in the front of the queue
	 */
		public Object dequeue() throws Underflow {
			if(!isEmpty()) {
				Object o =first.item;
				first=first.successor;
				if(isEmpty()) last=null;
				return o;
			}
			else throw new Underflow("dequeuing from empty queue");
		}
		
		
		/**
		 * Add an object at the end of a queue
		 * @param a object a added to the queue at the end
		 */

		public void enqueue(Object a) {
			if(isEmpty()) {
				first=new Link(a,null);
				last=first;
			}
			else {
				last.successor= new Link(a,null);
				last=last.successor;
			}
			
		}
		
		/**
		 * Examine the first item in the queue
		 */

		public Object examine() throws Underflow {
			if(!isEmpty()) return first.item;
			else throw new Underflow("examining empty queue");
		}
		
		/**
		 * Check if the queue is empty 
		 */

		public boolean isEmpty() {
			return first==null;
		}
		
		
	}

}
