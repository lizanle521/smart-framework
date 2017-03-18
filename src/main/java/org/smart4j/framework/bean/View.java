package org.smart4j.framework.bean;

import java.util.Map;

/**
 * 视图对象
 *
 * Created by Administrator on 2017/3/18.
 */
public class View {

    private String path;
    /**
     * model对象
     */
    private Map<String,Object> model;

    public View(String path) {
        this.path = path;
    }

    public View addModle(String key,Object value){
        model.put(key,value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
