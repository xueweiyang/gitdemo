package stack;

public class ArrayMerge {

    public static void main(String[] args) {
        int[] data = {4, 32, 23, 19, 20, 2, 110, 32, 10, 4, 443};
        mergeSort(data);
        int[] array1 = {3, 7, 9, 11, 39};
        int[] array2 = {2, 8, 9, 15, 29, 33};
        printArray(mergeArray(array1, array2));
    }

    private static int[] mergeSort(int[] data) {
        mergeSort(data, 0, data.length - 1);
        return null;
    }

    private static void mergeSort(int[] data, int start, int end) {
        if (start>=end) {
            return;
        }
        int mid = (end + start) / 2;
        mergeSort(data, start,mid);
        mergeSort(data,mid+1,end);

    }

    private static int[] mergeArray(int[] array1, int[] array2) {
        if (array1 == null || array1.length == 0) {
            return array2;
        }
        if (array2 == null || array2.length == 0) {
            return array1;
        }
        int length1 = array1.length;
        int length2 = array2.length;
        int[] result = new int[length1 + length2];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < length1 || j < length2) {
            if (i == length1) {
                result[k] = array2[j];
                j++;
            } else if (j == length2) {
                result[k] = array1[i];
                i++;
            } else if (array1[i] <= array2[j]) {
                result[k] = array1[i];
                i++;
            } else {
                result[k] = array2[j];
                j++;
            }
            k++;
        }
        return result;
    }

    static void printArray(int[] array) {
        for (int i : array) {
            System.out.println(i);
        }
    }

}
