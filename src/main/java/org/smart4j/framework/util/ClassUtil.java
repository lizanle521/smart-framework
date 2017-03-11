package org.smart4j.framework.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具
 * Created by Administrator on 2017/3/9.
 */
public final class ClassUtil {
    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     * @return
     */
    private static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类，是否初始化是指是否执行类的静态代码块
     * 为了提高加载性能，可以将isInitialized设置为false
     * @param className 类名
     * @param isInitialized 是否执行类的静态代码块
     * @return
     */
    public static Class<?> loadClass(String className,boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className,isInitialized,getClassLoader());
        } catch (ClassNotFoundException e) {

            logger.error("load class failure",e);
            throw  new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 获取包下所有的类
     * @param packageName 包名
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            Enumeration<URL> urlEnumeration = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urlEnumeration.hasMoreElements()){
                URL url = urlEnumeration.nextElement();
                if(url != null){
                    String protocol = url.getProtocol();
                    if("file".equals(protocol)){
                        String packagePath = url.getPath().replaceAll("%20", "");
                        addClass(classSet,packagePath,packageName);
                    }else if("jar".equals(protocol)){
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if(jarURLConnection != null){
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if(jarFile != null){
                                Enumeration<JarEntry> entries = jarFile.entries();
                                while (entries.hasMoreElements()){
                                    JarEntry jarEntry = entries.nextElement();
                                    String name = jarEntry.getName();
                                    if(name.endsWith(".class")){
                                        String clasName = name.substring(0, name.lastIndexOf("."));
                                        doAddClass(classSet,clasName);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("load class set failure",e);
            throw  new RuntimeException(e);
        }
        return  classSet;
    }

    /**
     *
     * @param classSet
     * @param packagePath
     * @param packageName
     */
    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile() && file.getName().endsWith(".class") || file.isDirectory();
            }
        });
        for(File file : files){
            String name = file.getName();
            if(file.isFile()){
                String className = name.substring(0,name.lastIndexOf("."));
                if(StringUtils.isNotEmpty(packagePath)){
                    className = packagePath+"."+className;
                }
                doAddClass(classSet,className);
            }else{
                String subpackagePath = name;
                if(StringUtils.isNotEmpty(packagePath)){
                    subpackagePath = packagePath + "/" + subpackagePath;
                }
                String subpackagename = name;
                if(StringUtils.isNotEmpty(packageName)){
                    subpackagename = packageName + "." + subpackagename;
                }
                addClass(classSet,subpackagePath,packageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }


}
