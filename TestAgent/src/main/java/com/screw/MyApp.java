package com.screw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.JarFile;

public class MyApp {
    public static void main(String[] args) throws IOException {
        File f = new File("/tmp/aaatest");
        f.getAbsoluteFile();
        //f.createNewFile();
        //FileInputStream fis = new FileInputStream(f);
        //f.exists();
       // System.out.println("accccc");
        new MyApp().sayhello();

    }

    public void sayhello(){
        System.out.println("hello !!!!!!!!!!!");
    }
}
