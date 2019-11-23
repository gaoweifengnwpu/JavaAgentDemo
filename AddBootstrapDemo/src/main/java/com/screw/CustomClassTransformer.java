package com.screw;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;
import java.util.LinkedList;

public class CustomClassTransformer implements ClassFileTransformer {

    private Instrumentation inst;
    public CustomClassTransformer(Instrumentation inst) {
        this.inst = inst;
        inst.addTransformer(this, true);
    }

    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        className = className.replace("/", ".");

        try {
            if (className.contains("File")) {
               // if (className.contains("ProcessBuilder")) {
                ClassReader classReader  = new ClassReader(classfileBuffer);
                ClassWriter classWriter  = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                //创建一个自定义ClassVisitor，方便后续ClassReader的遍历通知
                ClassVisitor classVisitor = new TestClassVisitor(classWriter);

                classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

                classfileBuffer = classWriter.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classfileBuffer;
    }


    public void retransform() throws UnmodifiableClassException {
        LinkedList<Class> retransformClasses = new LinkedList<Class>();
        Class[] loadedClasses = inst.getAllLoadedClasses();
        for (Class clazz : loadedClasses) {
            //这可以定限制只对哪些类进行重新定义，否则所有的类全部会重新定义。包
            if ("java.io.File".equals(clazz.getName())) {
                System.out.println(clazz.getName());
                if (inst.isModifiableClass(clazz) && !clazz.getName().startsWith("java.lang.invoke.LambdaForm")) {
                    inst.retransformClasses(clazz);
                }
            }
        }
    }
}
