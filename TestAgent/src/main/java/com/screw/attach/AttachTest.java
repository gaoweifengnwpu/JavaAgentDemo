package com.screw.attach;


import com.sun.tools.attach.*;


import java.io.IOException;
import java.util.List;


public class AttachTest {
    public static void main(String[] args) throws IOException, AgentLoadException, AgentInitializationException, AttachNotSupportedException {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            if (vmd.displayName().endsWith("MyApp")) {
                String pid = vmd.id();
                System.out.println("注入的进程号是: " +pid);
                VirtualMachine virtualMachine = VirtualMachine.attach(pid);
                virtualMachine.loadAgent("/Users/screw/Documents/Code/IDEACode/RASP/RaspAgent/target/agent.jar", "Attach!");
                System.out.println("ok");
                virtualMachine.detach();
            }
        }
    }
}