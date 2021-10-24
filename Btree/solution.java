import CITS2200.BinaryTree;

import CITS2200.Iterator;

import CITS2200.OutOfBounds;



import java.util.LinkedList;

import java.util.Queue;



public class BinTree<E> extends BinaryTree<E> {



	public BinTree() {

		super();

	}

	

	public BinTree(E item, BinaryTree<E> left, BinaryTree<E> right) {

		super(item, left, right);

	}

	

	@Override

	public boolean equals(Object o) {

		if (o instanceof BinTree && o != null) {

			if (isEmpty() && ((BinTree<?>)o).isEmpty()) return true;

			if ((isEmpty() && !((BinTree<?>)o).isEmpty()) || (!isEmpty() && ((BinTree<?>)o).isEmpty())) return false;

			

			return ((BinTree<?>)o).getItem().equals(getItem()) && getLeft().equals(((BinTree<?>)o).getLeft()) && getRight().equals(((BinTree<?>)o).getRight());

		}

		

		return false;

	}



	@Override

	public Iterator<E> iterator() {

		return new BinTreeIterator<E>(this);

	}

	

	class BinTreeIterator<E> implements Iterator<E> {



		Queue<E> q;

		java.util.Iterator<E> i;

		

		public BinTreeIterator(BinTree<E> b) {

			q = new LinkedList<E>();

			inOrder(b);

			i = q.iterator();

		}

		

		@Override

		public boolean hasNext() {

			return i.hasNext();

		}



		@Override

		public E next() throws OutOfBounds {

			return i.next();

		}

		

		public void inOrder(BinaryTree<E> binaryTree) {

			if(!binaryTree.isEmpty()) {

				inOrder(binaryTree.getLeft());

				q.add(binaryTree.getItem());

				inOrder(binaryTree.getRight());

			}

		}

		

	}

	

}