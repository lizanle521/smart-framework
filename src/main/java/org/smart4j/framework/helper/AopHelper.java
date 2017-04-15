package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.proxy.AspectProxy;
import org.smart4j.framework.proxy.Proxy;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by lizanle on 2017/4/14.
 */
public final  class AopHelper {

    /**
     * 获取所有带Acpect注解里边声明的类
     * 譬如 @Aspect(Controller.class),那么获取的是有Controller注解的类
     * @param aspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        HashSet<Class<?>> set = new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();
        if(annotation != null && annotation.equals(Aspect.class)){
            set.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return set;
    }

    /**
     * 获取代理类和目标类之间的关系
     * 这里代理类不确定，但是代理都是AspectProxy的子类，并且有Aspect注解
     * 目标类是 每个类上边有AspectProxy子类的注解的类
     * @return
     * @throws Exception
     */
    private static Map<Class<?>,Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>,Set<Class<?>>> classHashMap = new HashMap<Class<?>,Set<Class<?>>>();
        Set<Class<?>> classSetBySuper = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> aClass : classSetBySuper) {
            if(aClass.isAnnotationPresent(Aspect.class)){
                Aspect annotation = aClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(annotation);
                classHashMap.put(aClass,targetClassSet);
            }
        }
        return classHashMap;
    }

    /**
     * 根据代理类与目标类之间的关系，可以得出代理类与目标对象之间的映射关系
     * @param proxyMap
     * @return
     * @throws Exception
     */
    private static Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
        HashMap<Class<?>,List<Proxy>> classHashMap = new HashMap<Class<?>,List<Proxy>>();
        Set<Map.Entry<Class<?>, Set<Class<?>>>> entries = proxyMap.entrySet();
        for (Map.Entry<Class<?>, Set<Class<?>>> entry : entries) {
            Class<?> key = entry.getKey();
            Set<Class<?>> value = entry.getValue();
            for (Class<?> cls : value) {
                Proxy proxy = (Proxy)key.newInstance();
                if(classHashMap.containsKey(cls)){
                    classHashMap.get(cls).add(proxy);
                }else{
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    classHashMap.put(cls,proxyList);
                }
            }
        }
        return classHashMap;
    }

}
