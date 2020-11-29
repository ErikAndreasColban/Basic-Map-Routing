package bearmaps.utils.pq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;

/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;
    private HashMap<E, Integer> HashedContents;
    private int size;
    // TODO: YOUR CODE HERE (no code should be needed here if not 
    // implementing the more optimized version)

    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        HashedContents = new HashMap<>();
        contents.add(null);
        size = 0;
    }

    public ArrayList<E> getContents() {
        return contents;
    }
    /* Returns the element at index INDEX, and null if it is out of bounds. */
    private E getElement(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
        HashedContents.put(element, index);
    }

    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        setElement(index2, element1);
        setElement(index1, element2);
        HashedContents.put(element1, index2);
        HashedContents.put(element2, index1);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
        return index * 2;
    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
        return index * 2 + 1;
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
        return index / 2;
    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. If the elements are equal, return either index. */
    private int min(int index1, int index2) {
        E el1 = getElement(index1);
        E el2 = getElement(index2);
        if (el1 == null) {
            return index2;
        }
        if (el2 == null) {
            return index1;
        }
        return el1.compareTo(el2) <= 0 ? index1 : index2;
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E findMin() {
        return getElement(1);
    }

    /* Bubbles up the element currently at index INDEX. */
    private void bubbleUp(int index) {
        int parent = getParentOf(index);
        if (parent != 0) {
            if ((getElement(index)).compareTo(getElement(parent)) < 0) {
                swap(index, parent);
                bubbleUp(parent);
            }
        }
    }

    /* Bubbles down the element currently at index INDEX. */
    private void bubbleDown(int index) {
        int left = getLeftOf(index);
        int right = getRightOf(index);
        if (left < contents.size()) {
            int min = min(left, right);
            if (getElement(index).compareTo(getElement(min)) > 0) {
                swap(index, min);
                bubbleDown(min);
                return;

            }
        }
        return;
    }

    /* Returns the number of elements in the MinHeap. */
    public int size() {
        return size;
    }

    /* Inserts ELEMENT into the MinHeap. If ELEMENT is already in the MinHeap,
       throw an IllegalArgumentException.*/
    public void insert(E element) {
        if (contains(element)) {
            throw new IllegalArgumentException("Element is already in the MinHeap.");
        }
        size++;
        contents.add(element);
        HashedContents.put(element, size());
        bubbleUp(size());

    }


    /* Returns and removes the smallest element in the MinHeap. */
    public E removeMin() {
        E rtn = getElement(1);
        swap(1, size());
        contents.remove(size());
        HashedContents.remove(rtn);
        size--;
        if (size() > 0) {
            bubbleDown(1);
        }
        return rtn;
    }

    /* Replaces and updates the position of ELEMENT inside the MinHeap, which
       may have been mutated since the initial insert. If a copy of ELEMENT does
       not exist in the MinHeap, throw a NoSuchElementException. Item equality
       should be checked using .equals(), not ==. */
    public void update(E element) {

        // element is not null
        if (!HashedContents.containsKey(element)) {
            throw new NoSuchElementException();
        }
        int i = HashedContents.get(element);
            // contents.get(i).equals(elements)
//        contents.remove(i);
//        insert(element);
        if (contents.get(i).compareTo(element) > 0) {
                setElement(i, element);
                bubbleUp(i);
        }
        else if (contents.get(i).compareTo(element) < 0) {
                setElement(i, element);
                bubbleDown(i);
        }

    }


    /* Returns true if ELEMENT is contained in the MinHeap. Item equality should
       be checked using .equals(), not ==. */
    public boolean contains(E element) {
        return HashedContents.containsKey(element);
        /**if (findMin() == null) {
            return false;
        }
        if (element.equals(findMin())) {
            return true;
        }
        int cmp = element.compareTo(findMin());
        if (cmp > 0) {
            return false;
        }
        else {
            boolean leftcheck;
            boolean rightcheck;
            if (getLeftOf(1) > size()) {
                return false;
            }
            else {
                if (containsHelper(element, getLeftOf(1)) == 0) {
                    leftcheck = false;
                }
                else {
                    return true;
                }
            }
            if (getRightOf(1) > size()) {
                rightcheck = false;
            }
            else {
                if (containsHelper(element, getRightOf(1)) == 0 ) {
                    rightcheck = false;
                }
                else { return true; }
            }
            return (leftcheck || rightcheck);
            } */
    }


    /**private int containsHelper(E element, int child) {
        int cmp = element.compareTo(getElement(child));
        int leftcheck;
        int rightcheck;
        if (element.equals(getElement(child))) {
            return child;
        }
        if (cmp > 0) {
            return 0;
        }
        else {
            if (getLeftOf(child) > size()) {
                return 0;
            }
            else {leftcheck = containsHelper(element, getLeftOf(child)); }
            if (getRightOf(child) > size()) {
                rightcheck = 0;
            }
            else {rightcheck = containsHelper(element, getRightOf(child)); }
            if (leftcheck == 0  && rightcheck == 0) {
                return 0;
            }
            else {return min(leftcheck, rightcheck);}
        }
    } */

}
