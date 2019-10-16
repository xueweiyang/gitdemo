package sort;


import stack.Log;

public class BinarySearch {

    public static void main(String[] args) {
        int[] a = {3, 8, 12, 19, 32, 54, 55, 76, 98, 111};
        int value = 33;
        Log.log(String.format("find %d pos:%d", value, binarySearch(a, value)));
    }

    static int binarySearchCircle(int[] data, int value) {

        return 0;
    }

    static int binarySearch(int[] data, int value) {
        if (data == null || data.length == 0) {
            return -1;
        }
        int length = data.length;
        if (length == 1) {
            if (data[0] == value) {
                return 0;
            }
        }
        int start = 0;
        int end = length - 1;
        int mid = length / 2;
        while (start <= end) {
            if (data[mid] == value) {
                return mid;
            } else if (data[mid] < value) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
            mid = (start + end) / 2;
        }
        return -1;
    }
}
