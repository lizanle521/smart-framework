package com.smart4j.framework.nioserver;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

/**
 * 用户向客户端发送消息
 * Created by lizanle on 2017/4/16.
 */
public class Writer implements Runnable {
    private static List<SelectionKey> pool = new LinkedList<SelectionKey>();
    private static Notifier notifier = Notifier.getInstance();
    public Writer() {

    }

    /**
     * 发送线程主控服务方法，负责调度整个处理过程
     */
    public void run() {
        while (true) {
            try {
                SelectionKey key;
                synchronized (pool) {
                    while (pool.isEmpty()){
                        pool.wait();
                    }
                    key = pool.remove(0);
                }
                // 处理写事件
                write(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理向客户端发送数据
     * @param key
     */
    public void write(SelectionKey key) {
        try {
            SocketChannel channel = (SocketChannel) key.channel();
            Response response = new Response(channel);

            //触发onwriter事件
            notifier.fireOnWrite((Request) key.attachment(),response);
            //关闭
            channel.finishConnect();
            channel.socket().close();
            channel.close();

            notifier.fireOnClosed((Request)key.attachment());
        } catch (Exception e) {
            notifier.fireOnError("error in writer :" + e.getMessage());
        }
    }

    /**
     * 处理客户请求,管理用户的联结池,并唤醒队列中的线程进行处理
     */
    public static void processRequest(SelectionKey key) {
        synchronized (pool) {
            pool.add(pool.size(), key);
            pool.notifyAll();
        }
    }

}
