package org.smart4j.framework;

import org.smart4j.framework.util.ConfigHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

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




}
