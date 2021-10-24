import CITS2200.Link;
import CITS2200.List;
import CITS2200.OutOfBounds;
import CITS2200.WindowLinked;

  /**
   * Establish a singly-linked list which allows objects to be added, replaced and deleted from any position of a list.
   * The list ADT  implement the CITS2200.List interface
   * @author WEI
   *
   */
public class ListLinked implements List{
	
	private Link before;
	private Link after;
	
	
  /**
   * Establish a linked list with initial nodes.
   */
	public ListLinked() {
		after = new Link (null, null);
		before = new Link (null, after);
	}
	

  /**
   * Place the window at one place after the last node in the list
   * @param  w Indicate the position of the window
   */
	public void afterLast(WindowLinked w) {
		w.link = after;
		
	}

	/**
	 * Place the window at one place before the first node in the list
	 * @param  w Indicate the position of the window
	 */
	public void beforeFirst(WindowLinked w) {
		w.link = before;
		
	}

	/**
	 * Delete the node which the window is at or throw an exception
	 * @param  w Indicate the position of the window
	 * @return the item deleted
	 */
	public Object delete(WindowLinked w) throws OutOfBounds {
		Object o = null;
		if(isBeforeFirst(w)||isAfterLast(w)) {
			throw new OutOfBounds("Calling next after list end.");
		}
		else if (w.link.successor != after )
		{	
			o =w.link.item;
			w.link.item = w.link.successor.item;
			w.link.successor= w.link.successor.successor;	
		}
		else {
			o =w.link.item;
			after=w.link;
			
		}
		return o;
	}

	/**
	 * examine the item where the window is at or throw an exception
	 * @param  w Indicate the position of the window
	 * @return the item examined
	 */
	public Object examine(WindowLinked w) throws OutOfBounds {
		if(isBeforeFirst(w)||isAfterLast(w)) {
			throw new OutOfBounds("Calling next after list end.");
		}
		else return  w.link.item;
	}

	
	
	/**
	 * add a node after the window  or throw an exception if the window is after the last node
	 * @param  w Indicate the position of the window
	 * @param  e The item added in the list
	 */
	public void insertAfter(Object e, WindowLinked w) throws OutOfBounds {
		if(!isAfterLast(w)) {
			w.link.successor =new Link(e,w.link.successor);	
		}
		else throw new OutOfBounds("Calling next after list end."); 		
	}
		
	
	/**
	 * add a node in front of the window  or throw an exception if the window is before the first node
	 * @param  w Indicate the position of the window
	 * @param  e The item added in the list
	 */
	public void insertBefore(Object e, WindowLinked w) throws OutOfBounds {
		if(!isBeforeFirst(w)) {
			w.link.successor =new Link(w.link.item,w.link.successor);
			if (isAfterLast(w)) 
				after = w.link.successor;
				w.link.item=e;
				w.link= w.link.successor;
			
		}
		else throw new OutOfBounds("Calling next after list end."); 		
	}

	
	/**
	 * return true if the window is after the last node, false otherwise. 
	 * @param  w Indicate the position of the window
	 */	
	public boolean isAfterLast(WindowLinked w) {
		
		return w.link == after;
	}

	/**
	 * return true if the window is before the first node, false otherwise. 
	 * @param  w Indicate the position of the window
	 */	
	public boolean isBeforeFirst(WindowLinked w) {

		return w.link == before;
	}

	/**
	 * return true if the list is empty, false otherwise. 
	 */	
	public boolean isEmpty() {
		return before.successor == after;
	}

	/**
	 * Move the window one position forward on the list. 
	 * @param  w Indicate the position of the window
	 */	
	public void next(WindowLinked w) throws OutOfBounds {
		if(!isAfterLast(w)) {
			w.link = w.link.successor;
			}
		else throw new OutOfBounds("Calling next after list end."); 
		
		
	}

	/**
	 * Move the window one position backward on the list. 
	 * @param  w Indicate the position of the window
	 */	
	public void previous(WindowLinked w) throws OutOfBounds {
		if(!isBeforeFirst(w)) {
			Link current = before.successor;
			Link previous = before;
			while(current!=w.link) {
				previous= current;
				current= current.successor;
			}
			w.link=previous;
		}
		else throw new OutOfBounds("Calling next after list end."); 
		
	}

	/**
	 * replace the item which the window is at with item e or throw an exception if the window is after the last node or before the first one.
	 * @param  w Indicate the position of the window
	 * @param  e The item added in the list
	 * @return the item which has been replaced
	 */
	public Object replace(Object e, WindowLinked w) throws OutOfBounds {
		Object o = null;
		if(isBeforeFirst(w)||isAfterLast(w)) {
			throw new OutOfBounds("Calling next after list end.");
		}
		else 
		{
		    o = w.link.item;
			w.link.item =e;
		
		}
		
			
			return  o;
	
	
	}
	

}
