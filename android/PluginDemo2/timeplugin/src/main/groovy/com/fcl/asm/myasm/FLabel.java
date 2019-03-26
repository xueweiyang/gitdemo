package com.fcl.asm.myasm;

/**
 * Created by galio.fang on 19-3-22
 */
public class FLabel {

    int FLAG_DEBUG_ONLY = 1;
    int FLAG_JUMP_TARGET = 2;
    int FLAG_RESOLVED = 4;
    int FLAG_REACHABLE = 8;
    int FLAG_SUBROUTINE_CALLER = 16;
    int FLAG_SUBROUTINE_START = 32;
    int FLAG_SUBROUTINE_END = 64;

    short flags;
    short lineNumber;
    int[] otherLineNumbers;
    int bytecodeOffset;

    boolean resolve(byte[] code, int bytecodeOffset) {
        this.flags |= FLAG_RESOLVED;
        this.bytecodeOffset=bytecodeOffset;
return false;
    }
}
