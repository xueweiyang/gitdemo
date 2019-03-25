package com.fcl.asm.myasm;

/**
 * Created by galio.fang on 19-3-25
 */
public class FHandle {

    int tag;
    String owner;
    String name;
    String desc;
    boolean isInterface;

    FHandle(
        int tag,
        String owner,
        String name,
        String desc,
        boolean isInterface
    ) {
        this.tag = tag;
        this.owner = owner;
        this.name = name;
        this.desc = desc;
        this.isInterface = isInterface;
    }

}
