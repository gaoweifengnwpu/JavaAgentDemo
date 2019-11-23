package com.screw;

public class ProcessBuilderHook {
    public static void start() {

        System.out.println("非BootstrapClassLoader的方法已注入");
    }
}
