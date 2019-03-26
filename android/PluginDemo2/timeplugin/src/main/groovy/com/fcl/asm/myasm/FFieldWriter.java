package com.fcl.asm.myasm;

/**
 * Created by galio.fang on 19-3-26
 */
public class FFieldWriter extends FFieldVisitor {

    FSymbolTable symbolTable;
    int accessFlag;
    int nameIndex;
    int descIndex;
    int signatureIndex;
    int constantValueIndex;
    FAttribute firstAttribute;

    public FFieldWriter(FSymbolTable symbolTable, int access, String name, String desc, String signature,
        Object constantValue) {
        super(FOpcodes.ASM7);
        this.symbolTable=symbolTable;
        this.accessFlag=access;
        this.nameIndex=symbolTable.addConstantUtf8(name);
        this.descIndex=symbolTable.addConstantUtf8(desc);
        if (signature!=null){
            this.signatureIndex=symbolTable.addConstantUtf8(signature);
        }
        if (constantValue!=null){
            this.constantValueIndex=symbolTable.addConstant(constantValue).index;
        }
    }

    @Override
    void visitAttribute(FAttribute attribute) {
        attribute.nextAttribute=firstAttribute;
        firstAttribute=attribute;
    }

    @Override
    void visitEnd() {

    }
}
