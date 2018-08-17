package com.fcl

import com.android.build.api.transform.Context
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider

public class TimeTransform extends Transform {
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
        inputs.each { TransformInput input->
            input.jarInputs.each {JarInput jarInput->
                println("---jarinput file---" + jarInput.file.absolutePath)
            }

        }
    }
}