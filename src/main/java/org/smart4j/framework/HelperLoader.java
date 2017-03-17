package org.smart4j.framework;

import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ClassHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.helper.IocHelper;
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
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> aClass : classArray) {
            ClassUtil.loadClass(aClass.getName(),false);
        }
    }
}
