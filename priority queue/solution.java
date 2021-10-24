import CITS2200.*;


public class PriorityQueueLinked implements PriorityQueue<Object> {
	private Link front;

	public PriorityQueueLinked() {
		front = null;
	}

	private class Link {
		Object item;
		int priority;
		Link next;

		public Link(Object object, int p, Link n) {
			this.item = object;
			this.priority = p;
			this.next = n;
		}
	}

	public Object dequeue() throws Underflow {
		if (isEmpty()) {
			throw new Underflow("No items to dequeue");
		}
		Object frontItem = front.item;
		front = front.next;
		return frontItem;
	}

	public void enqueue(Object arg0, int arg1) throws IllegalValue {
		if (arg1 < 0) {
			throw new IllegalValue("Priority must be a non-negative integer");
		}
		if (isEmpty() || arg1 > front.priority) {
			front = new Link(arg0, arg1, front);
		} else {
			Link shiftingPlacement = front;
			while (shiftingPlacement.next != null && shiftingPlacement.next.priority >= arg1) {
				shiftingPlacement = shiftingPlacement.next;
			}
			shiftingPlacement.next = new Link(arg0, arg1, shiftingPlacement.next);
		}
	}

	public Object examine() throws Underflow {
		if (!isEmpty()) {
			return (Object) front.item;
		} else
			throw new Underflow("No items to examine");
	}

	public boolean isEmpty() {
		return front == null;
	}

	public Iterator<Object> iterator() {
		return new PriorityQueueIterator();
	}

	private class PriorityQueueIterator implements Iterator<Object> {
		Link currentIteration;
		Object[] iteratorList;
		int iterationCount = 0;
		int queueSize = 0;

		private PriorityQueueIterator() {

			if (front != null) {
				
				currentIteration = front;
				while (currentIteration != null && currentIteration.item != null) {
					queueSize++;
					currentIteration = currentIteration.next;
				}

				iteratorList = new Object[queueSize];

			
				currentIteration = front;
				for (int i = 0; i < queueSize; i++) {
					iteratorList[i] = currentIteration.item;
					currentIteration = currentIteration.next;
				}
			}
		}

		public boolean hasNext() {
			if (queueSize == 0) {
				return false;
			} else if (iterationCount < iteratorList.length) {
				return true;
			} else
				return false;
		}

		public Object next() throws OutOfBounds {
			if (hasNext()) {
				Object returnObject = iteratorList[iterationCount];
				iterationCount++;
				return returnObject;
			} else
				throw new OutOfBounds("No more items to return");
		}

	}

}