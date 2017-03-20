package org.smart4j.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * mvc中最核心的dispatch servlet
 * Created by Administrator on 2017/3/18.
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatchServlet extends HttpServlet {
    /**
     * 初始化
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        // 初始化相关的Helper类
        HelperLoader.init();
        // 获取servletContext对象，用于注册servlet
        ServletContext context = config.getServletContext();
        // 注册处理jsp的servlet
        ServletRegistration regist = context.getServletRegistration("jsp");
        regist.addMapping(ConfigHelper.getAppJspPath()+"*");

        // 处理静态资源的默认的servlet
        ServletRegistration defaultRegist = context.getServletRegistration("default");
        defaultRegist.addMapping(ConfigHelper.getAppAssetPath()+"*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod().toLowerCase();
        String pathInfo = req.getPathInfo();
        // 通过path和method找 handler
        Handler handler = ControllerHelper.getHandler(method, pathInfo);
        if(handler != null){
            Class<?> controllerClass = handler.getControllerClass();
            Object instance = BeanHelper.getInstance(controllerClass);
            // 创建请求参数对象
            HashMap<String, Object> map = new HashMap<String, Object>();
            Enumeration<String> paramName = req.getParameterNames();
            while (paramName.hasMoreElements()){
                String name = paramName.nextElement();
                String param = req.getParameter(name);
                map.put(name,param);
            }
            String body = CodecUtil.decodeUrl(StreamUtil.getString(req.getInputStream()));
            if(StringUtils.isNotEmpty(body)){
                String[] split = StringUtils.split(body, "&");
                if(ArrayUtil.isNotEmpty(split)){
                    for (String param : split) {
                        String[] paramKv = StringUtils.split(param, "=");
                        if(ArrayUtil.isNotEmpty(paramKv) && paramKv.length == 2){
                            String name = paramKv[0];
                            String value = paramKv[1];
                            map.put(name,value);
                        }

                    }
                }
            }

            Param param = new Param(map);
            Method actionMethod = handler.getMethod();
            // 调用Action方法
            Object o = ReflectionUtil.invokeMethod(instance, actionMethod, param);

            // 处理action的返回值
            if(o instanceof View){ // jsp
                View view = (View) o;
                String path = view.getPath();
                if(StringUtils.isNotEmpty(path)){
                    if(path.startsWith("/")){
                        resp.sendRedirect(req.getContextPath()+path);
                    }else{
                        Map<String, Object> model = view.getModel();
                        Set<Map.Entry<String, Object>> entries = model.entrySet();
                        for (Map.Entry<String, Object> entry : entries) {
                            req.setAttribute(entry.getKey(),entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(req,resp);
                    }
                }
            }else if(o instanceof Data){ //json
                Data data = (Data) o;
                Object model = data.getModel();
                if(model != null){
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("utf-8");
                    PrintWriter writer = resp.getWriter();
                    String json = new ObjectMapper().writeValueAsString(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }



    }
}
