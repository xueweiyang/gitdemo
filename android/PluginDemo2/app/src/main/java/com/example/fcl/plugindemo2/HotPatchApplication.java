package com.example.fcl.plugindemo2;

import android.app.Application;
import android.os.Environment;
import android.util.Log;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * Created by galio.fang on 18-8-23
 */
public class HotPatchApplication extends Application {
String TAG="HotPatchApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        initHack();
        initPatch();
    }

    private void initPatch() {
        String dexPath = Environment.getExternalStorageDirectory().getAbsolutePath().concat("/patch_dex.jar");
        File file = new File(dexPath);
        if (file.exists()) {
            inject(dexPath);
        } else {
            Log.e(TAG, dexPath+"不存在");
        }
    }

    private void initHack() {
        try {
            File hackDir = getDir("hackDir", 0);
            File hackJar = new File(hackDir, "hack.jar");
            FileOutputStream out = new FileOutputStream(hackJar);
            InputStream in = getAssets().open("hack.jar");

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf,0,len);
            }
            in.close();
            out.close();
            Log.e(TAG, "fuck path"+hackJar.getAbsolutePath()+"       "+hackJar.getName());
            inject(hackJar.getAbsolutePath());
        } catch (Exception e) {
            Log.e(TAG, "what error:"+e.getMessage());
        }
    }

    void inject(String path) {

        try {
            Class<?> cl = Class.forName("dalvik.system.BaseDexClassLoader");
            Object pathList = getField(cl, "pathList", getClassLoader());
            Object baseElements = getField(pathList.getClass(), "dexElements", pathList);

            String dexopt = getDir("dexopt", 0).getAbsolutePath();
            DexClassLoader dexClassLoader = new DexClassLoader(path,dexopt,dexopt,getClassLoader());
            Object obj = getField(cl, "pathList", dexClassLoader);
            Object dexElements = getField(obj.getClass(), "dexElements", obj);

            Object combineElements = combineArray(dexElements, baseElements);

            setField(pathList.getClass(), "dexElements", pathList, combineElements);

            Object object = getField(pathList.getClass(), "dexElements", pathList);
            int length = Array.getLength(object);
            Log.e(TAG, "length="+length);

        } catch (Exception e) {
            Log.e(TAG, "what error:"+e.getMessage());
        }
    }

    private Object combineArray(Object dexElements, Object baseElements) {
        int firstLength = Array.getLength(dexElements);
        int secondLength = Array.getLength(baseElements);
        int length = firstLength + secondLength;

        Class<?> componentType = dexElements.getClass().getComponentType();
        Object newArr = Array.newInstance(componentType, length);

        for (int i=0;i<length;i++) {
            if (i<firstLength) {
                Array.set(newArr, i, Array.get(dexElements, i));
            } else {
                Array.set(newArr,i,Array.get(baseElements,i-firstLength));
            }
        }

        return newArr;
    }

    Object getField(Class<?> cl, String fieldName, Object object) throws NoSuchFieldException,IllegalAccessException{
        Field field = cl.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    void setField(Class<?> cl, String fieldName, Object object, Object value) throws NoSuchFieldException,
        IllegalAccessException {
        Field field = cl.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }
}
