package com.screw;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class CmdClassVisitor extends ClassVisitor {

    public CmdClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

        if ("start".equals(name) && "()Ljava/lang/Process;".equals(desc)) {

            return new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
                @Override
                public void visitCode() {

                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, "java/lang/ProcessBuilder", "command", "Ljava/util/List;");
                    mv.visitMethodInsn(INVOKESTATIC, "com/screw/ProcessBuilderHook", "start", "(Ljava/util/List;)V", false);
                    mv.visitTypeInsn(Opcodes.NEW, "java/lang/IllegalArgumentException");
                    super.visitCode();
                }
            };
        }
        return mv;
    }
}
