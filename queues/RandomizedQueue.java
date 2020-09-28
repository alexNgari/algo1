/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int first, last;
    private Item[] queue;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.first = -1;
        this.last = -1;
        this.queue = (Item[]) new Object[8];
    }

    private void reduceSize() {
        Item[] temp = (Item[]) new Object[Math.floorDiv(queue.length, 2)];
        int i = 0;
        do {
            temp[i++] = queue[first];
            first = (first+1) % queue.length;
        } while (first != (last+1) % queue.length);
        queue = temp;
        first = 0;
        last = i-1;
    }

    private void increaseSize() {
        Item[] temp = (Item[]) new Object[queue.length*2];
        int i = 0;
        int j = first;
        do {
            temp[i++] = queue[j];
            j = (j+1) % queue.length;
        } while (j != first);
        queue = temp;
        first = 0;
        last = i-1;
    }

    private boolean isFull() {
        return size == queue.length;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null item not allowed!");
        }
        if (isFull()) {
            increaseSize();
        } else if (isEmpty()) {
            first++;
        }
        queue[++last] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!");
        }
        if (size == 1) {
            Item temp = queue[first];
            queue[first] = null;
            first = -1;
            last = -1;
            size--;
            return temp;
        }
        int index = (StdRandom.uniform(size) + first) % queue.length;
        Item temp = queue[index];
        queue[index] = queue[last];
        queue[last--] = null;
        size--;
        if ((queue.length > 10) && (size < queue.length/4)) {
            reduceSize();
        }
        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!");
        }
        if (size == 1) {
            return queue[first];
        }
        int index = (StdRandom.uniform(size) + first) % queue.length;
        return queue[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator();
    }

    private class RQIterator implements  Iterator<Item> {
        private Item[] iterQueue;
        private int j;

        public RQIterator() {
            if (size < 1) {
                j = -1;
            } else {
                iterQueue = (Item[]) new Object[size];
                int index = 0;
                int i = first;
                j = size - 1;
                do {
                    iterQueue[index++] = queue[i];
                    i = (i + 1) % queue.length;
                } while (i != (last + 1) % queue.length);
            }
        }

        public boolean hasNext() {
            return j >= 0;
        }

        public  Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("All elements traversed");
            }
            if (j == 0) {
                return iterQueue[j--];
            }
            int index = StdRandom.uniform(j+1);
            Item temp = iterQueue[index];
            iterQueue[index] = iterQueue[j];
            iterQueue[j--] = null;
            return temp;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove is unsupported in this structure");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }
        RandomizedQueue<String> rqueue = new RandomizedQueue<String>();
        StdOut.println(rqueue.isEmpty());
        StdOut.println(rqueue.size());
        for (String arg: args) rqueue.enqueue(arg);
        StdOut.println(rqueue.isEmpty());
        StdOut.println(rqueue.size());
        for (String i: rqueue) StdOut.print(String.format("%s ", i));
        StdOut.print('\n');
        int size = rqueue.size();
        for (int i = 0; i < size; i++) StdOut.print(String.format("%s ",
                                                                  rqueue.dequeue()));
        StdOut.print('\n');
        StdOut.println(rqueue.isEmpty());
        StdOut.println(rqueue.size());
    }

}