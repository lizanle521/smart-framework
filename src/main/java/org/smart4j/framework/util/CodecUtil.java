package org.smart4j.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

/**
 * 解码编码工具类
 * Created by Administrator on 2017/3/20.
 */
public class CodecUtil {
    public static String decodeUrl(String str){
        String decode = "";
        try {
             decode = URLDecoder.decode(str,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decode;
    }
}
