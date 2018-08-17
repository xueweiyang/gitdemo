package com.fcl

import javassist.ClassPool
import javassist.CtClass
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
                String filePath = file.absolutePath
//                println("filepath="+filePath)
                if (file.getName().equals("MainActivity.class")) {
                    CtClass ctClass = pool.getCtClass("com.example.fcl.plugindemo2.MainActivity")
                    println("ctClass="+ctClass)

                    //解冻
                    if (ctClass.isFrozen()) {
                        ctClass.defrost()
                    }

                    CtMethod ctMethod = ctClass.getDeclaredMethod("onCreate")

                    String insetBeforeStr = """
android.widget.Toast.makeText(this,"被插入的代码",android.widget.Toast.LENGTH_SHORT).show();"""

                    ctMethod.insertAfter(insetBeforeStr)
                    ctClass.writeFile(path)
                    ctClass.detach()
                }
            }
        }
    }


}