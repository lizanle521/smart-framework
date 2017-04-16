package com.smart4j.framework.nioserver;

import com.smart4j.framework.nioserver.envent.EventAdapter;

/**
 * 服务端事件处理器
 * Created by lizanle on 2017/4/16.
 */
public class ServerHandler extends EventAdapter {
    @Override
    public void onError(String error) {
        System.out.println("#OnError:" + error);
    }

    @Override
    public void onAccept() throws Exception {
        System.out.println("#OnAccept");
    }

    @Override
    public void onAccepted(Request request) throws Exception {
        System.out.println("#OnAccepted");
    }

    @Override
    public void onRead(Request request) throws Exception {
        super.onRead(request);
    }

    @Override
    public void onWrite(Request request, Response response) throws Exception {
        super.onWrite(request, response);
    }

    @Override
    public void onClosed(Request request) throws Exception {
        super.onClosed(request);
    }
}
