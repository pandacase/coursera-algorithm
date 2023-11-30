public class HelloGoodbye {
    public static void main(String[] args){
        try {
            System.out.println("Hello " + args[0] + " and " + args[1] + ".");
            System.out.println("Goodbye " + args[1] + " and " + args[0] + ".");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Sorry, we need two args to run.");
        }
    }
}
