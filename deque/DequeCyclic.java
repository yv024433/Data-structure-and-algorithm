
import CITS2200.Deque;
import CITS2200.Overflow;
import CITS2200.Underflow;

/**
 *  Establish a array which allows objects to be added and deleted from both ends of the queue.
 * @author WEI*
 */
public class DequeCyclic implements Deque<Object>{

	
	int maxSize;
	int first;
	int last;
	Object arr[];
	
	/**
	 * Establish a Deque with size s
	 * @param s the size of a Deque
	 */
	public DequeCyclic (int s) {
		maxSize = s+1;
		arr = new Object[maxSize];
		first = 0;
		last = 1;
	}
	
	/**
	 * return true iff the deque is empty, 
	 * false otherwise. 
	 */
	public boolean isEmpty() {
		
		if(first%maxSize == (last+maxSize-1)%maxSize) {
			return true;
		}
		else {
			return false;
		}
		}
	
/**
 * return true iff the deque is full, false otherwise. 
 */

	public boolean isFull() {
		if(first%maxSize == last%maxSize) {
			return true;
		}
		else {
			return false;
		}
	}

/**
* push Object o onto the left of the deque, 
* or throw an Overflow exception if the deque is full 
* @param c the object added on the left of a deque.
*/
	public void pushLeft(Object c) throws Overflow {
		if(isFull()) 
		{
			
			throw  new Overflow("the stack is full");
		}
			
		else {
		arr[first] = c;
		first=(first+maxSize-1)%maxSize;
		}
	}
/**
 * push Object o onto the right of the deque, 
 * or throw an Overflow exception if the deque is full
 * @param c the object added on the right of a deque
 */
	@Override
	public void pushRight(Object c) throws Overflow {
		if(isFull()) 
		{
			
			throw  new Overflow("the stack is full");
		}
		
		else {
			arr[last] = c;
			last=(last+1)%maxSize;
		}
	}
	
	
	/**
	 * return the left-most object in the deque, 
	 * or throw an Underflow exception if the deque is empty. 
	 */
	public Object peekLeft() throws Underflow {
		if(isEmpty())
		{
			throw new Underflow("the stack is empty");
		}
		
		else{
			return  arr[(first+1)%maxSize];
		}
	}

	/**
	 * return the right-most object in the deque, 
	 * or throw an Underflow exception if the deque is empty
	 */
	public Object peekRight() throws Underflow {
		if(isEmpty())
		{
			throw new Underflow("the stack is empty");
		}
		
		else{
			return  arr[(last+maxSize-1)%maxSize];
		}
	}
	

/**
 * remove and return the left-most object in the deque, 
 * or throw an Underflow exception if the deque is empty. 
 */
	public Object popLeft() throws Underflow {
		Object o = null;
		if(isEmpty())
		{
			throw new Underflow("the stack is empty");
		}
		else{
			first=(first+1)%maxSize;
			o = arr[first];
		        arr[first] = null;
		        }
		return o;
	
	}
	

/**
 * remove and return the right-most object in the deque, 
 * or throw an Underflow exception if the deque is empty. 
 */
	public Object popRight() throws Underflow {
		Object o = null;
		if(isEmpty())
		{
			throw new Underflow("the stack is empty");
		}
		else{
			last=(last+maxSize-1)%maxSize;
			o = arr[last];
		        arr[last] = null;		      
		        }
		        return o;
	}



}