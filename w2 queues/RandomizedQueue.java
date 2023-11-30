import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // set an initial capacity
    private static final int INIT_CAPACITY = 32;
    private Item[] queue;
    private int rear = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        // [warning] unchecked cast from object to item
        queue = (Item[]) new Object[INIT_CAPACITY];
    }

    private void resize(int newCapacity) {
        Item[] newQueue = (Item[]) new Object[newCapacity];
        for (int i = 0; i < rear; ++i) {
            newQueue[i] = queue[i];
        }
        queue = newQueue;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return rear == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return rear;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("arg cant be null!");
        }
        if (rear == queue.length) {
            resize(queue.length * 2);
        }
        queue[rear++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())  {
            throw new java.util.NoSuchElementException("empty now!");
        }
        if (rear < (queue.length / 4)) {
            resize(queue.length / 2);
        }

        int randomIndex = (int) (Math.random() * size());
        Item item = queue[randomIndex];
        queue[randomIndex] = queue[rear - 1];
        queue[--rear] = null;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("empty now!");
        }

        int randomIndex = (int) (Math.random() * size());
        return queue[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int current = rear;
        private int[] indexCollection;

        ListIterator() {
            indexCollection = new int[rear];
            for (int i = 0; i < rear; i++) {
                indexCollection[i] = i;
            }
            StdRandom.shuffle(indexCollection);
        }

        public boolean hasNext() {
            return current > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException("unsopported!");
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("null here!");
            }

            return queue[indexCollection[--current]];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> ins = new RandomizedQueue<Integer>();
        if (ins.isEmpty()) {
            System.out.println("empty now");
        }
        ins.enqueue(2);
        ins.enqueue(1);
        ins.enqueue(1);
        System.out.print("now check the size:  ");
        System.out.println(ins.size());
        
        // print the entire array
        Iterator<Integer> it = ins.iterator();
        while (it.hasNext()) {
            int val = it.next();
            System.out.print(val);
            System.out.print(" ");
        }
        System.out.println("");
        it = ins.iterator();
        while (it.hasNext()) {
            int val = it.next();
            System.out.print(val);
            System.out.print(" ");
        }
        System.out.println("");
        
        System.out.print("remove both side and check the size again:  ");
        ins.dequeue();
        System.out.println(ins.size());
        
        // print the entire array
        it = ins.iterator();
        while (it.hasNext()) {
            int val = it.next();
            System.out.print(val);
            System.out.print(" ");
        }
        System.out.println("");
    }

}