package stack;

import java.util.Arrays;

public class ArrayQueue<T> {

    private int num;
    private int head = 0, tail = 0;
    private Object[] items;

    public ArrayQueue(int num) {
        this.num = num;
        items = new Object[num];
    }

    public void enqueue(T data) {
        if (tail >= num  && head > 0) {
            System.arraycopy(items, head, items, 0, tail - head);
            head = 0;
            tail = tail - head;
        }
        if (tail >= num) {
            return;
        }
        items[tail++] = data;

    }

    public T dequeue() {
        if (head >= tail) {
            return null;
        }
        return (T) items[head++];
    }

    public boolean isEmpty() {
        return head >= tail;
    }
}
