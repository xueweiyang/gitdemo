package com.example.andresguard;

import com.example.andresguard.decoder.ARSCDecoder;

public class AndRes {

    static String TAG = "AndRes";

    public static void doTask() {
        Log.i(TAG, "dotask");
        ProguardCache.INSTANCE.init();
        ApkZip.INSTANCE.unzip();
        new ARSCDecoder().decode();
        new ARSCDecoder().write();
    }

}
