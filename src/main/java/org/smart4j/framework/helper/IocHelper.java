package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 控制翻转 帮助类
 * Created by Administrator on 2017/3/16.
 */
public class IocHelper {
    /**
     * 这个静态块实现Ioc，但是什么时候去实现他呢？我们需要一个统一的地方来加载这个IocHelper
     */
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if(beanMap != null && beanMap.isEmpty() == false){
            for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                Class<?> cls = entry.getKey();
                Object obj = entry.getValue();
                // 获取定义类的所有的成员变量
                Field[] fields = cls.getDeclaredFields();
                if(fields != null) {
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(Inject.class)) {
                            Class<?> beanType = field.getType();
                            Object o = beanMap.get(beanType);
                            if(o != null){
                                ReflectionUtil.setFieldValue(obj,field,o);
                            }
                        }
                    }
                }
            }
        }
    }
}
