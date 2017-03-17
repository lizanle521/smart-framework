package org.smart4j.framework.bean;

import org.smart4j.framework.util.CastUtil;
import org.smart4j.framework.util.ClassUtil;

import java.util.Map;

/**
 * 请求参数对象
 * Created by Administrator on 2017/3/18.
 */
public class Param {

    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public long getLong(String name){
        return CastUtil.castLong((String) paramMap.get(name));
    }

    public int getInt(String name){
        return CastUtil.castInt((String) paramMap.get(name));
    }

    public boolean getBoolean(String name){
        return CastUtil.castBoolean((String) paramMap.get(name));
    }

    public String getString(String name){
        return paramMap.get(name).toString();
    }

    public Map<String,Object> getParamMap(){
        return paramMap;
    }
}
