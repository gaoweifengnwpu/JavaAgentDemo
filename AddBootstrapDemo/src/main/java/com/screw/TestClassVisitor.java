package com.screw;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class TestClassVisitor extends ClassVisitor {
    public TestClassVisitor(ClassVisitor cv) {

        super(Opcodes.ASM5, cv);
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if ("getAbsoluteFile".equals(name) && "()Ljava/io/File;".equals(desc)) {

            return new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
                @Override
                public void visitCode() {
                    super.visitCode();
                    mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                    mv.visitLdcInsn("注入Agent方法");
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                    //测试File Bootstrap_Classload调用非自己定义的类也就是非Bootstrap_Classload加载的类
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/screw/FileHook", "start", "()V", false);

                }
            };
        }
        return mv;
    }
}
