package com.example.systrace.transform

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Status
import com.android.build.api.transform.Transform
import com.example.systrace.ReflectUtil

import java.lang.reflect.Field

abstract class BaseProxyTransform extends Transform {

    protected Transform originTransform

    public BaseProxyTransform(Transform transform) {
        this.originTransform = transform
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return originTransform.getInputTypes()
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return originTransform.getScopes()
    }

    @Override
    boolean isIncremental() {
        return originTransform.isIncremental()
    }

    void replaceFile(QualifiedContent input, File newFile) {
        Field fileField = ReflectUtil.getDeclaredFieldRecursive(input.class, 'file')
        fileField.set(input, newFile)
    }

    public void replaceChangedFile(DirectoryInput dirInput, Map<File, Status> changedFiles) {
        Field changedFilesField = ReflectUtil.getDeclaredFieldRecursive(dirInput.class, 'changedFiles')
        changedFilesField.set(dirInput, changedFiles)
    }
}