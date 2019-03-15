package com.fcl.asm

import com.android.ddmlib.Log
import jdk.internal.org.objectweb.asm.ClassReader
import jdk.internal.org.objectweb.asm.ClassVisitor
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
                        injectClass(className,path)
                    }
                }
            }
        }
    }

    public static void injectClass(String className,String path) {
        String filePath = (path+"/"+className).replace(".","/")+".class"
        println("filepath:$filePath")
        File file = new File(filePath)
        File outFile = new File(file.getParent(), file.getName()+".opt")
        FileInputStream inputStream=null
        FileOutputStream outputStream=null
        inputStream = new FileInputStream(file)
        outputStream=new FileOutputStream(outFile)
        byte[] bytes = referHack(inputStream)
        outputStream.write(bytes)
        inputStream.close()
        outputStream.close()
        if (file.exists()) {
            file.delete()
        }
        outFile.renameTo(file)
    }

    private static byte[] referHack(InputStream inputStream) {
        println("refrerhack")
        try {
            ClassReader classReader = new ClassReader(inputStream)
            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
            ClassVisitor classVisitor = new ChangeVisitor(classWriter)
            classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
            return classWriter.toByteArray()
        } catch (MissingMethodException e) {
            println("asm error:$e")
        }
        return null
    }
}
