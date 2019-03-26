package com.fcl.asm.myasm;

/**
 * Created by galio.fang on 19-3-19
 */
public class FConstantDynamic {

    String name;
    String desc;
    FHandle handle;
    Object[] bootstrapMethodArguments;

    public FConstantDynamic(String name, String desc, FHandle handle, Object[] bootstrapMethodArguments) {
        this.name=name;
        this.desc=desc;
        this.handle=handle;
        this.bootstrapMethodArguments=bootstrapMethodArguments;
    }
}
