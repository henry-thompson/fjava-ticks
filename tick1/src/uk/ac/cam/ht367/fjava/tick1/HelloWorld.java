package uk.ac.cam.ht367.fjava.tick1;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, " + getName(args));
    }

    private static String getName(String[] args) {
        return args.length == 0 ? "world" : args[0];
    }
}