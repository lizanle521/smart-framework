package org.smart4j.framework.bean;

/**
 *  数据对象，其实是一个Map类型的键值对对象，可以在视图中根据键名取键值
 * Created by Administrator on 2017/3/18.
 */
public class Data {
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
