package com.example.systrace.transform

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform

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
}