/* *****************************************************************************
 *  Author:          Yu Zihong
 *  Date:            2020/10/29 14:33
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
        size = 0;
    }

    // throw exception if queue is empty
    private void validEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // resize array
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == s.length) {
            resize(2 * s.length);
        }
        s[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        validEmpty();
        int index = StdRandom.uniform(size);
        Item item = s[index];
        // copy the last item to the location of the just removed item, and release last
        s[index] = s[--size];
        s[size] = null;

        if (size > 0 && size == s.length / 4) {
            resize(s.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        validEmpty();
        int index = StdRandom.uniform(size);
        return s[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int current = 0;
        private final int[] indexarray;

        public RandomizedQueueIterator() {
            indexarray = new int[size];
            for (int i = 0; i < size; i++) {
                indexarray[i] = i;
            }
            StdRandom.shuffle(indexarray);
        }

        public boolean hasNext() {
            return current < size;
        }

        public void remove() {
            /* not supported */
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == size) {
                throw new NoSuchElementException();
            }
            return s[indexarray[current++]];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        System.out.println(rq.size());
        System.out.println(rq.isEmpty());
        rq.enqueue(1);
        rq.enqueue(2);
        System.out.println(rq.dequeue());
        rq.dequeue();
        System.out.println(rq.size());
        System.out.println(rq.isEmpty());
        rq.enqueue(3);
        System.out.println(rq.sample());
        rq.enqueue(4);
        rq.enqueue(5);

        for (int d : rq) {
            System.out.println(d);
        }
    }

}
