package org.smart4j.framework.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Request;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.ClassUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  Controller助手类
 * Created by Administrator on 2017/3/17.
 */
public final class ControllerHelper {
    /**
     * 请求与处理器的映射关系
     */
    private static Map<Request,Handler> ACTION_MAP = new HashMap<Request,Handler>();

    static {
        Set<Class<?>> classSet = ClassHelper.getControllerClassSet();
        if(CollectionUtils.isNotEmpty(classSet)){
            // 遍历controller
            for (Class<?> cls : classSet) {
                // 获取controller类中定义的方法
                Method[] methods = cls.getDeclaredMethods();
                //遍历这些方法
                for (Method method : methods) {
                    // 判断方法是否有Action注解
                    if(method.isAnnotationPresent(Action.class)){
                        Action annotation = method.getAnnotation(Action.class);
                        String value = annotation.value();
                        // 验证是否符合url映射规则
                        if(value.matches("\\w+:/\\w+")){
                            String[] split = value.split(":");
                            if(ArrayUtil.isNotEmpty(split) && split.length == 2){
                                String requestMethod = split[0];
                                String requestPath = split[1];
                                Request request = new Request(requestMethod, requestPath);
                                Handler handler = new Handler(cls, method);
                                ACTION_MAP.put(request,handler);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取handler
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod,String requestPath){
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
