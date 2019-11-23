package com.screw;

import java.lang.instrument.Instrumentation;

public class Agent {

    private CustomClassTransformer transformer;

    public static void premain(String agentArgs, Instrumentation inst) throws Exception {
        //将Agent jar加载到Bootstrap中
        JarFileHelper.addJarToBootstrap(inst);
        CustomClassTransformer transformer = new CustomClassTransformer(inst);
        //因为agent加载的时候Bootstrap已经被加载了。所以需要重新转换
        transformer.retransform();
    }
}
