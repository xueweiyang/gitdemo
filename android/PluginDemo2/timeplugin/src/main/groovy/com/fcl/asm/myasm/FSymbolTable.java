package com.fcl.asm.myasm;

import org.gradle.internal.impldep.org.eclipse.jgit.util.FS;

/**
 * Created by galio.fang on 19-3-20
 */
public class FSymbolTable {

    int majorVersion;
    String className;
    FEntry[] entries;
    int constantPoolCount;
    int entryCount;
    FByteVector constantPool;
    FClassWriter classWriter;
    FClassReader sourceClassReader;

    public FSymbolTable(FClassWriter classWriter) {
        this.classWriter = classWriter;
        this.sourceClassReader = null;
        this.entries = new FEntry[256];
        this.constantPoolCount = 1;
        this.constantPool = new FByteVector();
    }

    public FSymbolTable(FClassWriter classWriter, FClassReader classReader) {
        this.classWriter = classWriter;
        this.sourceClassReader = classReader;

        byte[] inputBytes = classReader.classFileBuffer;
        int constantPoolOffset = classReader.getItem(1) - 1;
        int constantPoolLength = classReader.header - constantPoolOffset;
        constantPoolCount = classReader.getItemCount();
        constantPool = new FByteVector(constantPoolLength);
        constantPool.putByteArray(inputBytes, constantPoolOffset, constantPoolLength);

        entries = new FEntry[constantPoolCount * 2];
        char[] charBuffer = new char[classReader.maxStringLength];
        boolean hasBootstrapMethods = false;
        int itemIndex = 1;
        while (itemIndex < constantPoolCount) {
            int itemOffset = classReader.getItem(itemIndex);
            int itemTag = inputBytes[itemOffset - 1];
            int nameAndTypeItemOffset;
            switch (itemTag) {
                case FSymbol.CONSTANT_FIELDREF_TAG:
                case FSymbol.CONSTANT_METHODREF_TAG:
                case FSymbol.CONSTANT_INTERFACE_METHODREF_TAG:
                    nameAndTypeItemOffset =
                        classReader.getItem(classReader.readUnsignedShort(itemOffset + 2));
                    addConstantMemberReference(itemIndex, itemTag,
                        classReader.readClass(itemOffset, charBuffer),
                        classReader.readUTF8(nameAndTypeItemOffset, charBuffer),
                        classReader.readUTF8(nameAndTypeItemOffset + 2, charBuffer));
                    break;
                case FSymbol.CONSTANT_INTEGER_TAG:
                case FSymbol.CONSTANT_FLOAT_TAG:
                    addConstantIntegerOrFloat(itemIndex, itemTag, classReader.readInt(itemOffset));
                    break;
                case FSymbol.CONSTANT_NAME_AND_TYPE_TAG:
                    addConstantNameAndType(itemIndex,
                        classReader.readUTF8(itemOffset, charBuffer),
                        classReader.readUTF8(itemOffset + 2, charBuffer));
                    break;
                case FSymbol.CONSTANT_LONG_TAG:
                case FSymbol.CONSTANT_DOUBLE_TAG:
                    addConstantLongOrDouble(itemIndex, itemTag, classReader.readLong(itemOffset));
                    break;
                case FSymbol.CONSTANT_UTF8_TAG:
                    addConstantUtf8(itemIndex, classReader.readUtf(itemIndex, charBuffer));
                    break;
                case FSymbol.CONSTANT_METHOD_HANDLE_TAG:
                    int memberRefItemOffset =
                        classReader.getItem(classReader.readUnsignedShort(itemOffset + 1));
                    nameAndTypeItemOffset =
                        classReader.getItem(classReader.readUnsignedShort(memberRefItemOffset + 2));
                    addConstantMethodHandle(
                        itemIndex,
                        classReader.readByte(itemOffset),
                        classReader.readClass(memberRefItemOffset, charBuffer),
                        classReader.readUTF8(nameAndTypeItemOffset, charBuffer),
                        classReader.readUTF8(nameAndTypeItemOffset + 2, charBuffer)
                    );
                    break;
                case FSymbol.CONSTANT_DYNAMIC_TAG:
                case FSymbol.CONSTANT_INVOKE_DYNAMIC_TAG:
                    hasBootstrapMethods = true;
                    nameAndTypeItemOffset =
                        classReader.getItem(classReader.readUnsignedShort(itemOffset + 2));
                    addConstantDynamicOrInvokeDynamicReference(
                        itemTag,
                        itemIndex,
                        classReader.readUTF8(nameAndTypeItemOffset, charBuffer),
                        classReader.readUTF8(nameAndTypeItemOffset + 2, charBuffer),
                        classReader.readUnsignedShort(itemOffset)
                    );
                    break;
                case FSymbol.CONSTANT_STRING_TAG:
                case FSymbol.CONSTANT_CLASS_TAG:
                case FSymbol.CONSTANT_METHOD_TYPE_TAG:
                case FSymbol.CONSTANT_MODULE_TAG:
                case FSymbol.CONSTANT_PACKAGE_TAG:
                    addConstantUtf8Reference(itemIndex, itemTag,
                        classReader.readUTF8(itemOffset, charBuffer));
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            itemIndex +=
                (itemTag == FSymbol.CONSTANT_LONG_TAG || itemTag == FSymbol.CONSTANT_DOUBLE_TAG) ?2:1;
        }
    }

    private void addConstantUtf8Reference(int itemIndex, int itemTag, String value) {
        add(new FEntry(itemIndex, itemTag, value, hash(itemTag, value)));
    }

    private void addConstantDynamicOrInvokeDynamicReference(int itemTag, int itemIndex,
        String name, String desc, int bootstrapMethodIndex) {
        int hashCode = hash(itemTag, name, desc, bootstrapMethodIndex);
        add(new FEntry(itemIndex, itemTag, null, name, desc, bootstrapMethodIndex, hashCode));
    }

    private void addConstantMethodHandle(int itemIndex, int referenceKind, String owner, String name, String desc) {
        int tag = FSymbol.CONSTANT_METHOD_HANDLE_TAG;
        int hashCode = hash(tag, owner, name, desc, referenceKind);
        add(new FEntry(itemIndex, tag, owner, name, desc, referenceKind, hashCode));
    }

    FSymbol addConstant(Object value) {
        if (value instanceof Integer){
            return null;
        }
        return null;
    }

    int addConstantUtf8(String value) {
        int hashCode = hash(FSymbol.CONSTANT_UTF8_TAG,value);
        FEntry entry = get(hashCode);
        while (entry != null) {
            if (entry.tag == FSymbol.CONSTANT_UTF8_TAG
                && entry.hashCode == hashCode
                && entry.value.equals(value)) {
                return entry.index;
            }
            entry=entry.next;
        }
        constantPool.putByte(FSymbol.CONSTANT_UTF8_TAG).putUTF8(value);
        return put(new FEntry(constantPoolCount++,FSymbol.CONSTANT_UTF8_TAG,value,hashCode)).index;
    }

    private void addConstantUtf8(int itemIndex, String value) {
        int tag = FSymbol.CONSTANT_UTF8_TAG;
        add(new FEntry(itemIndex, tag, value, hash(tag, value)));
    }

    private void addConstantLongOrDouble(int itemIndex, int itemTag, long value) {
        add(new FEntry(itemIndex, itemTag, value, hash(itemTag, value)));
    }

    private void addConstantNameAndType(int itemIndex, String name, String desc) {
        int tag = FSymbol.CONSTANT_NAME_AND_TYPE_TAG;
        add(new FEntry(itemIndex, tag, name, desc, hash(tag, name, desc)));
    }

    FSymbol addConstantInerger(int value) {
        return addConstantIntegerOrFloat(FSymbol.CONSTANT_INTEGER_TAG, value);
    }

    FSymbol addConstantFloat(float value) {
        return addConstantIntegerOrFloat(FSymbol.CONSTANT_FLOAT_TAG,Float.floatToRawIntBits(value));
    }

    FSymbol addConstantIntegerOrFloat(int tag,int value) {
        int hashCode = hash(tag, value);
        FEntry entry = get(hashCode);
        while (entry != null) {
            if (entry.tag==tag&&entry.hashCode==hashCode&&entry.data==value){
                return entry;
            }
            entry=entry.next;
        }
        constantPool.putByte(tag).putInt(value);
        return put(new FEntry(constantPoolCount++,tag,value,hashCode));
    }

    private void addConstantIntegerOrFloat(int itemIndex, int itemTag, int value) {
        add(new FEntry(itemIndex, itemTag, value, hash(itemTag, value)));
    }

    void addConstantMemberReference(
        int index,
        int tag,
        String owner,
        String name,
        String descriptor
    ) {
        add(new FEntry(index, tag, owner, name, descriptor, 0, hash(tag, owner, name, descriptor)));
    }

    FEntry put(FEntry entry) {
        if (entryCount > (entries.length*3)/4) {
            int currentCapacity = entries.length;
            int newCapacity = currentCapacity * 2+1;
            FEntry[] newEntries=new FEntry[newCapacity];
            for (int i = currentCapacity-1;i>=0;--i) {
                FEntry currentEntry=entries[i];
                while (currentEntry!=null){
                    int newCurrentEntryIndex=currentEntry.hashCode%newCapacity;
                    FEntry nextEntry = currentEntry.next;
                    currentEntry.next = newEntries[newCurrentEntryIndex];
                    newEntries[newCurrentEntryIndex]=currentEntry;
                    currentEntry=nextEntry;
                }
            }
            entries=newEntries;
        }
        entryCount++;
        int index = entry.hashCode%entries.length;
        entry.next=entries[index];
        return entries[index]=entry;
    }

    void add(FEntry entry) {
        entryCount++;
        int index = entry.hashCode % entries.length;
        entry.next = entries[index];
        entries[index] = entry;
    }

    int setMajorVersionAndClassName(int majorVersion, String className) {
        this.majorVersion = majorVersion;
        this.className = className;
        return addConstantClass(className).index;
    }

    FSymbol addConstantClass(String value) {
        return addConstantUtf8Reference(FSymbol.CONSTANT_CLASS_TAG, value);
    }

    private FSymbol addConstantUtf8Reference(int tag, String value) {
        int hashCode = hash(tag, value);
        FEntry entry = get(hashCode);
        while (entry != null) {
            if (entry.tag == tag && entry.hashCode == hashCode && entry.value.equals(value)) {
                return entry;
            }
            entry = entry.next;
        }
        constantPool.put12(tag, addConstantUtf8(value));
        return put(new FEntry(constantPoolCount++,tag,value,hashCode));
    }

    FEntry get(int hashCode) {
        return entries[hashCode % entries.length];
    }

    // -----------------------------------------------------------------------------------------------
    // Static helper methods to compute hash codes.
    // -----------------------------------------------------------------------------------------------

    private static int hash(final int tag, final int value) {
        return 0x7FFFFFFF & (tag + value);
    }

    private static int hash(final int tag, final long value) {
        return 0x7FFFFFFF & (tag + (int) value + (int) (value >>> 32));
    }

    private static int hash(final int tag, final String value) {
        return 0x7FFFFFFF & (tag + value.hashCode());
    }

    private static int hash(final int tag, final String value1, final int value2) {
        return 0x7FFFFFFF & (tag + value1.hashCode() + value2);
    }

    private static int hash(final int tag, final String value1, final String value2) {
        return 0x7FFFFFFF & (tag + value1.hashCode() * value2.hashCode());
    }

    private static int hash(
        final int tag, final String value1, final String value2, final int value3) {
        return 0x7FFFFFFF & (tag + value1.hashCode() * value2.hashCode() * (value3 + 1));
    }

    private static int hash(
        final int tag, final String value1, final String value2, final String value3) {
        return 0x7FFFFFFF & (tag + value1.hashCode() * value2.hashCode() * value3.hashCode());
    }

    private static int hash(
        final int tag,
        final String value1,
        final String value2,
        final String value3,
        final int value4) {
        return 0x7FFFFFFF & (tag + value1.hashCode() * value2.hashCode() * value3.hashCode() * value4);
    }

    static class FEntry extends FSymbol {
        int hashCode;

        FEntry next;

        FEntry(
            int index,
            int tag,
            String owner,
            String name,
            String value,
            long data,
            int hashCode
        ) {
            super(index, tag, owner, name, value, data);
            this.hashCode = hashCode;
        }

        FEntry(int index, int tag, String value, int hashCode) {
            super(index, tag, null, null, value, 0);
            this.hashCode = hashCode;
        }

        FEntry(int index, int tag, String value, long data, int hashCode) {
            super(index, tag, null, null, value, data);
            this.hashCode = hashCode;
        }

        FEntry(int index, int tag, String name, String value, int hashCode) {
            super(index, tag, null, name, value, 0);
            this.hashCode = hashCode;
        }

        FEntry(int index, int tag, long data, int hashCode) {
            super(index, tag, null, null, null, data);
            this.hashCode = hashCode;
        }
    }
}
