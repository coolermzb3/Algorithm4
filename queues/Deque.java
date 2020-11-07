/* *****************************************************************************
 *  Author:          Yu Zihong
 *  Date:            2020/10/28 21:43
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node first, last;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    private void validItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    private void validEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        validItem(item);

        Node oldFirst = first;
        first = new Node();
        first.item = item;

        if (isEmpty()) {
            last = first;
        }
        else {
            oldFirst.prev = first;
            first.next = oldFirst;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        validItem(item);
        Node oldLast = last;
        last = new Node();
        last.item = item;

        if (isEmpty()) {
            first = last;
        }
        else {
            oldLast.next = last;
            last.prev = oldLast;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        validEmpty();
        Item item = first.item;
        first = first.next;
        size--;

        if (isEmpty()) {
            // only one element, after remove, last should be set to null
            last = null;
        }
        else {
            // remove reference, waiting for garbage collection
            first.prev = null;
        }

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        validEmpty();
        Item item = last.item;
        last = last.prev;
        size--;

        if (isEmpty()) {
            // only one element, after remove, first should be set to null
            first = null;
        }
        else {
            // remove reference, waiting for garbage collection
            last.next = null;
        }

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // iterator interface
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            /* not supported */
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> mydeque = new Deque<>();
        System.out.println(mydeque.size());
        mydeque.addFirst(1);
        mydeque.addFirst(2);
        System.out.println("rem last " + mydeque.removeLast());
        mydeque.addLast(4);
        System.out.println("removefirst: " + mydeque.removeFirst());
        System.out.println("removelast: " + mydeque.removeLast());
        // System.out.println("removelast: " + mydeque.removeLast());
        System.out.println(mydeque.isEmpty());
        mydeque.addFirst(1);
        mydeque.addLast(2);
        System.out.println(mydeque.size());
        System.out.println(mydeque.isEmpty());
        for (int d : mydeque) {
            System.out.println(d);
        }
    }

}
