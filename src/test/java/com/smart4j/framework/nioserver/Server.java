package com.smart4j.framework.nioserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * Created by lizanle on 2017/4/16.
 */
public class Server implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static List<SelectionKey> pool = new LinkedList<SelectionKey>();// 回应池
    protected Notifier notifier;
    private static int MAX_THREADS = 4;
    private   ServerSocketChannel ssc ;
    private static Selector selector;

    private int port;
    public Server(int port) throws Exception {
        notifier = Notifier.getInstance();
        for (int i = 0; i < MAX_THREADS; i++) {

        }
        this.port = port;
        //创建选择器
        selector = Selector.open();
        // 创建监听信道
         ssc = ServerSocketChannel.open();
         //与本地端口绑定
        ssc.socket().bind(new InetSocketAddress(port));
        //是否阻塞
        ssc.configureBlocking(false);
        // 注册选择器，只有非阻塞信道才能注册选择器，并在注册过程中指出该信道可以进行Acccept操作
        ssc.register(selector, SelectionKey.OP_ACCEPT);
    }
    public void run() {
        if(logger.isInfoEnabled()){
            logger.info("server started ,server listening on port at {}" + port);
        }
        //监听
        while(true){
            try {
                if(selector.select() > 0){
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> it = keys.iterator();
                    while(it.hasNext()){
                        SelectionKey key = it.next();
                        it.remove();
                        //处理io事件
                        boolean accept = SelectionKey.OP_ACCEPT == (key.readyOps() & SelectionKey.OP_ACCEPT);
                        boolean read = SelectionKey.OP_READ == (key.readyOps() & SelectionKey.OP_READ);

                        boolean write = SelectionKey.OP_WRITE == (key.readyOps() & SelectionKey.OP_WRITE);
                        if(accept){
                            // 处理这个新的连接
                            ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                            notifier.fireOnAccept();

                            SocketChannel sc = ssc.accept();
                            sc.configureBlocking(false);

                            // 触发接受连接事件
                            Request request = new Request(sc);
                            notifier.fireOnAccepted(request);

                            //注册读操作
                            sc.register(selector,SelectionKey.OP_READ );
                        }else if(read){
                            Reader.processRequest(key);
                            key.cancel();
                        }else if(write){
                            Writer.processRequest(key);
                            key.cancel();
                        }
                    }
                }else {
                    addRegister();
                }
            } catch (Exception e) {
               notifier.fireOnError("error in server :" + e.getMessage());
               continue;
            } finally {
            }
        }
    }

    /**
     * 添加新的通道注册
     */
    public void addRegister () {
        synchronized (pool) {
            while (!pool.isEmpty()) {
                SelectionKey key = pool.remove(0);
                SocketChannel sc = (SocketChannel) key.channel();
                try {
                    sc.register(selector,SelectionKey.OP_WRITE,key.attachment());
                } catch (ClosedChannelException e) {
                    try {
                        sc.finishConnect();
                        sc.socket().close();
                        sc.close();
                        notifier.fireOnClosed((Request)key.attachment());
                    } catch (Exception e1) {
                        notifier.fireOnError("error in add register:" + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * 提交新的客户端写请求于主服务线程池中
     * @param key
     */
    public static    void processWriteRequest(SelectionKey key) {
        synchronized (pool) {
            pool.add(pool.size(),key);
            pool.notifyAll();
        }
        selector.wakeup();
    }
}
