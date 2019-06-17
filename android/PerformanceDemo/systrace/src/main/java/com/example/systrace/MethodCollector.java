package com.example.systrace;

import com.example.systrace.item.TraceMethod;
import com.example.systrace.retrace.MappingCollector;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class MethodCollector {
    String TAG = "MethodCollector";
    HashMap<String, TraceMethod> collectMethodMap;
    HashMap<String, TraceMethod> collectIgnoreMethodMap;
    HashMap<String, TraceMethod> collectBlackMethodMap;

    HashMap<String, String> collectClassExtendMap;
    TraceBuildConfig traceConfig;
    MappingCollector mappingCollector;
    AtomicInteger methodId = new AtomicInteger(0);
    int incrementCount, ignoreCount;

    public MethodCollector(TraceBuildConfig config, MappingCollector mappingCollector) {
        collectMethodMap = new HashMap<>();
        collectClassExtendMap = new HashMap<>();
        collectIgnoreMethodMap = new HashMap<>();
        collectBlackMethodMap = new HashMap<>();
        traceConfig = config;
        this.mappingCollector = mappingCollector;
    }

    HashMap collect(List<File> srcFolderList, List<File> dependencyJarList) {
        traceConfig.parseBlackFile(mappingCollector);

        File originMethodMapFile = new File(traceConfig.getBaseMethodMap());
        getMethodFromBaseMethod(originMethodMapFile);
        retraceMethodMap(mappingCollector, collectMethodMap);

collectMethodFromSrc(srcFolderList,true);
collectMethodFromSrc(srcFolderList,false);
collectMethodFromJar(dependencyJarList,true);
collectMethodFromJar(dependencyJarList,false);



        return collectMethodMap;
    }

    void saveCollectedMethod(MappingCollector mappingCollector){
        File methodMapFile=new File(traceConfig.methodMapFile);
        if (!methodMapFile.getParentFile().exists()){
            methodMapFile.getParentFile().mkdirs();
        }
        List<TraceMethod> methodList = new ArrayList<>(collectMethodMap.values());
        Collections.sort(methodList, (t1, t2) -> t1.id-t2.id);

        PrintWriter pw=null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(methodMapFile,false);
            Writer w = new OutputStreamWriter(fileOutputStream, "UTF-8");
            pw = new PrintWriter(w);
            for(TraceMethod traceMethod : methodList){
                traceMethod.
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void collectMethodFromSrc(List<File> srcFolderList, boolean isSingle) {
        if (null != srcFolderList) {
            for (File srcFile : srcFolderList) {
                innerCollectMethodFromSrc(srcFile,isSingle);
            }
        }
    }

    void collectMethodFromJar(List<File> dependencyJarList,boolean isSingle){
        if (null!=dependencyJarList){
            for(File jarFile:dependencyJarList){
                innerCollectMethodFromJar(jarFile,isSingle);
            }
        }
    }

    void innerCollectMethodFromSrc(File srcFile, boolean isSingle) {
        ArrayList<File> classFileList = new ArrayList<>();
        if (srcFile.isDirectory()) {
            listClassFiles(classFileList, srcFile);
        } else {
            classFileList.add(srcFile);
        }
        for (File classFile : classFileList) {
            InputStream is = null;
            try {
                is = new FileInputStream(classFile);
                ClassReader classReader = new ClassReader(is);
                ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                ClassVisitor visitor;
                if (isSingle) {
                    visitor = new SingleTraceClassAdapter(Opcodes.ASM5,classWriter);
                } else  {
                    visitor=new TraceClassAdapter(Opcodes.ASM5,classWriter);
                }
                classReader.accept(visitor,0);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void innerCollectMethodFromJar(File fromJar,boolean isSingle){
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(fromJar);
            Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
            while (enumeration.hasMoreElements()){
                ZipEntry zipEntry = enumeration.nextElement();
                String zipEntryName =zipEntry.getName();
                if (traceConfig.isNeedTraceClass(zipEntryName)){
                    InputStream inputStream = zipFile.getInputStream(zipEntry);
                    ClassReader classReader = new ClassReader(inputStream);
                    ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                    ClassVisitor visitor;
                    if (isSingle){
                        visitor = new SingleTraceClassAdapter(Opcodes.ASM5,classWriter);
                    } else {
                        visitor=new TraceClassAdapter(Opcodes.ASM5,classWriter);
                    }
                    classReader.accept(visitor, 0);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
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
                if (null != file && file.isFile() && traceConfig.isNeedTraceClass(file.getName())) {
                    classFiles.add(file);
                }
            }
        }
    }

    void retraceMethodMap(MappingCollector processor, HashMap<String, TraceMethod> methodMap) {
        if (null == processor || null == methodMap) {
            return;
        }
        HashMap<String, TraceMethod> retraceMethodMap = new HashMap<>(methodMap.size());
        for (Map.Entry<String, TraceMethod> entry : methodMap.entrySet()) {
            TraceMethod traceMethod = entry.getValue();
            traceMethod.proguard(processor);
            retraceMethodMap.put(traceMethod.methodName, traceMethod);
        }
        methodMap.clear();
        methodMap.putAll(retraceMethodMap);
        retraceMethodMap.clear();
    }

    void getMethodFromBaseMethod(File baseMethodFile) {
        if (!baseMethodFile.exists()) {
            Log.w(TAG, "[getMethodFromBaseMethod] not exists!%s", baseMethodFile.getAbsolutePath());
            return;
        }
        Scanner fileReader = null;
        try {
            fileReader = new Scanner(baseMethodFile, "UTF-8");
            while (fileReader.hasNext()) {
                String nextLine = fileReader.nextLine();
                if (!Util.isNullOrNil(nextLine)) {
                    nextLine = nextLine.trim();
                    if (nextLine.startsWith("#")) {
                        Log.i("[getMethodFromBaseMethod] comment %s", nextLine);
                        continue;
                    }
                    String[] fields = nextLine.split(",");
                    TraceMethod traceMethod = new TraceMethod();
                    traceMethod.id = Integer.parseInt(fields[0]);
                    traceMethod.accessFlag = Integer.parseInt(fields[1]);
                    String[] methodField = fields[2].split(" ");
                    traceMethod.className = methodField[0].replace("/", ".");
                    traceMethod.methodName = methodField[1];
                    if (methodField.length > 2) {
                        traceMethod.desc = methodField[2].replace("/", ".");
                    }
                    if (methodId.get() < traceMethod.id) {
                        methodId.set(traceMethod.id);
                    }
                    if (traceConfig.isNeedTrace(traceMethod.className, mappingCollector)) {
                        collectMethodMap.put(traceMethod.methodName, traceMethod);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "[getMethodFromBaseMethod] err!");
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
        }
    }

    class SingleTraceClassAdapter extends ClassVisitor {

        public SingleTraceClassAdapter(int i, ClassVisitor classVisitor) {
            super(i, classVisitor);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces);
        }
    }

    class TraceClassAdapter extends ClassVisitor {
        String className;
        boolean isABSClass = false;

        public TraceClassAdapter(int i, ClassVisitor classVisitor) {
            super(i, classVisitor);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces);
            this.className = name;
            if ((access & Opcodes.ACC_ABSTRACT) > 0 || (access & Opcodes.ACC_INTERFACE) > 0) {
                this.isABSClass = true;
            }
            collectClassExtendMap.put(className, superName);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc,
                                         String signature, String[] exceptions) {
            if (isABSClass) {
                return super.visitMethod(access, name, desc, signature, exceptions);
            } else {
                return new CollectMethodNode(className, access, name, desc, signature, exceptions);
            }
        }
    }

    class CollectMethodNode extends MethodNode {

        String className;
        boolean isConstructor;

        CollectMethodNode(String className, int access, String name, String desc,
                          String signature, String[] exceptions) {
            super(Opcodes.ASM5, access, name, desc, signature, exceptions);
            this.className = className;
        }

        @Override
        public void visitEnd() {
            super.visitEnd();
            TraceMethod traceMethod = TraceMethod.create(0, access, className, name, desc);
            if ("<init>".equals(name)) {
                isConstructor = true;
            }
            if ((isEmptyMethod() || isGetSetMethod() || isSingleMethod())
            && traceConfig.isNeedTrace(traceMethod.className,mappingCollector)){
                ignoreCount++;
                collectIgnoreMethodMap.put(traceMethod.methodName,traceMethod);
                incrementCount++;
            } else if (!traceConfig.isNeedTrace(traceMethod.className, mappingCollector)
            && !collectBlackMethodMap.containsKey(traceMethod.className)){
                ignoreCount++;
                collectBlackMethodMap.put(traceMethod.methodName,traceMethod);
            }
        }

        boolean isGetSetMethod() {
            int ignoreCount = 0;
            ListIterator<AbstractInsnNode> iterator = instructions.iterator();
            while (iterator.hasNext()) {
                AbstractInsnNode insnNode = iterator.next();
                int opcode = insnNode.getOpcode();
                if (-1 == opcode) {
                    continue;
                }
                if (opcode != Opcodes.GETFIELD
                        && opcode != Opcodes.GETSTATIC
                        && opcode != Opcodes.H_GETFIELD
                        && opcode != Opcodes.H_GETSTATIC

                        && opcode != Opcodes.RETURN
                        && opcode != Opcodes.ARETURN
                        && opcode != Opcodes.DRETURN
                        && opcode != Opcodes.FRETURN
                        && opcode != Opcodes.LRETURN
                        && opcode != Opcodes.IRETURN

                        && opcode != Opcodes.PUTFIELD
                        && opcode != Opcodes.PUTSTATIC
                        && opcode != Opcodes.H_PUTFIELD
                        && opcode != Opcodes.H_PUTSTATIC
                        && opcode > Opcodes.SALOAD
                ) {
                    if (isConstructor && opcode == Opcodes.INVOKESPECIAL){
                        ignoreCount++;
                        if (ignoreCount>1){
                            return false;
                        }
                        continue;
                    }
                    return false;
                }
            }
            return true;
        }

        boolean isSingleMethod() {
            ListIterator<AbstractInsnNode> iterator = instructions.iterator();
            while (iterator.hasNext()) {
                AbstractInsnNode insnNode = iterator.next();
                int opcode = insnNode.getOpcode();
                if (-1 == opcode) {
                    continue;
                } else if (Opcodes.INVOKEVIRTUAL <= opcode && opcode <= Opcodes.INVOKEDYNAMIC) {
                    return false;
                }
            }
            return true;
        }

        boolean isEmptyMethod() {
            ListIterator<AbstractInsnNode> iterator = instructions.iterator();
            while (iterator.hasNext()) {
                AbstractInsnNode insnNode = iterator.next();
                int opcode = insnNode.getOpcode();
                if (-1 == opcode) {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }
    }

}
