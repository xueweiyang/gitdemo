package com.fcl.asm.myasm;

/**
 * Created by changle on 2019/3/20.
 */

public class FType {

    /** The sort of the {@code void} FType. See {@link #getSort}. */
    public static final int VOID = 0;

    /** The sort of the {@code boolean} FType. See {@link #getSort}. */
    public static final int BOOLEAN = 1;

    /** The sort of the {@code char} FType. See {@link #getSort}. */
    public static final int CHAR = 2;

    /** The sort of the {@code byte} FType. See {@link #getSort}. */
    public static final int BYTE = 3;

    /** The sort of the {@code short} FType. See {@link #getSort}. */
    public static final int SHORT = 4;

    /** The sort of the {@code int} FType. See {@link #getSort}. */
    public static final int INT = 5;

    /** The sort of the {@code float} FType. See {@link #getSort}. */
    public static final int FLOAT = 6;

    /** The sort of the {@code long} FType. See {@link #getSort}. */
    public static final int LONG = 7;

    /** The sort of the {@code double} FType. See {@link #getSort}. */
    public static final int DOUBLE = 8;

    /** The sort of array reference FTypes. See {@link #getSort}. */
    public static final int ARRAY = 9;

    /** The sort of object reference FTypes. See {@link #getSort}. */
    public static final int OBJECT = 10;

    /** The sort of method FTypes. See {@link #getSort}. */
    public static final int METHOD = 11;

    /** The (private) sort of object reference FTypes represented with an internal name. */
    private static final int INTERNAL = 12;

    /** The descriptors of the primitive FTypes. */
    private static final String PRIMITIVE_DESCRIPTORS = "VZCBSIFJD";

    /** The {@code void} FType. */
    public static final FType VOID_TYPE = new FType(VOID, PRIMITIVE_DESCRIPTORS, VOID, VOID + 1);

    /** The {@code boolean} FType. */
    public static final FType BOOLEAN_TYPE =
        new FType(BOOLEAN, PRIMITIVE_DESCRIPTORS, BOOLEAN, BOOLEAN + 1);

    /** The {@code char} FType. */
    public static final FType CHAR_TYPE = new FType(CHAR, PRIMITIVE_DESCRIPTORS, CHAR, CHAR + 1);

    /** The {@code byte} FType. */
    public static final FType BYTE_TYPE = new FType(BYTE, PRIMITIVE_DESCRIPTORS, BYTE, BYTE + 1);

    /** The {@code short} FType. */
    public static final FType SHORT_TYPE = new FType(SHORT, PRIMITIVE_DESCRIPTORS, SHORT, SHORT + 1);

    /** The {@code int} FType. */
    public static final FType INT_TYPE = new FType(INT, PRIMITIVE_DESCRIPTORS, INT, INT + 1);

    /** The {@code float} FType. */
    public static final FType FLOAT_TYPE = new FType(FLOAT, PRIMITIVE_DESCRIPTORS, FLOAT, FLOAT + 1);

    /** The {@code long} FType. */
    public static final FType LONG_TYPE = new FType(LONG, PRIMITIVE_DESCRIPTORS, LONG, LONG + 1);

    /** The {@code double} FType. */
    public static final FType DOUBLE_TYPE =
        new FType(DOUBLE, PRIMITIVE_DESCRIPTORS, DOUBLE, DOUBLE + 1);

    int sort;
    String valueBuffer;
    int valueBegin;
    int valueEnd;

    FType(int sort,String valueBuffer,int valueBegin,int valueEnd) {
        this.sort=sort;
        this.valueBuffer=valueBuffer;
        this.valueBegin=valueBegin;
        this.valueEnd=valueEnd;
    }

    //todo
    static int getArgumentsAndReturnSizes(String methodDesc) {
        int argumentsSize=1;
        int currentOffset=1;
        int currentChar=methodDesc.charAt(currentOffset);
        while (currentChar!=')') {
            if (currentChar == 'J' || currentChar == 'D') {
                currentOffset++;
                argumentsSize+=2;
            } else {
                while (methodDesc.charAt(currentOffset) == '[') {
                    currentOffset++;
                }
                if (methodDesc.charAt(currentOffset++) == 'L') {
                    currentOffset = methodDesc.indexOf(';', currentOffset) +1;
                }
                argumentsSize+=1;
            }
            currentChar = methodDesc.charAt(currentOffset);
        }
        currentChar = methodDesc.charAt(currentOffset+1);
        if (currentChar == 'V') {
            return argumentsSize<<2;
        } else {
            int returnSize = (currentChar == 'J' || currentChar == 'D') ? 2:1;
            return argumentsSize<<2|returnSize;
        }
    }

    public static FType getObjectType(String internalName) {
        return new FType(internalName.charAt(0) == '[' ? ARRAY : INTERNAL, internalName,0,internalName.length());
    }

    static FType getMethodType(String desc) {
        return new FType(METHOD, desc,0,desc.length());
    }
}
