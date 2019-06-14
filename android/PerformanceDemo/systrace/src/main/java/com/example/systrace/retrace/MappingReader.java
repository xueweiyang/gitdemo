package com.example.systrace.retrace;

import com.android.ddmlib.Log;

import java.io.*;

public class MappingReader {
    private final static String TAG = "MappingReader";
    private final static String SPLIT = ":";
    private final static String SPACE = " ";
    private final static String ARROW = "->";
    private final static String LEFT_PUNC = "(";
    private final static String RIGHT_PUNC = ")";
    private final static String DOT = ".";
    File proguardMappingFile;

    public MappingReader(File proguardMappingFile) {
        this.proguardMappingFile = proguardMappingFile;
    }

    public void read(MappingProcessor mappingProcessor) throws IOException {
        LineNumberReader reader = new LineNumberReader(new BufferedReader(new FileReader(proguardMappingFile)));
        try {
            String className = null;
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                line = line.trim();
                if (!line.startsWith("#")) {
                    if (line.endsWith(SPLIT)) {
                        className = parseClassMapping(line, mappingProcessor);
                    } else if (className != null) {
                        parseClassMemberMapping(className, line, mappingProcessor);
                    }
                } else {
                    Log.i(TAG, "comment:# " + line);
                }
            }
        } catch (IOException e) {
            throw new IOException("can't read mapping file", e);
        } finally {
            reader.close();
        }
    }

    String parseClassMapping(String line,MappingProcessor mappingProcessor) {
        int leftIndex=line.indexOf(ARROW);
        if (leftIndex<0){
            return null;
        }
        int offset = 2;
        int rightIndex = line.indexOf(SPLIT,leftIndex+offset);
        if (rightIndex < 0){
            return null;
        }
        String className = line.substring(0,leftIndex).trim();
        String newClassName = line.substring(leftIndex+offset,rightIndex).trim();

        boolean ret = mappingProcessor.processClassMapping(className, newClassName);
        return ret?className:null;
    }

}
