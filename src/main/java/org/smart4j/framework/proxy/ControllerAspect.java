package org.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;

/**
 * Created by lizanle on 2017/4/14.
 */
@Aspect(Controller.class)
public class ControllerAspect extends  AspectProxy {
    private final static Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        logger.debug("---------------------------------------");
        logger.debug("class: {} ",cls.getName());
        logger.debug("method: {} ",method.getName());
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params) throws Throwable {
        logger.debug("time: {}",System.currentTimeMillis());
        logger.debug("---------------------------------------");
    }
}
