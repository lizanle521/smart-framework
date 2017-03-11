package org.smart4j.framework.helper;

import org.smart4j.framework.util.ClassUtil;
import org.smart4j.framework.util.ConfigHelper;

import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手类
 * Created by Administrator on 2017/3/11.
 */
public final class ClassHelper {
    /**
     * 定义类集合（用于存放加载的类）
     */
    private static Set<Class<?>> CLASS_SET;

    static {
        String appBasePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(appBasePackage);
    }

    /**
     * 获取应用包名下的所有类
     * @return
     */
    public static Set<Class<?>> getClassSet(){
        return  CLASS_SET;
    }

    /**
     * 获取service类
     * @return
     */
    public static  Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        
    }
}
