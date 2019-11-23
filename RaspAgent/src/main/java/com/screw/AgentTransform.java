package com.screw;

import org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;


public class AgentTransform implements ClassFileTransformer {


    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        className = className.replace("/", ".");

        try {
           // if (className.contains("MyApp")) {
            if (className.contains("io.File")) {
                System.out.println("Load class: " + className);

                ClassReader  classReader  = new ClassReader(classfileBuffer);
                ClassWriter  classWriter  = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
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


}