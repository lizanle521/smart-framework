package com.smart4j.framework.nioserver;

import com.smart4j.framework.nioserver.envent.ServerListener;

import java.util.ArrayList;
import java.util.List;

/**
 *  事件触发器
 * Created by lizanle on 2017/4/16.
 */
public class Notifier {
    private static List<ServerListener> listeners = null;
    private static Notifier instance = null;

    /**
     * 内部类单例模式实现
     */
    private static class NotifierHolder {
        private static Notifier notifier = new Notifier();
    }
    private Notifier () {
        listeners = new ArrayList<ServerListener>();
    }

    public static Notifier getInstance() {
        return NotifierHolder.notifier;
    }

    public void addListener(ServerListener listener){
        synchronized (listeners) {
            if(!listeners.contains(listener)){
                listeners.add(listener);
            }
        }
    }

    public void fireOnAccept() throws Exception {
        for (int i = listeners.size() - 1; i >= 0; i--)
            ( (ServerListener) listeners.get(i)).onAccept();
    }

    public void fireOnAccepted(Request request) throws Exception {
        for (int i = listeners.size() - 1; i >= 0; i--)
            ( (ServerListener) listeners.get(i)).onAccepted(request);
    }

    void fireOnRead(Request request) throws Exception {
        for (int i = listeners.size() - 1; i >= 0; i--)
            ( (ServerListener) listeners.get(i)).onRead(request);

    }

    void fireOnWrite(Request request, Response response)  throws Exception  {
        for (int i = listeners.size() - 1; i >= 0; i--)
            ( (ServerListener) listeners.get(i)).onWrite(request, response);

    }

    public void fireOnClosed(Request request) throws Exception {
        for (int i = listeners.size() - 1; i >= 0; i--)
            ( (ServerListener) listeners.get(i)).onClosed(request);
    }

    public void fireOnError(String error) {
        for (int i = listeners.size() - 1; i >= 0; i--)
            ( (ServerListener) listeners.get(i)).onError(error);
    }
}
