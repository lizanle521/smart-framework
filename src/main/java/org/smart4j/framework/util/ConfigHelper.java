package org.smart4j.framework.util;

import java.util.Properties;

/**
 * 属性文件助手类
 * Created by Administrator on 2017/3/8.
 */
public class ConfigHelper {
    private static final Properties CONFIG_PROPERS = PropsUtil.loadProperties(ConfigConstant.CONFIG_FILE);

    /**
     * 获取jdbc驱动
     * @return jdbc驱动
     */
    public static String getJdbcDriver(){
        return PropsUtil.getString(CONFIG_PROPERS,ConfigConstant.JDBC_DRIVER);
    }

    /**
     * 获取jdbc url
     * @return jdbc url
     */
    public static  String getJdbcUrl(){
        return PropsUtil.getString(CONFIG_PROPERS,ConfigConstant.JDBC_URL);
    }

    /**
     * 获取jdbc用户名
     * @return
     */
    public static String getJdbcUerName() {
        return PropsUtil.getString(CONFIG_PROPERS,ConfigConstant.JDBC_USERNAME);
    }

    /**
     * 获取jdbc密码
     * @return
     */
    public static String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPERS,ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取应用基础包名
     * @return
     */
    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPERS,ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取应jsp路径
     * @return
     */
    public static String getAppJspPath() {
        return PropsUtil.getString(CONFIG_PROPERS,ConfigConstant.APP_JSP_PATH);
    }

    /**
     * 获取应用静态资源路径
     * @return
     */
    public static String getAppAssetPath() {
        return PropsUtil.getString(CONFIG_PROPERS,ConfigConstant.APP_ASSET_PATH);
    }



}
