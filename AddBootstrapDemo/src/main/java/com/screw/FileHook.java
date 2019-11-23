package com.screw;

import java.util.List;

public class FileHook {
    public static void start() throws Exception {
        System.out.println("非BootstrapClassLoader的方法已注入");
    }
}
