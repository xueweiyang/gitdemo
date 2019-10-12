package stack;


public class ArrayStack<T> {

    private int num;//容量
    private int count;
    private Object[] items;

    public ArrayStack(int num) {
        this.num = num;
        items = new Object[num];
    }

    public void push(T value) {
        if (count >= num) {
            return;
        }
        items[count++] = value;
    }

    public T pop() {
        if (count == 0) {
            return null;
        }
        return (T) items[--count];
    }

    public T top() {
        if (count == 0) {
            return null;
        }
        return (T) items[count-1];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void printData() {
        Log.log("===================");
        while (!isEmpty()) {
            Log.log(pop()+"");
        }
        Log.log("===================");
    }
}
