package com.screw;

import javassist.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class HookTransform implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        className = className.replace("/", ".");
        if (className.contains("ProcessBuilder")) {
            CtClass ctClass = null;
            CtMethod ctMethod = null;
            try{
                ClassPool classPool = new ClassPool();
                addLoader(classPool, loader);
                ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
                ctMethod = ctClass.getMethod("start", "()Ljava/lang/Process;");
                //ctMethod.insertBefore(" System.out.println($0.command);");
                ctMethod.insertBefore("com.screw.ProcessBuilderHook.start($0.command);");
                classfileBuffer = ctClass.toBytecode();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (CannotCompileException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }finally {
                if (ctClass != null) {
                    ctClass.detach();
                }
            }
        }
        return classfileBuffer;
    }

    private void addLoader(ClassPool classPool, ClassLoader loader) throws NotFoundException {
        classPool.appendSystemPath();
        classPool.appendClassPath(new ClassClassPath(ProcessBuilderHook.class));
        if (loader != null) {
            classPool.appendClassPath(new LoaderClassPath(loader));
        }
    }
}
