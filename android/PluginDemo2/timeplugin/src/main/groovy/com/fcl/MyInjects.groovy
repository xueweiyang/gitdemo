package com.fcl

import javassist.ClassPool
import javassist.CtClass
import javassist.CtConstructor
import javassist.CtMethod
import org.gradle.api.Project

public class MyInjects{

    private final static ClassPool pool = ClassPool.getDefault()

    public static void inject(String path, Project project) {
        //当前路径加入类池，不然找不到这个类
        pool.appendClassPath(path)
        pool.appendClassPath(project.android.bootClasspath[0].toString())
        pool.importPackage("android.os.Bundle")

        File dir = new File(path)
        if (dir.isDirectory()) {
            dir.eachFileRecurse {File file->
                String filename=file.getName()
                if (filename.endsWith(".class")
&& !filename.contains('R$')
&& !filename.contains('R.class')
                ) {
                    String classname = "com.example.fcl.plugindemo2." + filename.substring(0, filename.lastIndexOf("" +
                        "."))
                    println("classname"+classname)
                    CtClass c = pool.getCtClass(classname)
                    if (c.isFrozen()) {
                        c.defrost()
                    }
                    CtConstructor[] cts = c.getDeclaredConstructors()
                    if (cts==null || cts.length==0) {
                        insertNewConstructor(c)
                    }
//                    else {
//                        cts[0].insertBeforeBody("System.out.println()")
//                    }
                    c.writeFile(path)
                    c.detach()
                }

//                String filePath = file.absolutePath
////                println("filepath="+filePath)
//                if (file.getName().equals("MainActivity.class")) {
//                    CtClass ctClass = pool.getCtClass("com.example.fcl.plugindemo2.MainActivity")
//
//
//                    //解冻
//                    if (ctClass.isFrozen()) {
//                        ctClass.defrost()
//                    }
//
//                    CtMethod ctMethod = ctClass.getDeclaredMethod("onCreate")
//
//                    String insetBeforeStr = """
//android.widget.Toast.makeText(this,"被插入的代码",android.widget.Toast.LENGTH_SHORT).show();"""
//
//                    ctMethod.insertAfter(insetBeforeStr)
//                    ctClass.writeFile(path)
//                    ctClass.detach()
//                }
//                println("ctClassanme="+file.getAbsolutePath()+"  "+file.getName())
//                CtClass ctClass = pool.getCtClass()
            }
        }
    }

    private static void insertNewConstructor(CtClass c) {
        CtConstructor constructor = new CtConstructor(new CtClass[0], c)
        String insetBeforeStr = """
android.widget.Toast.makeText(this,"被插入的代码",android.widget.Toast.LENGTH_SHORT).show();"""
        constructor.insertBeforeBody(insetBeforeStr)
        c.addConstructor(constructor)
    }
}