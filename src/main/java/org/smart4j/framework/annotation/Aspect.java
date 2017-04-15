package org.smart4j.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by lizanle on 2017/4/14.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 注解
     */
    Class<? extends Annotation> value();


}
