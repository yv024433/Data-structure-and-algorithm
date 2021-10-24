
import CITS2200.Stack;
import CITS2200.Overflow;
import CITS2200.Underflow;
/**
* A class to make a stack acts as a last-in, first-out (LIFO) buffer
* A collection class in which the last element added to the collection is the first element to be removed.
* Access to elements is via the top of the stack 
* @author WEI YANG
*/

public class StackBlock implements Stack
{
	
	int maxSize;
	int top;
	Object arr[];
	
	/**
	 * Establish a stack with size s
	 * @param s the size of a stack
	 */
	public  StackBlock(int s) {
		maxSize = s;
		arr = new Object[maxSize];
		top = 0;
	}
	

 /**
  *  Check if the stack is empty.
  *  return true iff the stack is empty, false otherwise
  */
	public boolean isEmpty(){
		if(top==0) {
			return true;
		}
		else {
			return false;
		}
		}
		
		
		
	/**
	 * Check if the stack is full.
	*return true iff the stack is full, false otherwise 
	**/
	public boolean isFull(){
		if(top==maxSize) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	

	/**
	 * push Object o onto the top of the stack, 
	 * or throw an Overflow exception if the stack is full 
	 * @param o the object added on the top of a stack
	 */
	public void push(Object o) {
		if(isFull()) 
		{
			
			throw  new Overflow("the stack is full");
		}
		else {
		arr[top] = o;
		top++;
	}
		
	}

	
	/**
	 * return the Object on top of the stack, 
	 * or throw an Underflow exception if the stack is empty
	 */

	public Object examine(){
		
		if(isEmpty())
		{
			throw new Underflow("the stack is empty");
		}
		
		else{
			return  arr[top-1];
		}
		
	}
	
	
	/**
	 * remove and return the Object on the top of the stack, 
	 * or throw an Underflow exception if the stack is empty 
	 */
	
	public Object pop(){
		Object o = null;
		if(isEmpty())
		{
			throw new Underflow("the stack is empty");
		}
		else{
		        top--;
		       o = arr[top];
		        arr[top] = null;
		        }
		        return o;
	
	}
}
