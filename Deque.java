import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node sentinel;
    private int size;

    public Deque() {
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    private class Node {
        Item value;
        Node next;
        Node prev;

        Node(Item value) {
            this.value = value;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addLast(Item entry) {
        if (entry == null) {
            throw new IllegalArgumentException("Cannot add null to the deque.");
        }
        else {
            Node newNode = new Node(entry);
            newNode.next = sentinel;
            newNode.prev = sentinel.prev;
            sentinel.prev.next = newNode;
            sentinel.prev = newNode;
            size++;
        }
    }

    public void addFirst(Item entry) {
        if (entry == null) {
            throw new IllegalArgumentException("Cannot add null to the deque.");
        }
        else {
            Node newNode = new Node(entry);
            newNode.next = sentinel.next;
            newNode.prev = sentinel;
            sentinel.next.prev = newNode;
            sentinel.next = newNode;
            size++;
        }
    }

    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("can't remove from an empty deque.");
        }
        else {
            Item tempValue = sentinel.next.value;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size--;
            return tempValue;
        }
    }

    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("can't remove from an empty deque.");
        }
        else {
            Item tempValue = sentinel.prev.value;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size--;
            return tempValue;
        }
    }

    private class InnerIterator implements Iterator<Item> {
        Node startingNode = sentinel.next;

        public boolean hasNext() {
            return startingNode != sentinel;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Nothing to iterate");
            }
            else {
                Item entry = startingNode.value;
                startingNode = startingNode.next;
                return entry;
            }
        }
    }

    public Iterator<Item> iterator() {
        InnerIterator iterator = new InnerIterator();
        return iterator;
    }

    public static void main(String[] args) {
        Deque<Integer> numberDeque = new Deque<>();
        numberDeque.addFirst(12);
        numberDeque.addFirst(45);
        numberDeque.addFirst(56);
        numberDeque.addLast(7);
        numberDeque.addLast(8);
        System.out.println("___" + numberDeque.size());
        System.out.println(numberDeque.removeFirst());
        System.out.println(numberDeque.removeLast());
        System.out.println("___" + numberDeque.size());
        for (int i : numberDeque) {
            System.out.println(i + " ");
        }
    }
}
