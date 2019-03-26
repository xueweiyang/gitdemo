package com.fcl.asm.myasm;

/**
 * Created by galio.fang on 19-3-20
 */
public class FByteVector {

    /**actual number of bytes**/
    int length;

    byte[] data;

    FByteVector(){
        data=new byte[64];
    }

    public FByteVector(int initialCapacity) {
        data = new byte[initialCapacity];
    }

    FByteVector putByte(int byteValue) {
        int currentLength = length;
        if (currentLength+1>data.length) {
            enlarge(1);
        }
        data[currentLength++]=(byte)byteValue;
        length=currentLength;
        return this;
    }

    FByteVector putInt(int value){
        int currentLength=length;
        if (currentLength+4>data.length){
            enlarge(4);
        }
        byte[] currentData=data;
        currentData[currentLength++]=(byte)(value>>>24);
        currentData[currentLength++]=(byte)(value>>>16);
        currentData[currentLength++]=(byte)(value>>>8);
        currentData[currentLength++]=(byte)value;
        length = currentLength;
        return this;
    }

    FByteVector putUTF8(String stringValue) {
        int charLength = stringValue.length();
        if (charLength>65535) {
            throw new IllegalArgumentException("utf8 string too large");
        }
        int currentLength = length;
        if (currentLength+2+charLength>data.length) {
            enlarge(2+charLength);
        }
        byte[] currentData=data;
        currentData[currentLength++]=(byte)(charLength>>>8);
        currentData[currentLength++]=(byte)charLength;

        for (int i = 0; i < charLength; i++) {
            char charValue = stringValue.charAt(i);
            if (charValue >= '\u0001' && charValue <= '\u007f') {
                currentData[currentLength++]=(byte)charValue;
            } else {
                length=currentLength;
                return encodeUtf8(stringValue,i,65535);
            }
        }
        length=currentLength;
        return this;

    }

    FByteVector encodeUtf8(String stringValue,int offset, int maxByteLength) {
        return this;
    }

    FByteVector put12(int byteValue,int shortValue) {
        int currentLength = length;
        if (currentLength+3 >data.length) {
            enlarge(3);
        }
        byte[] currentData=data;
        currentData[currentLength++]=(byte)byteValue;
        currentData[currentLength++]=(byte)(shortValue>>>8);
        currentData[currentLength++]=(byte)(shortValue);
        length=currentLength;
        return this;
    }

    FByteVector putByteArray(
        byte[] byteArrayValue,int byteOffset, int byteLength
    ) {
        if (length+byteLength>data.length) {
            enlarge(byteLength);
        }
        if (byteArrayValue!=null){
            System.arraycopy(byteArrayValue,byteOffset,data,length,byteLength);
        }
        length+=byteLength;
        return this;
    }

    private void enlarge(int size) {
        int doubleCapacity=2*data.length;
        int minimalCapacity=length+size;
        byte[] newData=new byte[Math.max(doubleCapacity,minimalCapacity)];
        System.arraycopy(data, 0, newData,0,length);
        data=newData;
    }
}
