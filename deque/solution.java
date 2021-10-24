import CITS2200.Deque;
import CITS2200.Overflow;
import CITS2200.Underflow;



public class DequeCyclic<E> implements Deque<E> {

  int s;
  int leftMost;
  int rightMost;
  int currentAmount;

  E[] list;

  public DequeCyclic(int size) {
    s = size;

    @SuppressWarnings("unchecked")
    E[] temp_list = (E[]) new Object[size];
    list = temp_list;


    leftMost = 1;
    rightMost = 0;
    currentAmount = 0;

  }

  public boolean isEmpty() {
    return currentAmount == 0;
  }

  public boolean isFull() {
    return currentAmount == s;
  }

  public void pushLeft(E item) throws Overflow {
    if (isFull()){
      throw new Overflow("Stack is full");
    } else {
      if (leftMost == 0){
        leftMost = s;
      }
      currentAmount++;
      leftMost--;
      list[leftMost] = item;
      //System.out.println("left most " + leftMost + " | right most " + rightMost + " | current " + currentAmount);
    }
  }

  public void pushRight(E item) throws Overflow {
    if (isFull()){
      throw new Overflow("Stack is full");
    } else{
      if (rightMost == s-1){
        rightMost = -1;
      }
      rightMost++;
      currentAmount++;
      list[rightMost] = item;
      //System.out.println("left most " + leftMost + " | right most " + rightMost + " | current " + currentAmount);
    }
  }

  public E peekLeft() throws Underflow {
    if (isEmpty()){
      throw new Underflow("Queue is empty");
    }
    //System.out.println("left most " + leftMost + " | right most " + rightMost + " | current " + currentAmount);
    return list[leftMost];
  }

  public E peekRight() throws Underflow {
    if (isEmpty()){
      throw new Underflow("Queue is empty");
    }
    //System.out.println("left most " + leftMost + " | right most " + rightMost + " | current " + currentAmount);
    return list[rightMost];
  }

  public E popLeft() throws Underflow {
    if (isEmpty()){
      throw new Underflow("Queue is empty");
    } else {
      E tempItem = list[leftMost];
      // list[leftMost] = null;
      if (leftMost == s-1){
        leftMost = -1;
      }
      leftMost++;
      currentAmount--;
      //System.out.println("left most " + leftMost + " | right most " + rightMost + " | current " + currentAmount);
      return tempItem;
    }
  }

  public E popRight() throws Underflow {
    if (isEmpty()){
      throw new Underflow("Queue is empty");
    } else {
      E tempItem  = list[rightMost];
      // list[rightMost] = null;
      if (rightMost == 0){
        rightMost = s;
      }
      rightMost--;
      currentAmount--;
      //System.out.println("left most " + leftMost + " | right most " + rightMost + " | current " + currentAmount);
      return tempItem;
    }
  }
}