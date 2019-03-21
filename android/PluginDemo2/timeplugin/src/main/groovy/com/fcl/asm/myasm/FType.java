package com.fcl.asm.myasm;

/**
 * Created by changle on 2019/3/20.
 */

public class FType {
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
}
