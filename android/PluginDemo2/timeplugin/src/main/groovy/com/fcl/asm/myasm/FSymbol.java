package com.fcl.asm.myasm;

import org.gradle.internal.impldep.org.eclipse.jgit.util.FS;

/**
 * Created by galio.fang on 19-3-19
 */
public class FSymbol {
    static final int CONSTANT_UTF8_TAG = 1;
    static final int CONSTANT_INTEGER_TAG = 3;
    static final int CONSTANT_FLOAT_TAG = 4;
    static final int CONSTANT_LONG_TAG = 5;
    static final int CONSTANT_DOUBLE_TAG = 6;
    static final int CONSTANT_CLASS_TAG = 7;
    static final int CONSTANT_STRING_TAG = 8;
    static final int CONSTANT_FIELDREF_TAG = 9;
    static final int CONSTANT_METHODREF_TAG = 10;
    static final int CONSTANT_INTERFACE_METHODREF_TAG = 11;
    /**
     * 字段或方法的部分符号引用
     */
    static final int CONSTANT_NAME_AND_TYPE_TAG = 12;
    static final int CONSTANT_METHOD_HANDLE_TAG = 15;
    static final int CONSTANT_METHOD_TYPE_TAG = 16;
    static final int CONSTANT_DYNAMIC_TAG = 17;
    static final int CONSTANT_INVOKE_DYNAMIC_TAG = 18;
    static final int CONSTANT_MODULE_TAG = 19;
    static final int CONSTANT_PACKAGE_TAG = 20;

    int index;
    int tag;
    String owner;
    String name;
    String value;
    long data;

    FSymbol(
        int index,
        int tag,
        String owner,
        String name,
        String value,
        long data
    ) {
        this.index = index;
        this.tag = tag;
        this.owner = owner;
        this.name = name;
        this.value = value;
        this.data = data;
    }
}
