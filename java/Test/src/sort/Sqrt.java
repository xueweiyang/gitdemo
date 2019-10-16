package sort;

import stack.Log;

public class Sqrt {
    public static void main(String[] args) {
        Log.log(sqrt(333)+"");
    }

    static double sqrt(double data) {
        int n = (int) data;
        return find(n);
    }

    static double find(double data) {
        double start = 0;
        double end = data;
        double mid = end / 2;
        while (start <= end) {
            if (Math.pow(mid, 2) <= data && Math.pow(mid + 1, 2) > data) {
                return mid;
            } else if (Math.pow(mid,2) > data) {
                end = mid-1;
            } else if (Math.pow(mid+1,2)<data) {
                start=mid+1;
            }
            mid=(start+end)/2;
        }
        return -1;
    }
}
