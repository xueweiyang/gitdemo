package com.fcl

import com.android.build.api.transform.Context
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

public class TimeTransform extends Transform{

    Project project

    public TimeTransform(Project project) {
        this.project =project
    }

    @Override
    String getName() {
        return "TimePlugin"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return true
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs,
        TransformOutputProvider outputProvider, boolean isIncremental)
        throws IOException, TransformException, InterruptedException {
        println('transform   eee')
        inputs.each { TransformInput input->
            input.directoryInputs.each {DirectoryInput directoryInput->
//                MyInjects.inject(directoryInput.file.absolutePath,project)

                def dest=outputProvider.getContentLocation(directoryInput.name,
                directoryInput.contentTypes,directoryInput.scopes,Format.DIRECTORY)
                println("dest:"+dest)
                FileUtils.copyDirectory(directoryInput.file,dest)
            }
            input.jarInputs.each {JarInput jarInput->
//                println("---jarinput file---" + jarInput.file.absolutePath)
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0,jarName.length()-4)
                }
                def dest = outputProvider.getContentLocation(jarName+md5Name,jarInput.contentTypes,jarInput.scopes,
                    Format.JAR)
                FileUtils.copyFile(jarInput.file,dest)
            }

        }
    }
}
