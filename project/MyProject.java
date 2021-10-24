//WEI YANG 21220208
import java.util.LinkedList;
import java.util.Queue;
import java.awt.Point;
import java.util.PriorityQueue;

/**
 * A project for CITS2200 in 2020 which contains four method to solve problems relevant to 
 * greyscale images specified as a 2D int arra.
 * @author WEI
 *
 */

public class MyProject implements Project {
	
	public int floodFillCount(int[][] image, int row, int col) {
		 final int value =image[row][col]; // the value of given pixels
		  if(value==0) {// if the value is black, no pixels will change
	        	return 0;
	        }else {
		 
		 
		 int count=0; // set to count the number of dequeue
		 
		
		 int[][] colours =new int[image.length][image[0].length]; //define if a pixel has been detected:1= detected 0= has not been detected 
		 Queue<Point> queue = new LinkedList<Point>();  // establish a queue to save point
		    colours[row][col] = 1; // set to be detected
		
	        queue.offer(new Point(row,col)); // add the origin point in queue
	        
	      
	       
	        while(!queue.isEmpty()) {
	            Point inner =  (Point) queue.poll();  // take a point out of queue
	            
	            count++;   // count the times of dequeue
	            int x = inner.x;   // get x,y from a point
	            int y = inner.y;
	           
	            
	            x++;    // examine down
	            if(x<image.length&&image[x][y]==value&&colours[x][y]==0) {
	            	colours[x][y] = 1;           	
	            queue.offer(new Point(x,y));
	            }
	            
	            x=x-2;  // examine up
	            if(-1<x&&image[x][y]==value&&colours[x][y]==0) {
	            	colours[x][y] = 1;           	           
	            queue.offer(new Point(x,y));
	            }
	            
	            
	            x++;
	            y++; // examine right
	            if(y<image[0].length&&image[x][y]==value&&colours[x][y]==0) {
	            	colours[x][y] = 1;           	
	            queue.offer(new Point(x,y));
	            
	            }
	            
	            y=y-2; // examine right
	            if(-1<y&&image[x][y]==value&&colours[x][y]==0) {
	            	colours[x][y] = 1;           	
	            queue.offer(new Point(x,y));
	            
	            }
	           
	                }
	            
	        return count;
	        }
	        }
		 
		
	

	public int brightestSquare(int[][] image, int k) {
		int n = image.length; // get rows of image
		int m = image[0].length; // get columns of image
		int initial =0;
		Queue<Integer> queue1=  new LinkedList<Integer>(); // set up two queues
		Queue<Integer> queue2=  new LinkedList<Integer>();
	
		for(int i=0;i<k;i++) {  // calculate the first square 
			int sum=0;
			for(int j=0;j<k;j++) {
				initial=initial+image[i][j];
				sum=sum+image[j][i];
			}
			queue1.offer(sum);
			queue2.offer(sum);
			
		}
		
		int maxSum=initial;
		int sum=initial;
		
		for(int i =k;i<m;i++) {  // calculate the first row of squares
			int addcol=0;
			for(int l=0;l<k;l++) { // calculate sum of the added column
				addcol=addcol+image[l][i];
			}
			queue1.offer(addcol);
			queue2.offer(addcol);
			int deletecol=(int) queue1.poll(); // get the sum of the delete column from queue1
	
			sum=sum+addcol-deletecol;
			maxSum=Math.max(maxSum, sum); //comparing results
			}

		    while(!queue1.isEmpty()) { // empty queue1 at end of a row
			queue1.poll();
		}
		
		
		
		for(int j=k; j<n; j++) {// calculate the rest rows
			int sum1 =0;
			for(int l=0;l<k;l++) { // calculate downward move using the data in queue2
				int a=(int) queue2.poll()+image[j][l]-image[j-k][l];
				queue1.offer(a);
				queue2.offer(a);
				sum1=sum1+a;
				
			}
						
				maxSum=Math.max(maxSum, sum1);
					
		
		for(int i =k;i<m;i++) { // calculate left move using the data in both queue1&queue2
			
			int addcol=(int) queue2.poll()+image[j][i]-image[j-k][i];// get previous data from queue2 to calculate the new column add. 
			
			
			queue1.offer(addcol);
			queue2.offer(addcol);
			
			int deletecol=(int) queue1.poll(); // get the delete column from queue1
	
			sum1=sum1+addcol-deletecol;
			maxSum=Math.max(maxSum, sum1); //comparing the results
			}
		while(!queue1.isEmpty()) { //empty queue1 at the end of a row
			queue1.poll();
		}		  
		}
		
		return maxSum;  //return the final result
	}
	
	
	
	
	public int darkestPath(int[][] image, int ur, int uc, int vr, int vc) {
		
		
		int[][] detected = new int[image.length][image[0].length]; //0 undetected; 1 : detected
		 
		PriorityQueue<Sample> queue = new PriorityQueue<Sample>(); //set up a heap with smaller number has higher priority
 		queue.add(new Sample(new Point (ur,uc), image[ur][uc]));  //add starting point with its priority in the heap 
 		
 		detected[ur][uc] = 1;  // set to be detected
 		int brightest=image[ur][uc];//set the initial maximum value
 		
 		while (!queue.isEmpty()) {	
 			
			Point inner = (Point) queue.remove().point; //get the current smallest option from the heap
			int x=inner.x;
			int y=inner.y;
		
			if(image[x][y]>brightest) { // update the biggest number
				brightest=image[x][y];
			}
			if(x==vr&&y==vc) {  // if the smallest option is the destination, the loop will be break and the answer will return
				break;	
			}
			
		    x++;  // examine down
            if(x<image.length&&detected[x][y]==0) {
            	detected[x][y] = 1;           	       	  	
            queue.add(new Sample(new Point(x,y), image[x][y]));            
            }
            
            x=x-2; // examine up
            if(-1<x&&detected[x][y]==0) {
            	detected[x][y] = 1;           		
            	queue.add(new Sample(new Point(x,y), image[x][y]));            
            }
            
            
            x++;// examine right
            y++;
            if(y<image[0].length&&detected[x][y]==0) {
            	detected[x][y] = 1;           	       	
            	queue.add(new Sample(new Point(x,y), image[x][y])); 
            }
            
            y=y-2; // examine left
            if(-1<y&&detected[x][y]==0) {
            	detected[x][y] = 1;           	
            	queue.add(new Sample(new Point(x,y), image[x][y]));
            }
		}
		return brightest; //return the answer 
	}

	

	public int[] brightestPixelsInRowSegments(int[][] image, int[][] queries) {
		 int C = image[0].length;  //get the number of column in the image
		 int[][] maxSegment = new int[image.length][2*C-1]; // Construct a segment tree 2D array        
	     int[] maximum=new int[queries.length]; //Construct a array to store results of each query
	       
	      for(int j =0; j<image.length;j++) {  // fill the segment tree with number
 
	        for (int i = 0; i < C; i++) { // last C elements of given row = the item of given row in the image
	            maxSegment[j][C-1+i] = image[j][i];
	        }
	        
	        for (int i = C - 2; i >=0; i--) { // fill the first C-1 position with parents of lower nodes
	            maxSegment[j][i] = Math.max(maxSegment[j][2 * i+1], maxSegment[j][2 * i + 2]);
	        }
	        
	      }
	    
	 
	      
	   for(int k=0;k<queries.length;k++) {// for combining segments into a query
		   int l=queries[k][1];  //get the index of starting point in image
		   int u=queries[k][2];  //get the index of ending point image
	        l += C-1;   //locate the position of the starting point in segment tree
	        u += C-1;   //locate the position of the ending point in segment tree
	        int max = 0 ; //initial max
	        
	        while (l < u) {// find proper segments to make up the query, details are in the report
	            if ((l & 1) == 0) {// l is even
	            	
	                max = Math.max(max, maxSegment[queries[k][0]][l]);
	                
	            }
	            
	            if ((u & 1) == 0) {// u is even
	                u--;
	                max = Math.max(max,  maxSegment[queries[k][0]][u]);
	            }
	            l = l/2;
	            u =u/2;
	            
	        }
	        maximum[k]=max;
	   }
	        return maximum;
	}	
	
	
	/**
	 * Set up a variable type named Sample which contains a point and a priority index
	 * @author WEI
	 *
	 */
	private class Sample implements Comparable<Sample>{

		public Point point;
		public int pointWeight;
		
		public Sample(Point point, int weight){
			this.point = point;
			pointWeight = weight;
		}
		
		public int compareTo(Sample current) {
	int currentWeight = current.pointWeight;
			
			if(pointWeight < currentWeight){
				return -1;
			}
			else if(pointWeight > currentWeight){
				return 1;
			}
			else return 0;
		}

	
		
	}
		    		
		    		
}
		
	
	


