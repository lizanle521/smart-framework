package org.smart4j.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流处理工具类
 * Created by Administrator on 2017/3/20.
 */
public class StreamUtil {
    public static String getString(InputStream stream) {
        String line = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuffer sb = new StringBuffer();
        try {
            while ((line = reader.readLine()) != null){
                    sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  sb.toString();
    }
}
