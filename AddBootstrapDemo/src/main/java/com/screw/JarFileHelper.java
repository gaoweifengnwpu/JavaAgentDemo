package com.screw;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.net.URLDecoder;
import java.util.jar.JarFile;


/**
 * Created by tyy on 3/29/17.
 * 对jar文件操作的工具类
 */
public class JarFileHelper {

    /**
     * 添加jar文件到jdk的跟路径下，优先加载
     *
     * @param inst {@link Instrumentation}
     */
    public static void addJarToBootstrap(Instrumentation inst) throws IOException {
        String localJarPath = getLocalJarPath();
        inst.appendToBootstrapClassLoaderSearch(new JarFile(localJarPath));
    }

    /**
     * 获取当前所在jar包的路径
     *
     * @return jar包路径
     */
    public static String getLocalJarPath() {
        URL localUrl = Agent.class.getProtectionDomain().getCodeSource().getLocation();
        String path = null;
        try {
            path = URLDecoder.decode(
                    localUrl.getFile().replace("+", "%2B"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.err.println("[OpenRASP] Failed to get jarFile path.");
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 获取当前jar包所在的文件夹路径
     *
     * @return jar包所在文件夹路径
     */
    public static String getLocalJarParentPath() {
        String jarPath = getLocalJarPath();
        return jarPath.substring(0, jarPath.lastIndexOf("/"));
    }

}
