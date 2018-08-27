package com.fcl

import javassist.ClassPool
import javassist.CtClass
import javassist.CtConstructor

public class Inject{

    private static ClassPool pool = ClassPool.getDefault()

    public static void appendClassPath(String libPath) {
        pool.appendClassPath(libPath)
    }

    public static void injectDir(String path) {
        pool.appendClassPath(path)
        File dir = new File(path)
        if (dir.isDirectory()) {
            dir.eachFileRecurse {File file->
                String filePath = file.absolutePath
                if (filePath.endsWith(".class")
                && !filePath.contains('R$')
                && !filePath.contains('R.class')
                && !filePath.contains("BuildConfig.class")
                && !filePath.contains("HotPatchApplication.class")) {
                    int index = filePath.indexOf("com\\example\\fcl\\plugindemo2")
                    if (index != -1) {
                        int end = filePath.length() - 6
                        String className = filePath.substring(index,end).replace('\\','.')
                        injectClass(className,path)
                    }
                }
            }
        }
    }

    public static void injectJar(String path) {
        if (path.endsWith(".jar")) {
            File jarFile = new File(path)
            String jarZipDir = jarFile.getParent() + "/" + jarFile.getName().replace('.jar', '')
            List classNameList = JarZipUtil.unzipJar(path, jarZipDir)
            jarFile.delete()

            pool.appendClassPath(jarZipDir)

            for (String className:classNameList) {
                if (className.endsWith(".class")
                && !className.contains('R$')
                && !className.contains('R.class')
                && !className.contains("BuildConfig.class")) {
                    className = className.substring(0,className.length()-6)
                }
            }
        }
    }

    public static void injectClass(String className,String path) {
        CtClass c = pool.getCtClass(className)
        if (c.isFrozen()) {
            c.defrost()
        }
        CtConstructor[] cts = c.getDeclaredConstructors()
        if (cts==null||cts.length == 0) {
            insertNewConstructor(c)
        } else {
            cts[0].insertBeforeBody("System.out.println(com.example.hack.AntilazyLoad.class")
        }
        c.writeFile(path)
        c.detach()
    }

    private static void insertNewConstructor(CtClass c) {
        CtConstructor constructor = new CtConstructor(new CtClass[0], c)
        constructor.insertBeforeBody("System.out.println(com.example.hack.AntilazyLoad.class")
        c.addConstructor(constructor)
    }

}