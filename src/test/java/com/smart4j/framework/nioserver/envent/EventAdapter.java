package com.smart4j.framework.nioserver.envent;

import com.smart4j.framework.nioserver.Request;
import com.smart4j.framework.nioserver.Response;

/**
 * 适配器模式
 * Created by lizanle on 2017/4/16.
 */
public abstract class EventAdapter implements ServerListener {
    public void onError(String error) {

    }

    public void onAccept() throws Exception {

    }

    public void onAccepted(Request request) throws Exception {

    }

    public void onRead(Request request) throws Exception {

    }

    public void onWrite(Request request, Response response) throws Exception {

    }

    public void onClosed(Request request) throws Exception {

    }
}
