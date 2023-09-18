import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> {

    Item[] hiddenArray;
    int size;
    int pointer;
    private static int initArraySize = 4;

    public RandomizedQueue() {
        hiddenArray = (Item[]) new Object[initArraySize];
        size = 0;
        pointer = 0;
    }

    public boolean isEmptry() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (pointer >= hiddenArray.length) {
            resize(hiddenArray.length * 2);
        }
        hiddenArray[pointer] = item;
        pointer++;
        size++;
    }

    private void resize(int newSize) {
        Item[] newArray = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newArray[i] = hiddenArray[i];
            hiddenArray = newArray;
        }
    }

    public Item dequeue() {
        if (size <= 0) {
            throw new NoSuchElementException("The queue is empty, cannot dequeue anymore!");
        }
        int index = (int) (size * Math.random());
        Item tempValue = hiddenArray[index];
        hiddenArray[index] = hiddenArray[pointer - 1];
        hiddenArray[pointer - 1] = null;
        pointer--;
        size--;

        if (size <= hiddenArray.length / 4 && size >= initArraySize) {
            resize(size / 2);

        }
        return tempValue;
    }

    public Item sample() {
        if (size <= 0) {
            throw new NoSuchElementException("The queue is empty, cannot dequeue anymore!");
        }
        int index = (int) (size * Math.random());
        Item tempValue = hiddenArray[index];
        return tempValue;
    }

    public Iterator<Item> iterator() {
        IteratorClass<Item> iterator = new IteratorClass<>();
        return iterator;
    }

    public class IteratorClass<Item> implements Iterator<Item> {
        Item[] iteratorArray;
        int sizeCount = size;

        public IteratorClass() {
            int tempSize = size;
            iteratorArray = (Item[]) new Object[size];
            Item[] tempArray = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                tempArray[i] = (Item) hiddenArray[i];
            }

            for (int i = 0; i < size; i++) {
                int index = (int) (Math.random() * tempSize);
                iteratorArray[i] = tempArray[index];
                tempArray[index] = (Item) tempArray[tempSize - 1];
                tempSize--;
            }
        }

        public boolean hasNext() {
            return sizeCount > 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("The queue is empty, cannot dequeue anymore!");
            }
            else {
                sizeCount--;
                return iteratorArray[sizeCount];
            }
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> newQueue = new RandomizedQueue<>();
        for (int i = 0; i < 20; i++) {
            newQueue.enqueue(i);
            Iterator<Integer> newIterator = newQueue.iterator();

            while (newIterator.hasNext()) {
                System.out.println(newIterator.next());
            }
        }
    }
}
