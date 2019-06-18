package com.example.systrace;

import com.example.systrace.item.TraceMethod;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class MethodTracker {
    String TAG = "MethodTracker";
    TraceBuildConfig traceBuildConfig;
    HashMap<String, TraceMethod> collectMethodMap;
    HashMap<String, String> collectedClassExtendMap;
    AtomicInteger traceMethodCount = new AtomicInteger();

    public MethodTracker(TraceBuildConfig config, HashMap<String, TraceMethod> collectMethodMap,
                  HashMap<String, String> collectedClassExtendMap) {
        this.traceBuildConfig = config;
        this.collectedClassExtendMap = collectedClassExtendMap;
        this.collectMethodMap = collectMethodMap;
    }

    public void trace(Map<File, File> srcFolderList, Map<File, File> dependencyJarList) {
        traceMethodFromSrc(srcFolderList);
        traceMethodFromJar(dependencyJarList);
    }

    void traceMethodFromSrc(Map<File, File> srcMap) {
        if (null != srcMap) {
            for (Map.Entry<File, File> entry : srcMap.entrySet()) {
                innerTraceMethodFromSrc(entry.getKey(), entry.getValue());
            }
        }
    }

    void traceMethodFromJar(Map<File, File> dependencyMap) {
        if (null != dependencyMap) {
            for (Map.Entry<File, File> entry : dependencyMap.entrySet()) {
                innerTraceMethodFromJar(entry.getKey(), entry.getValue());
            }
        }
    }

    void innerTraceMethodFromJar(File input, File output) {
        ZipOutputStream zipOutputStream = null;
        ZipFile zipFile = null;
        try {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(output));
            zipFile = new ZipFile(input);
            Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
            while (enumeration.hasMoreElements()) {
                ZipEntry zipEntry = enumeration.nextElement();
                String zipEntryName = zipEntry.getName();
                if (traceBuildConfig.isNeedTraceClass(zipEntryName)) {
                    InputStream inputStream = zipFile.getInputStream(zipEntry);
                    ClassReader classReader = new ClassReader(inputStream);
                    ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                    ClassVisitor visitor = new TraceClassAdapter(Opcodes.ASM5, classWriter);
                    classReader.accept(visitor, ClassReader.EXPAND_FRAMES);
                    byte[] data = classWriter.toByteArray();
                    InputStream byteArrayInputStrem = new ByteArrayInputStream(data);
                    ZipEntry newZipEntry = new ZipEntry(zipEntryName);
                    Util.addZipEntry(zipOutputStream, newZipEntry, byteArrayInputStrem);
                } else {
                    InputStream inputStream = zipFile.getInputStream(zipEntry);
                    ZipEntry newZipEntry = new ZipEntry(zipEntryName);
                    Util.addZipEntry(zipOutputStream, newZipEntry, inputStream);
                }
            }
        } catch (Exception e) {

        } finally {
            try {


                if (zipOutputStream != null) {
                    zipOutputStream.finish();
                    zipOutputStream.flush();
                    zipOutputStream.close();
                }
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (Exception e) {

            }
        }
    }

    void innerTraceMethodFromSrc(File input, File output) {

        ArrayList<File> classFileList = new ArrayList<>();
        if (input.isDirectory()) {
            listClassFiles(classFileList, input);
        } else {
            classFileList.add(input);
        }

        for (File classFile : classFileList) {
//            Log.e(TAG, String.format("input file:%s out:", classFile.getAbsolutePath()));
            InputStream is = null;
            FileOutputStream os = null;
            try {
                String changedFileInputFullPath = classFile.getAbsolutePath();
                File changedFileOutput = new File(changedFileInputFullPath.replace(input.getAbsolutePath(), output.getAbsolutePath()));
                if (!changedFileOutput.exists()) {
                    changedFileOutput.getParentFile().mkdirs();
                }
                changedFileOutput.createNewFile();

                if (traceBuildConfig.isNeedTraceClass(classFile.getName())) {
                    is = new FileInputStream(classFile);
                    ClassReader classReader = new ClassReader(is);
                    ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                    ClassVisitor classVisitor = new TraceClassAdapter(Opcodes.ASM5, classWriter);
                    classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
                    is.close();

                    if (output.isDirectory()) {
                        os = new FileOutputStream(changedFileOutput);
                    } else {
                        os = new FileOutputStream(output);
                    }
                    os.write(classWriter.toByteArray());
                    os.close();
                } else {
                    Util.copyFileUsingStream(classFile, changedFileOutput);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is!=null){

                        is.close();
                    }
                    if (os!=null){

                        os.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class TraceClassAdapter extends ClassVisitor {

        String className;
        boolean isABSClass = false;
        boolean isMethodBeatClass = false;
        boolean hasWindowFocusMethod=false;

        public TraceClassAdapter(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces);
            this.className = name;
            if ((access & Opcodes.ACC_ABSTRACT) > 0 || (access & Opcodes.ACC_INTERFACE) > 0) {
                isABSClass = true;
            }
            if (traceBuildConfig.isMethodBeatClass(className, collectedClassExtendMap)) {
                isMethodBeatClass = true;
            }
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (isABSClass) {
                return super.visitMethod(access, name, desc, signature, exceptions);
            } else {
                if (!hasWindowFocusMethod){
                    hasWindowFocusMethod = traceBuildConfig.isWindowFocusChangeMethod(name,desc);
                }
                MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
                return new TraceMethodAdapter(api, methodVisitor, access, name, desc, className, isMethodBeatClass);
            }
        }

        @Override
        public void visitEnd() {
            TraceMethod traceMethod = TraceMethod.create(-1,Opcodes.ACC_PUBLIC,className,
                    TraceBuildConstants.MATRIX_TRACE_ON_WINDOW_FOCUS_METHOD, TraceBuildConstants.MATRIX_TRACE_ON_WINDOW_FOCUS_METHOD_ARGS);
            if (!hasWindowFocusMethod && traceBuildConfig.isActivityOrSubClass(className,collectedClassExtendMap)
            && collectMethodMap.containsKey(traceMethod.methodName)){

            }
            super.visitEnd();
        }
    }

    class TraceMethodAdapter extends AdviceAdapter {

        String methodName;
        String name;
        String className;
        boolean isMethodBeatClass;

        protected TraceMethodAdapter(int api, MethodVisitor mv, int access, String name, String desc,
                                     String className, boolean isMethodBeatClass) {
            super(api, mv, access, name, desc);
            TraceMethod traceMethod = TraceMethod.create(0, access, className, name, desc);
            this.methodName = traceMethod.methodName;
            this.isMethodBeatClass = isMethodBeatClass;
            this.className = className;
            this.name = name;
        }

        @Override
        protected void onMethodEnter() {
            TraceMethod traceMethod = collectMethodMap.get(methodName);
            if (traceMethod != null) {
                traceMethodCount.incrementAndGet();
                String sectionName = methodName;
                int length = sectionName.length();
                if (length > TraceBuildConstants.MAX_SECTION_NAME_LEN) {
                    int paramIndex = sectionName.indexOf('(');
                    sectionName = sectionName.substring(0, paramIndex);
                    length = sectionName.length();
                    if (length > TraceBuildConstants.MAX_SECTION_NAME_LEN) {
                        sectionName = sectionName.substring(length - TraceBuildConstants.MAX_SECTION_NAME_LEN);
                    }
                }
                Log.e(TAG, String.format("classname:%s method:%s",className,methodName));
                mv.visitLdcInsn(sectionName);
                mv.visitMethodInsn(INVOKESTATIC, TraceBuildConstants.MATRIX_TRACE_METHOD_BEAT_CLASS, "i", "(Ljava/lang/String;)V", false);
            }
        }

        @Override
        protected void onMethodExit(int opcode) {
            TraceMethod traceMethod = collectMethodMap.get(methodName);
            if (traceMethod != null) {
                traceMethodCount.incrementAndGet();
                mv.visitMethodInsn(INVOKESTATIC, TraceBuildConstants.MATRIX_TRACE_METHOD_BEAT_CLASS, "o", "()V", false);
            }
        }
    }

    void listClassFiles(ArrayList<File> classFiles, File folder) {
        File[] files = folder.listFiles();
        if (null == files) {
            Log.e(TAG, "[listClassFiles] files is null! %s", folder.getAbsolutePath());
            return;
        }
        for (File file : files) {
            if (file == null) {
                continue;
            }
            if (file.isDirectory()) {
                listClassFiles(classFiles, file);
            } else {
                if (null != file && file.isFile()) {
                    classFiles.add(file);
                }
            }
        }
    }

    private void traceWindowFocusChangeMethod(MethodVisitor mv) {
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, TraceBuildConstants.MATRIX_TRACE_METHOD_BEAT_CLASS, "at", "(Landroid/app/Activity;Z)V", false);
    }

    void insertWindowFocusChangeMethod(ClassVisitor cv) {
        MethodVisitor methodVisitor = cv.visitMethod(Opcodes.ACC_PUBLIC,TraceBuildConstants.MATRIX_TRACE_ON_WINDOW_FOCUS_METHOD,
                TraceBuildConstants.MATRIX_TRACE_ON_WINDOW_FOCUS_METHOD_ARGS, null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, TraceBuildConstants.MATRIX_TRACE_ACTIVITY_CLASS,
                TraceBuildConstants.MATRIX_TRACE_ON_WINDOW_FOCUS_METHOD,
                TraceBuildConstants.MATRIX_TRACE_ON_WINDOW_FOCUS_METHOD_ARGS,false);
        methodVisitor.visitInsn(Opcodes.RETURN);
        methodVisitor.visitMaxs(2,2);
        methodVisitor.visitEnd();
    }
}
