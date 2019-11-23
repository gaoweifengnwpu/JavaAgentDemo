package com.screw;

import java.util.List;

public class ProcessBuilderHook {
    public static void start(List<String> commands) throws Exception {
        String[] commandArr = commands.toArray(new String[commands.size()]);
        for(String command: commandArr){
            System.out.println("我Hook到了命令："+command);
            System.out.println("此处可以做相应的阻断处理！");
        }
    }
}
