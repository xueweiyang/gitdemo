package com.fcl.asm.myasm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.Opcodes;
import org.stringtemplate.v4.ST;

/**
 * Created by galio.fang on 19-3-19
 */
public class FClassReader {

    int SKIP_CODE=1;
    int SKIP_DEBUG=2;
    int SKIP_FRAMES=4;
    int EXPAND_FRAMES = 8;

    private static final int INPUT_STREAM_DATA_CHUNK_SIZE = 4096;

    byte[] classFileBuffer;
    int[] cpInfoOffsets;
    String[] constantUtf8Values;
    int maxStringLength;
    /**
     * access_flags
     */
    int header;
    FConstantDynamic[] constantDynamicValues;
    int[] bootstrapMethodOffsets;

    public FClassReader(byte[] classFile) {
        this(classFile, 0, classFile.length);
    }

    public FClassReader(byte[] classFileBuffer, int classFileOffset, int classFileLength) {
        this(classFileBuffer, classFileOffset, true);
    }

    FClassReader(byte[] classFileBuffer, int classFileOffset, boolean checkClassVersion) {
        this.classFileBuffer = classFileBuffer;
        if (checkClassVersion && readShort(classFileOffset + 6) > Opcodes.V1_3) {
            throw new IllegalArgumentException("unsupported class file major version" + readShort(classFileOffset + 6));
        }
        int constantPoolCount = readUnsignedShort(classFileOffset + 8);
        cpInfoOffsets = new int[constantPoolCount];
        constantUtf8Values = new String[constantPoolCount];

        int currentCpInfoIndex = 1;
        int currentCpInfoOffset = classFileOffset + 10;
        int currentMaxStringLength = 0;
        boolean hasBootstrapMethods = false;
        boolean hasConstantDynamic = false;
        while (currentCpInfoIndex < constantPoolCount) {
            cpInfoOffsets[currentCpInfoIndex++] = currentCpInfoOffset + 1;//第一位描述常量类型
            int cpInfoSize;
            switch (classFileBuffer[currentCpInfoOffset]) {
                case FSymbol.CONSTANT_FIELDREF_TAG:
                case FSymbol.CONSTANT_METHODREF_TAG:
                case FSymbol.CONSTANT_INTERFACE_METHODREF_TAG:
                case FSymbol.CONSTANT_INTEGER_TAG:
                case FSymbol.CONSTANT_FLOAT_TAG:
                case FSymbol.CONSTANT_NAME_AND_TYPE_TAG:
                    cpInfoSize = 5;
                    break;
                case FSymbol.CONSTANT_DYNAMIC_TAG:
                    cpInfoSize = 5;
                    hasBootstrapMethods = true;//todo
                    hasConstantDynamic = true;
                    break;
                case FSymbol.CONSTANT_INVOKE_DYNAMIC_TAG:
                    cpInfoSize = 5;
                    hasBootstrapMethods = true;
                    break;
                case FSymbol.CONSTANT_LONG_TAG:
                case FSymbol.CONSTANT_DOUBLE_TAG:
                    cpInfoSize = 9;
                    currentCpInfoIndex++; //todo
                    break;
                case FSymbol.CONSTANT_UTF8_TAG:
                    cpInfoSize = 3 + readUnsignedShort(currentCpInfoOffset + 1);
                    if (cpInfoSize > currentMaxStringLength) {
                        currentMaxStringLength = cpInfoSize;
                    }
                    break;
                case FSymbol.CONSTANT_METHOD_HANDLE_TAG:
                    cpInfoSize = 4;
                    break;
                case FSymbol.CONSTANT_CLASS_TAG:
                case FSymbol.CONSTANT_STRING_TAG:
                case FSymbol.CONSTANT_METHOD_TYPE_TAG:
                case FSymbol.CONSTANT_MODULE_TAG:
                    cpInfoSize = 3;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            currentCpInfoOffset += cpInfoSize;
        }
        maxStringLength = currentMaxStringLength;
        header = currentCpInfoOffset;
        constantDynamicValues = hasConstantDynamic ? new FConstantDynamic[constantPoolCount] : null;
        bootstrapMethodOffsets = hasBootstrapMethods ? readBootstrapMethodsAttribute(currentMaxStringLength) : null;
    }

    void accept(FClassVisitor classVisitor, int parsingOptions) {
        accept(classVisitor, new FAttribute[0], parsingOptions);
    }

    void accept(FClassVisitor classVisitor, FAttribute[] attributePrototypes,
        int parsingOptions) {
        FContext context = new FContext();
        context.attributePrototypes = attributePrototypes;
        context.parsingOptions = parsingOptions;
        context.charBuffer = new char[maxStringLength];

        //read the access_flags,this_class,super_class,interface_count and interfaces fields
        char[] charBuffer = context.charBuffer;
        int currentOffset = header;
        int accessFlags = readUnsignedShort(currentOffset);
        String thisClass = readClass(currentOffset + 2, charBuffer);
        String superClass = readClass(currentOffset + 4, charBuffer);
        String[] interfaces = new String[readUnsignedShort(currentOffset + 6)];
        currentOffset += 8;
        for (int i = 0; i < interfaces.length; i++) {
            interfaces[i] = readClass(currentOffset, charBuffer);
            currentOffset += 2;
        }

        int innerClassOffset = 0;
        String sourceFile = null;
        int innerClassesOffset = 0;
        String signature = null;
        String sourceDebugExtension=null;

        FAttribute attributes = null;
        int currentAttributeOffset = getFirstAttributeOffset();
        for (int i = readUnsignedShort(currentAttributeOffset - 2); i > 0; --i) {
            String attributeName = readUTF8(currentAttributeOffset, charBuffer);
            int attributeLength = readInt(currentAttributeOffset + 2);
            currentAttributeOffset += 6;
            if (FConstants.SOURCE_FILE.equals(attributeName)) {
                sourceFile = readUTF8(currentAttributeOffset, charBuffer);
            } else if (FConstants.INNER_CLASSES.equals(attributeName)) {
                innerClassOffset = currentAttributeOffset; //这个位置记录了内部类的个数
            } else if (FConstants.ENCLOSING_METHOD.equals(attributeName)) {

            } else if (FConstants.SOURCE_DEBUG_EXTENSION.equals(attributeName)) {
sourceDebugExtension=readUtf(currentAttributeOffset,attributeLength,new char[attributeLength]);
            }
            currentAttributeOffset += attributeLength;
        }

        classVisitor.visit(readInt(cpInfoOffsets[1] - 7), accessFlags, thisClass, signature, superClass, interfaces);

        if ((parsingOptions & SKIP_DEBUG) == 0
            && (sourceFile != null || sourceDebugExtension != null)) {
classVisitor.visitSource(sourceFile,sourceDebugExtension);
        }

        int fieldsCount = readUnsignedShort(currentOffset);
        currentOffset+=2;
        while (fieldsCount-->0){
            currentOffset = readField(classVisitor, context, currentOffset);
        }
    }

    private int readField(FClassVisitor classVisitor, FContext context, int fieldInfoOffset) {
        char[] charBuffer = context.charBuffer;
        int currentOffset = fieldInfoOffset;
        int accessFlags = readUnsignedShort(currentOffset);
        String name = readUTF8(currentOffset+2,charBuffer);
        String desc = readUTF8(currentOffset+4,charBuffer);
        currentOffset+=6;

        Object constantValue=null;

        int attributesCount = readUnsignedShort(currentOffset);
        currentOffset+=2;
        while (attributesCount-->0) {
            String attributeName = readUTF8(currentOffset,charBuffer);
            int attributeLength = readInt(currentOffset+2);
            currentOffset +=6;
            if (FConstants.CONSTANT_VALUE.equals(attributeName)) {
                int constantValueIndex = readUnsignedShort(currentOffset);
constantValue = constantValueIndex == 0?null:
            }
        }

    }

    int readMethod(
        FClassVisitor classVisitor, FContext context, int methodInfoOffset) {

    }

    private int getFirstAttributeOffset() {
        // Skip the access_flags, this_class, super_class, and interfaces_count fields (using 2 bytes
        // each), as well as the interfaces array field (2 bytes per interface).
        int currentOffset = header + 8 + readUnsignedShort(header + 6) * 2;

        int fieldsCount = readUnsignedShort(currentOffset);
        currentOffset += 2;
        while (fieldsCount-- > 0) {
            int attributesCount = readUnsignedShort(currentOffset + 6);
            currentOffset += 8;
            while (attributesCount-- > 0) {
                currentOffset += 6 + readInt(currentOffset + 2);
            }
        }

        int methodsCount = readUnsignedShort(currentOffset);
        currentOffset += 2;
        while (methodsCount-- > 0) {
            int attributesCount = readUnsignedShort(currentOffset + 6);
            currentOffset += 8;
            while (attributesCount-- > 0) {
                currentOffset += 6 + readInt(currentOffset + 2);
            }
        }
        //skip attributes_count
        return currentOffset + 2;
    }

    private int[] readBootstrapMethodsAttribute(final int maxStringLength) {
        return null;
    }

    int readByte(int offset) {
        return classFileBuffer[offset] & 0xff;
    }

    short readShort(int offset) {
        byte[] classBuffer = classFileBuffer;
        return (short) (((classBuffer[offset] & 0xff) << 8) | (classBuffer[offset + 1] & 0xff));
    }

    int readUnsignedShort(int offset) {
        byte[] classBuffer = classFileBuffer;
        return ((classBuffer[offset] & 0xff) << 8) | (classBuffer[offset + 1] & 0xff);
    }

    int readInt(int offset) {
        byte[] classBuffer = classFileBuffer;
        return ((classBuffer[offset] & 0xff) << 24)
            | ((classBuffer[offset + 1] & 0xff) << 16)
            | ((classBuffer[offset + 2] & 0xff) << 8)
            | (classBuffer[offset + 3] & 0xff);
    }

    long readLong(int offset) {
        long l1 = readInt(offset);
        long l0 = readInt(offset + 4) & 0xffffffffL;
        return (l1 << 32) | l0;
    }

    String readClass(int offset, char[] charBuffer) {
        return readStringish(offset, charBuffer);
    }

    String readStringish(int offset, char[] charBuffer) {
        //this_class 和super_class存储的都是在常量池中的索引
        //而且Class_info类型的数据是全限定名在常量池的索引头
        return readUTF8(cpInfoOffsets[readUnsignedShort(offset)], charBuffer);
    }

    String readUTF8(int offset, char[] charBuffer) {
        int constantPoolEntryIndex = readUnsignedShort(offset);
        if (offset == 0 || constantPoolEntryIndex == 0) {
            return null;
        }
        return readUtf(constantPoolEntryIndex, charBuffer);
    }

    String readUtf(int constantPoolEntryIndex, char[] charBuffer) {
        String value = constantUtf8Values[constantPoolEntryIndex];
        if (value != null) {
            return value;
        }
        int cpInfoOffset = cpInfoOffsets[constantPoolEntryIndex];
        return constantUtf8Values[constantPoolEntryIndex] =
            readUtf(cpInfoOffset + 2, readUnsignedShort(cpInfoOffset), charBuffer);
    }

    String readUtf(int utfOffset, int utfLength, char[] charBuffer) {
        int currentOffset = utfOffset;
        int endOffset = currentOffset + utfLength;
        int strLength = 0;
        byte[] classBuffer = classFileBuffer;
        while (currentOffset < endOffset) {
            int currentByte = classBuffer[currentOffset++];
            //todo
            if ((currentByte & 0x80) == 0) {
                charBuffer[strLength++] = (char) (currentByte & 0x7f);
            } else if ((currentByte & 0xe0) == 0xc0) {
                charBuffer[strLength++] =
                    (char) (((currentByte & 0x1f) << 6) + (classBuffer[currentOffset++] & 0x3f));
            } else {
                charBuffer[strLength++] =
                    (char)
                        (((currentByte & 0xF) << 12)
                            + ((classBuffer[currentOffset++] & 0x3F) << 6)
                            + (classBuffer[currentOffset++] & 0x3F));
            }
        }
        return new String(charBuffer, 0, strLength);
    }

    public int getItem(int constantPoolEntryIndex) {
        return cpInfoOffsets[constantPoolEntryIndex];
    }

    public int getItemCount() {
        return cpInfoOffsets.length;
    }
}
