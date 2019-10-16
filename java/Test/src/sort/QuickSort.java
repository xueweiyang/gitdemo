package sort;

import stack.Log;

public class QuickSort {

    public static void main(String[] args) {
        int[] data = {7, 6, 3, 8, 10, 5, 2, 9};
        quickSort(data, 0, data.length - 1);
        Log.printArray(data);
    }

    private static void quickSort(int[] data, int start, int end) {
        if (start < end) {
            int mid = sort(data, start, end);
            quickSort(data, start, mid - 1);
            quickSort(data, mid + 1, end);
        }
    }

    private static int sort(int[] data, int start, int end) {
        int tmp = data[start];
        int tmpPos = start;
        boolean inc = true;
        while (start < end) {
            if (inc) {
                if (data[end] < tmp) {
                    data[tmpPos] = data[end];
                    tmpPos = end;
                    inc = false;
                } else {
                    end--;
                }
            } else {
                if (data[start] > tmp) {
                    data[tmpPos] = data[start];
                    tmpPos = start;
                    inc = true;
                } else {
                    start++;
                }
            }

        }
        data[start] = tmp;
        return start;
    }

}
