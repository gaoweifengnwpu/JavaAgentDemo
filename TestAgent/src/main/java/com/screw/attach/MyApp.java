package com.screw.attach;

import java.io.File;
import java.util.Scanner;


public class MyApp {
    public static void main(String[] args) {
        while (1==1){
            File f =new File("");
            f.exists();
            System.out.print("输入");
            Scanner scan = new Scanner(System.in);
            System.out.println(scan.next());
            new MyApp().sayhello();
        }
    }


    public void sayhello(){
        System.out.println("he!!!!!!");

    }
}