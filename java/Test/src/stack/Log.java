package stack;

public class Log {
    public static void log(String msg) {
        System.out.println(msg);
    }

    public static void log(int val) {
        System.out.println(val + "");
    }

    public static void printArray(int[] array) {
        for (int i : array) {
            System.out.println(i);
        }
    }
}
