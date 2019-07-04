package com.example.andresguard;

public class AndRes {

    static String TAG = "AndRes";

    public static void doTask() {
        Log.i(TAG, "dotask");
        ProguardCache.INSTANCE.init();
        ApkZip.INSTANCE.unzip();
    }

}
