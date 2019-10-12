package stack;

public class CircleArrayQueue<T> {

    private int num;
    private int head = 0, tail = 0;
    private Object[] items;

    public CircleArrayQueue(int num) {
        this.num = num;
        items = new Object[num];
    }

    public boolean enqueue(T data) {
        if ((tail + 1) % num == head) {
            return false;
        }
        tail = (tail + 1) % num;
        items[tail] = data;
        return true;
    }

    public T dequeue() {
        if (head == tail) {
            return null;
        }
        head = (head + 1) % num;
        return (T) items[head];
    }
}
