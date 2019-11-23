package com.screw;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class Agent {
    public static void agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
        CustomClassTransformer transformer = new CustomClassTransformer(inst);
        transformer.retransform();
    }
}
