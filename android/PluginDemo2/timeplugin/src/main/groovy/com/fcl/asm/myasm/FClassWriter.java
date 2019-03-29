package com.fcl.asm.myasm;

/**
 * Created by galio.fang on 19-3-20
 */
public class FClassWriter extends FClassVisitor {

    int version;
    private int accessFlags;

    /** The this_class field of the JVMS ClassFile structure. */
    private int thisClass;

    /** The super_class field of the JVMS ClassFile structure. */
    private int superClass;

    /** The interface_count field of the JVMS ClassFile structure. */
    private int interfaceCount;

    /** The 'interfaces' array of the JVMS ClassFile structure. */
    private int[] interfaces;

    int signatureIndex;

    FSymbolTable symbolTable;

    int sourceFileIndex;
    FByteVector debugExtension;

    FFieldWriter firstField;
    FFieldWriter lastField;
    int compute;

    FMethodWriter firstMethod;
    FMethodWriter lastMethod;

    public FClassWriter(FClassReader classReader, int flags) {
        super(FOpcodes.ASM7);
        symbolTable = classReader == null ? new FSymbolTable(this) : new FSymbolTable(this, classReader);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.version = version;
        this.accessFlags = access;
        this.thisClass = symbolTable.setMajorVersionAndClassName(version & 0xffff, name);
        if (signature != null) {
            this.signatureIndex = symbolTable.addConstantUtf8(signature);
        }
        this.superClass = superName == null ? 0 : symbolTable.addConstantClass(superName).index;
        if (interfaces != null && interfaces.length > 0) {
            interfaceCount = interfaces.length;
            this.interfaces = new int[interfaceCount];
            for (int i = 0; i < interfaceCount; i++) {
                this.interfaces[i] = symbolTable.addConstantClass(interfaces[i]).index;
            }
        }
    }

    FFieldVisitor visitField(
        int access,
        String name,
        String desc,
        String signature,
        Object value
    ) {
        FFieldWriter fieldWriter =
            new FFieldWriter(symbolTable, access, name, desc, signature, value);
        if (firstField == null) {
            firstField = fieldWriter;
        } else {
            lastField.fv = fieldWriter;
        }
        return lastField = fieldWriter;
    }

    @Override
    FMethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        FMethodWriter methodWriter =
            new FMethodWriter(symbolTable, access, name, desc, signature, exceptions, compute);
        if (firstMethod == null) {
            firstMethod = methodWriter;
        } else {
            lastMethod.mv = methodWriter;
        }
        return lastMethod = methodWriter;
    }

    @Override
    void visitSource(String file, String debug) {
        if (file != null) {
            sourceFileIndex = symbolTable.addConstantUtf8(file);
        }
        if (debug != null) {
            debugExtension = new FByteVector().encodeUtf8(debug, 0, Integer.MAX_VALUE);
        }
    }

    @Override
    void visitEnd() {

    }
}
