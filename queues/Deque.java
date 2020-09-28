/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size = 0;

    private class Node {
        Item item;
        Node next = null;
        Node prev = null;
        public  Node(Item arg) {
            item = arg;
        }
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return  size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Must pass item to add");
        }
        size++;
        if (isEmpty()) {
            first = new Node(item);
            last = first;
            return;
        }
        Node temp = first;
        first = new Node(item);
        first.next = temp;
        temp.prev = first;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Must pass item to add");
        }
        size++;
        if (isEmpty()) {
            first = new Node(item);
            last = first;
            return;
        }
        Node temp = last;
        last = new Node(item);
        temp.next = last;
        last.prev = temp;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        Node temp = first;
        first = first.next;
        if (first != null) first.prev = null;
        else last = null;
        size--;
        return temp.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        Node temp = last;
        last = last.prev;
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        size--;
        return temp.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements  Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public  Item next() {
            if (current == null) {
                throw new NoSuchElementException("All elements traversed");
            }
            Item item = current.item;
            current = current.next;
            return item;
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
        Deque<String> deque = new Deque<String>();
        StdOut.println(deque.isEmpty());
        try {
            deque.removeFirst();
        } catch (NoSuchElementException ex) {
            StdOut.println(ex.getMessage());
        }
        for (String arg: args) deque.addLast(arg);
        StdOut.println(deque.isEmpty());
        for (String arg: args) deque.addFirst(arg);
        for (String i: deque) StdOut.print(i);
        StdOut.print("\n");
        for (int i = 0; i < args.length; i++) StdOut.print(deque.removeLast());
        StdOut.print("\n");
        for (int i = 0; i < args.length; i++) StdOut.print(deque.removeFirst());
        StdOut.print("\n");
        StdOut.println(deque.size());
        StdOut.println(deque.isEmpty());
    }
}
