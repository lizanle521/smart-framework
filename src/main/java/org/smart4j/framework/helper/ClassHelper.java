package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.util.ClassUtil;
import org.smart4j.framework.util.ConfigHelper;
import org.springframework.stereotype.Controller;

import java.lang.annotation.Annotation;
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
        for (Class<?> cls : CLASS_SET) {
            if(cls.isAnnotationPresent(Service.class)){
                classSet.add(cls);
            }
        }
        return  classSet;
    }

    /**
     * 获取所有controller类
     * @return
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if(cls.isAnnotationPresent(Action.class)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取所有bean类，包括controller和service
     * @return
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        classSet.addAll(getControllerClassSet());
        classSet.addAll(getServiceClassSet());
        return classSet;
    }

    /**
     * 获取应用包名下所有的子类或者实现类
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
        Set<Class<?>> set = new HashSet<Class<?>>();
        for (Class<?> aClass : CLASS_SET) {
            if(superClass.isAssignableFrom(aClass) && !superClass.equals(aClass)){
                set.add(aClass);
            }
        }
        return set;
    }

    /**
     * 获取应用包名下的所有的带有某个注解的类
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
        HashSet<Class<?>> set = new HashSet<Class<?>>();
        for (Class<?> aClass : CLASS_SET) {
            if(aClass.isAnnotationPresent(annotationClass)){
                set.add(aClass);
            }
        }
        return  set;
    }


}
