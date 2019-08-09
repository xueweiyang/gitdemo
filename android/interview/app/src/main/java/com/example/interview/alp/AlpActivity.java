package com.example.interview.alp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.interview.R;

public class AlpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alp2);
        sort(new int[]{13, 57, 21,43,7,49,102,43});
    }

    void sort(int[] data) {
        sortA(data, 0, data.length-1);
        for (int datum : data) {
            Log.e("aplpp", datum+"\n");
        }
    }

    void sortA(int[] data, int start, int end) {
        int middle;
        if (start < end) {
            middle = cmm(data, start, end);
            sortA(data,start,middle-1);
            sortA(data,middle+1,end);
        }
    }

    int cmm(int[] data,int start,int end) {
        int key = data[start];
        while (start<end) {
            while (start<end && data[end]>=key) {
                end--;
            }
            swap(data,start,end);
            while (start<end && data[start]<=key){
                start++;
            }
            swap(data,start,end);
        }
        return start;
    }

    void swap(int[] data,int start,int end) {
        int a=data[start];
        data[start]=data[end];
        data[end]=a;
    }
}
