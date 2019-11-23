package com.screw;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.jar.JarFile;

public class Agent {


    public static void premain(String agentArgs, Instrumentation inst) throws IOException, UnmodifiableClassException {
       // LoadBootstrp.addJarToBootstrap(inst);

        CustomClassTransformer transformer = new CustomClassTransformer(inst);
        transformer.retransform();
    }
//
//    public static void agentmain(String agentArg, Instrumentation inst) throws IOException, UnmodifiableClassException {
//       JarFileHelper.addJarToBootstrap(inst);
//        CustomClassTransformer transformer = new CustomClassTransformer(inst);
//        transformer.retransform();
//    }
}
