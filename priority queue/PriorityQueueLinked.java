import CITS2200.IllegalValue;
import CITS2200.Iterator;
import CITS2200.Link;
import CITS2200.OutOfBounds;
import CITS2200.PriorityQueue;
import CITS2200.Queue;
import CITS2200.Underflow;

/**
 * First in First out queue with higher priority insert before lower priority
 * @author WEI
 *
 */

	public class PriorityQueueLinked implements PriorityQueue<Object> {
/**
 * @param  front Indicate the the first link in the queue
 * @param  priority the priority of objects
 * @param  next the next link
 * @param  element the object in the link
 */
		
		/**
		 * Constructor of the priority queue.
		 */
		
		public PriorityQueueLinked() { 
			this.front = null; 
			}
		private  PriorityQueueLinked front;
		int priority;
		PriorityQueueLinked next;
		Object element;
	
	    /**
	     * Construct a new empty priority queue.
	     */
		
	    public PriorityQueueLinked(Object e, int p, PriorityQueueLinked n) {
	        this.next = n;
	        this.priority=p;
	            this.element =e;
	    }
	    
	    
		/**
		 * Dequeue the top, highest priority link in this queue.
		 *  @return The item in the dequeue link
		 * @throws Underflow If there are no elements currently in the queue
		 */
		public Object dequeue() throws Underflow {
			if(!isEmpty()) {
				Object temp = front.element;
				front=front.next;
				return temp;
			}else throw new Underflow("Empty Queue");
			
		}
		
		/**
		 * Enqueue a new element with priority.
		 * @param  e the new object which enqueue 
         * @param  p the priority of the item
		 */

	
		public void enqueue(Object e, int p) throws IllegalValue {
			if(isEmpty()||p>front.priority) {
				front = new PriorityQueueLinked(e,p,front);
			}else {
				PriorityQueueLinked l = front;
				while(l.next !=null&&l.next.priority>=p) {
					l=l.next;
					
					}
				l.next=new PriorityQueueLinked(e,p,l.next);
				}
			
		}

		/**
		 * Get the next element.
		 * @return The object of the top-most, highest priority link in this queue
		 * @throws Underflow If there are no elements currently in the queue
		 */
		public Object examine() throws Underflow {
			if(!isEmpty()) {
				return front.element;
			}else throw new Underflow("Empty Queue");
			
		}

		
		
		/**
		 * @return true if there are no elements in this queue, false otherwise
		 */
		public boolean isEmpty() {
			return front == null;			
		}
		

		@Override
		public Iterator<Object> iterator() {
			
			
			return new iter(this);
		}
		
		
		
		
		/**
	     * An iterator that traverses a priority queue.
	     *
	     * @param <Object> the type of element within the queue being iterated
	     */
		public class iter implements Iterator<Object>{
			
			
			/**
	         * Construct an iterator to iterate the priority queue.
	         *  @param Pqueue the priority queue which will be iterated 
	         */QueueLinked queue;
			public iter(PriorityQueueLinked Pqueue) {
				queue = new QueueLinked();
				PriorityQueueLinked link = Pqueue.front;
				
				while(link != null) {
		                queue.enqueue(link.element);

		                link = link.next;
			}
			}
	        
			 /**
	         * Check whether there are any more items in the priority queue that have not yet been iterated.
	         *
	         * @return true iff there are more items to be iterated, false otherwise
	         */
			public boolean hasNext() {
				
				return !queue.isEmpty();
			}


	        /**
	         * Find the next item in the priority.
	         *
	         * @return the next item to be iterated in the queue
	         *
	         * @throws OutOfBounds if there are no more items to be iterated
	         */
			
			public Object next() throws OutOfBounds {
				 
				 return queue.dequeue();
	        }
		
		}
		
	     /**
         * First in First out queue implement
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
