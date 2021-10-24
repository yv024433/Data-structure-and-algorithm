import java.util.LinkedList;
import java.util.Queue;

import CITS2200.Graph;
import CITS2200.Search;

public class SearchImp implements Search
{
	int time;
	int times[][];
	Queue<Integer> timeQueue;
	boolean checked[];
	
	public int[] getConnectedTree(Graph arg0, int arg1) 
	{
		int vNum = arg0.getNumberOfVertices();
		
	
		int adjacent[] = new int[vNum];
		for (int i=0;i<vNum;i++)
		{
			adjacent[i]=-2;
		}
		
		int[][] matrix = arg0.getEdgeMatrix();
		

		checked = new boolean[vNum];
		

		Queue<Integer> queue = new LinkedList<Integer>();
		

		queue.add(arg1);
		checked[arg1] = true;
		
		while (!queue.isEmpty())
		{

			int currentVertex = queue.poll();
			

			for(int i=0; i<vNum;i++)
			{
				if (matrix[currentVertex][i] == 1 && checked[i]==false && !queue.contains(i) && !(currentVertex==i))
				{
					adjacent[i] = currentVertex;
					queue.add(i);
				}
			}
			checked[currentVertex]=true;
		}
		

		for (int i=0;i<vNum;i++)
		{
			if (adjacent[i]==-2)
			{
				adjacent[i]=-1;
			}
		}
		
		return adjacent;
	}

	@Override
	public int[] getDistances(Graph arg0, int arg1) 
	{
		int vNum = arg0.getNumberOfVertices();
		

		int lengths[] = new int[vNum];
		for (int i=0;i<vNum;i++)
		{
			lengths[i]=-2;
		}
		
		int[][] matrix = arg0.getEdgeMatrix();
		

		boolean checked[] = new boolean[vNum];
		

		Queue<Integer> queue = new LinkedList<Integer>();
		

		queue.add(arg1);
		checked[arg1] = true;
		lengths[arg1]=0;
		
		while (!queue.isEmpty())
		{
			int currentVertex = queue.poll();
			
			for(int i=0; i<vNum;i++)
			{
				if (matrix[currentVertex][i] == 1 && checked[i]==false && !queue.contains(i) && !(currentVertex==i))
				{
					lengths[i] = lengths[currentVertex]+1;
					queue.add(i);
				}
			}
			checked[currentVertex]=true;
		}
		for (int i=0;i<vNum;i++)
		{
			if (lengths[i]==-2)
			{
				lengths[i]=-1;
			}
		}
		
		return lengths;
		
	}

	@Override
	public int[][] getTimes(Graph arg0, int arg1) 
	{
		int vNum = arg0.getNumberOfVertices();
		
		times = new int[vNum][vNum];
		for (int i=0;i<vNum;i++)
		{
			times[i][0] = -2;
			times[i][1] = -2;
		}
		
		timeQueue = new LinkedList<Integer>();
		time = 0;
		int[][] matrix = arg0.getEdgeMatrix();
		checked = new boolean[vNum];
		
		DFS(arg1, vNum, matrix);
		
		return times;
	}
	
	private void DFS(int vertex, int vNum, int[][] matrix)
	{
		timeQueue.add(vertex);
		
		times[vertex][0] = time;
		time++;
		for(int i=0; i<vNum;i++)
		{
			if (matrix[vertex][i] == 1 && checked[i]==false && !timeQueue.contains(i) && !(vertex==i))
			{
				timeQueue.add(i);
				DFS(i, vNum, matrix);
			}
		checked[vertex] = true;
		timeQueue.poll();
		times[vertex][1] = time;
		time++;
		}
	}
}