package com.fcl

import jdk.internal.org.objectweb.asm.ClassReader
import jdk.internal.org.objectweb.asm.ClassWriter

public class AsmInject {

    public static void injectDir(String path) {
        File dir = new File(path)
        if (dir.isDirectory()) {
            dir.eachFileRecurse {File file->
                String filePath = file.absolutePath
                if (filePath.endsWith(".class")
                    && !filePath.contains('R$')
                    && !filePath.contains('R.class')
                    && !filePath.contains("BuildConfig.class")
                    && !filePath.contains("HotPatchApplication.class")) {
                    int index = filePath.indexOf("com/example/fcl/plugindemo2")
                    if (index != -1) {
                        int end = filePath.length() - 6
                        String className = filePath.substring(index,end).replace('/','.')
                        println("className:"+className)
                        injectClass(className,path)
                    }
                }
            }
        }
    }

    public static void injectClass(String className,String path) {
        File file = new File(path)
        File outFile = new File(file.getParent(), file.getName()+".opt")
        FileInputStream inputStream=null
        FileOutputStream outputStream=null
        inputStream = new FileInputStream(file)
        outputStream=new FileOutputStream(outFile)
        byte[] bytes =
    }

    private static byte[] referHack(InputStream inputStream) {
        try {
            ClassReader classReader = new ClassReader(inputStream)
            ClassWriter classWriter = new ClassWriter(classReader, )
        }
    }
}
