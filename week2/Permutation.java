import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        RandomizedQueue<String> list = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            list.enqueue(input);
        }
        for (int i = 0; i < num; ++i) {
            System.out.println(list.dequeue());
        }
    }
}