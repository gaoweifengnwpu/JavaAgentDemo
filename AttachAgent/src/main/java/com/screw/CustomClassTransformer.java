package com.screw;

import org.objectweb.asm.*;

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

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("In Transform");
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw) {
            @Override
            public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
//                return super.visitMethod(i, s, s1, s2, strings);
                final MethodVisitor mv = super.visitMethod(i, s, s1, s2, strings);
                if ("sayhello".equals(s)) {
                    return new MethodVisitor(Opcodes.ASM5, mv) {
                        @Override
                        public void visitCode() {
                            super.visitCode();
                            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                            mv.visitLdcInsn("注入Agent方法");
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                        }
                    };
                }
                return mv;
            }
        };
        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        classfileBuffer = cw.toByteArray();
        return classfileBuffer;
    }

    public void retransform() throws UnmodifiableClassException {
        LinkedList<Class> retransformClasses = new LinkedList<Class>();
        Class[] loadedClasses = inst.getAllLoadedClasses();
        for (Class clazz : loadedClasses) {
            if ("com.screw.attach.MyApp".equals(clazz.getName())) {
                if (inst.isModifiableClass(clazz) && !clazz.getName().startsWith("java.lang.invoke.LambdaForm")) {
                    inst.retransformClasses(clazz);
                }
            }
        }
    }
}
