import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size;

    private class Node {
        Item item;
        Node next, prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
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
        if (item == null) {
            throw new IllegalArgumentException("oh my god, u are pushing a null one.");
        }

        size += 1;
        Node oldFisrt = first;
        first = new Node();
        first.item = item;
        first.next = oldFisrt;
        first.prev = null;
        
        if (size == 1) {
            last = first;
        } else {
            oldFisrt.prev = first;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("oh my god, u are pushing the null!");
        }

        size += 1;
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        
        if (size == 1) {
            first = last;
        } else {
            oldLast.next = last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("nono! is empty now!");
        }

        Item tmp = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        }
        size -= 1;

        if (size == 0) {
            last = null;
        }
        return tmp;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("nono! It's empty now!");
        }

        Item tmp = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        }
        size -= 1;
        
        if (size == 0) {
            first = null;
        }
        return tmp;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("unsopported!");
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("null here!");
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> ins = new Deque<Integer>();
        if (ins.isEmpty()) {
            System.out.println("empty now");
        }
        
        ins.addFirst(3);
        ins.addFirst(2);
        ins.addFirst(1);
        ins.addLast(1);
        ins.addLast(2);
        ins.addLast(3);

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
        
        System.out.print("remove FIRST two and check the size again:  ");
        ins.removeFirst();
        ins.removeFirst();
        System.out.println(ins.size());
        // print the entire array
        it = ins.iterator();
        while (it.hasNext()) {
            int val = it.next();
            System.out.print(val);
            System.out.print(" ");
        }
        System.out.println("");

        System.out.print("remove LAST two and check the size again:  ");
        ins.removeLast();
        ins.removeLast();
        System.out.println(ins.size());
        
        // print the entire array
        it = ins.iterator();
        while (it.hasNext()) {
            int val = it.next();
            System.out.print(val);
            System.out.print(" ");
        }
        System.out.println("");

        System.out.println("now we continue to remove");
        System.out.println(ins.removeLast());
        if (ins.isEmpty()) {
            System.out.println("empty now");
        }
        System.out.println(ins.removeLast());
        if (ins.isEmpty()) {
            System.out.println("empty now");
        }
    }

}