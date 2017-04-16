package org.smart4j.framework;

import org.smart4j.framework.helper.*;
import org.smart4j.framework.util.ClassUtil;

/**
 * 加载相应的Helper类
 * Created by Administrator on 2017/3/18.
 */
public final class HelperLoader {
    public static void init() {
        Class<?>[] classArray = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> aClass : classArray) {
            ClassUtil.loadClass(aClass.getName(),false);
        }
    }
}
