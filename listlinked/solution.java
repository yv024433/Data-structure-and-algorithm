import CITS2200.*;


public class ListLinked implements CITS2200.List
{
	private Link before;
	
	private Link after;
	
	public ListLinked()
	{
		after = new Link(null,null);
		before = new Link(null,after);
	}
	
	public boolean isEmpty()
	{
		return before.successor == after;
	}
	
	public boolean isBeforeFirst(WindowLinked w)
	{
		return w.link == before;
	}
	
	public boolean isAfterLast(WindowLinked w)
	{
		return w.link == after;
	}
	
	public Object delete(WindowLinked w) throws OutOfBounds
	{
		if (isBeforeFirst(w) || isAfterLast(w)) 
			throw new OutOfBounds("Cannot delete at this point.");
		Object item = w.link.item;
		Link next = w.link.successor;
		w.link.item = next.item;
		w.link.successor = next.successor;
		if (after == next)
			after = w.link;
		return item;
	}
	
	public Object replace(Object e, WindowLinked w) throws OutOfBounds
	{
		if (isBeforeFirst(w) || isAfterLast(w))
			throw new OutOfBounds("Cannot replace data here.");
		Object item = w.link.item;
		w.link.item = e;
		return item;
	}
	
	public Object examine(WindowLinked w) throws OutOfBounds
	{
		if (isBeforeFirst(w) || isAfterLast(w))
			throw new OutOfBounds("cannot retrieve data here.");
		return w.link.item;
	}
	
	public void insertBefore(Object e, WindowLinked w) throws OutOfBounds
	{
		if (isBeforeFirst(w)) 
			throw new OutOfBounds("Cannot insert before start of list.");
		w.link.successor = new Link(w.link.item,w.link.successor);
		if (isAfterLast(w)) after = w.link.successor;
		w.link.item = e;
		w.link = w.link.successor;
	}
	
	public void insertAfter(Object e, WindowLinked w) throws OutOfBounds
	{
		if (isAfterLast(w))
			throw new OutOfBounds("Cannot insert after end of list.");
		w.link.successor = new Link(e,w.link.successor);
	}
	
	public void previous(WindowLinked w) throws OutOfBounds
	{
		if (isBeforeFirst(w))
			throw new OutOfBounds("w is at start of list.");
		Link current = before;
		while (current.successor != w.link)
			current = current.successor;
		w.link = current;
	}
	
	public void next(WindowLinked w) throws OutOfBounds
	{
		if (isAfterLast(w)) 
			throw new OutOfBounds("window is at end of list.");
		w.link = w.link.successor;
	}
	 
	public void afterLast(WindowLinked w)
	{
		w.link = after;
	}
	
	public void beforeFirst(WindowLinked w)
	{
		w.link = before;
	}
	
	public String toString()
	{
		String a = "";
		Link current = before;
		while (current != null)
		{
			a += current.item + " , ";
			current = current.successor;
		}
		return a;
	}
	
}