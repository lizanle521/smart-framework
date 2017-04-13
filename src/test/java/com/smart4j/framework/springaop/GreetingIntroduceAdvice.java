package com.smart4j.framework.springaop;

import com.smart4j.framework.aop.Apology;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import org.springframework.stereotype.Component;

/**
 * Created by lizanle on 2017/4/13.
 */
@Component
public class GreetingIntroduceAdvice extends DelegatingIntroductionInterceptor implements Apology {
    public void saySorry(String name) {
        System.out.println("sorry:" + name);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        return super.invoke(mi);
    }
}
