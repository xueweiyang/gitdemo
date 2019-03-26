package com.fcl.asm.myasm;

/**
 * Created by changle on 2019/3/20.
 */

public class FMethodWriter extends FMethodVisitor {
    static final int COMPUTE_NOTHING = 0;

    /**
     * Indicates that the maximum stack size and the maximum number of local variables must be
     * computed, from scratch.
     */
    static final int COMPUTE_MAX_STACK_AND_LOCAL = 1;

    /**
     * Indicates that the maximum stack size and the maximum number of local variables must be
     * computed, from the existing stack map frames. This can be done more efficiently than with the
     * control flow graph algorithm used for {@link #COMPUTE_MAX_STACK_AND_LOCAL}, by using a linear
     * scan of the bytecode instructions.
     */
    static final int COMPUTE_MAX_STACK_AND_LOCAL_FROM_FRAMES = 2;

    /**
     * Indicates that the stack map frames of type F_INSERT must be computed. The other frames are not
     * computed. They should all be of type F_NEW and should be sufficient to compute the content of
     * the F_INSERT frames, together with the bytecode instructions between a F_NEW and a F_INSERT
     * frame - and without any knowledge of the type hierarchy (by definition of F_INSERT).
     */
    static final int COMPUTE_INSERTED_FRAMES = 3;

    /**
     * Indicates that all the stack map frames must be computed. In this case the maximum stack size
     * and the maximum number of local variables is also computed.
     */
    static final int COMPUTE_ALL_FRAMES = 4;

    FSymbolTable symbolTable;
    int accessFlags;
    int nameIndex;
    String name;
    int descIndex;
    String desc;
    int signatureIndex;
    int numberOfExceptions;
    int[] exceptionIndexTable;
    int compute;
    int maxLocals;
    int currentLocals;
    FLabel firstBasicBlock;
    boolean hasAsmInstructions;

    FMethodWriter(
        FSymbolTable symbolTable,
        int access,
        String name,
        String desc,
        String signature,
        String[] exceptions,
        int compute
    ) {
        super(FOpcodes.ASM7);
        this.symbolTable = symbolTable;
        this.accessFlags = "<init>".equals(name) ? access | FConstants.ACC_CONSTRUCTOR : access;
        this.nameIndex = symbolTable.addConstantUtf8(name);
        this.name = name;
        this.descIndex = symbolTable.addConstantUtf8(desc);
        this.desc = desc;
        this.signatureIndex = signature == null ? 0 : symbolTable.addConstantUtf8(signature);
        if (exceptions != null && exceptions.length > 0) {
            numberOfExceptions = exceptions.length;
            this.exceptionIndexTable = new int[numberOfExceptions];
            for (int i = 0; i < numberOfExceptions; i++) {
                this.exceptionIndexTable[i] = symbolTable.addConstantClass(exceptions[i]).index;//todo
            }
        } else {
            numberOfExceptions = 0;
            this.exceptionIndexTable = null;
        }
        this.compute = compute;
        if (compute != COMPUTE_NOTHING) {
            int argumentsSize = FType.getArgumentsAndReturnSizes(desc) >> 2;
            if ((access & FOpcodes.ACC_STATIC) != 0) {
                --argumentsSize;
            }
            maxLocals=argumentsSize;
            currentLocals = argumentsSize;
            firstBasicBlock=new FLabel();
            visitLabel(firstBasicBlock);
        }
    }

    private void visitLabel(FLabel label) {
        //hasAsmInstructions |= label.resolve()
    }
}
