package com.fcl.asm.myasm;

/**
 * Created by galio.fang on 19-3-19
 */
public class FAttribute {
    String type;
    byte[] content;
    FAttribute nextAttribute;

    public FAttribute(String type) {
        this.type=type;
    }

    FAttribute read(
        FClassReader classReader,
        int  offset,
        int length,
        char[] charBuffer,
        int codeAttributeOffset,
        FLabel[] labels
    ) {
        FAttribute attribute = new FAttribute(type);
        attribute.content=new byte[length];
        System.arraycopy(classReader.classFileBuffer,offset,attribute.content,0,length);
        return attribute;
    }
}
