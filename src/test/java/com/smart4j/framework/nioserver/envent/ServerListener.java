package com.smart4j.framework.nioserver.envent;

import com.smart4j.framework.nioserver.Request;
import com.smart4j.framework.nioserver.Response;

/**
 * 服务器事件监听器
 * Created by lizanle on 2017/4/16.
 */
public interface ServerListener {
    /**
     * 服务器处理发生错误的时候触发本事件
     * @param error
     */
    public void onError(String error);

    /**
     * 当有客户端发来请求时触发本事件
     */
    public void onAccept() throws Exception;

    /**
     * 当服务端接受客户端请求后触发本事件
     * @param request 客户端请求
     */
    public void onAccepted(Request request) throws Exception;

    /**
     * 当客户端发来数据，并已被服务器控制线程正确读取时，触发该事件
     * @param request 客户端请求
     */
    public void onRead(Request request) throws Exception;

    /**
     * 当可以向客户端发送请求触发本事件
     * @param request 客户端请求
     */
    public void onWrite(Request request, Response response) throws Exception;

    /**
     * 当客户端与服务器结束连接后触发本事件
     * @param request 客户端请求
     */
    public void onClosed(Request request) throws Exception;
}
