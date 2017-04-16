package com.smart4j.framework.nioserver;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于读取客户端数据
 * Created by lizanle on 2017/4/16.
 */
public class Reader implements Runnable {
    private static List<SelectionKey> pool = new ArrayList<SelectionKey>();
    private static Notifier notifier = Notifier.getInstance();
    public Reader () {

    }
    public void run() {
        while(true) {
            try{
                SelectionKey key;
                synchronized (pool) {
                    while (pool.isEmpty()){
                        pool.wait();
                    }
                    key = pool.remove(0);
                }
                //读取数据
                read(key);
            }catch (Exception e){
                continue;
            }finally {

            }
        }
    }

    private static int BUFF_SIZE = 1024;

    /**
     * 读取客户端发出的请求数据
     * @param socketChannel 套接通道
     * @return
     * @throws Exception
     */
    public static  byte[] readRequest(SocketChannel socketChannel ) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(BUFF_SIZE);
        int off = 0;
        int r = 0;
        byte[] data = new byte[BUFF_SIZE * 10];
        while (true) {
            buffer.clear();
            r = socketChannel.read(buffer);
            if(r == -1 ){
                break;
            }
            byte[] array = buffer.array();
            System.arraycopy(array,0,data,off,r);
        }
        byte[] req = new byte[off];
        System.arraycopy(data,0,req,0,off);
        return  req;
    }

    /**
     * 处理连接数据读取
     * @param key
     */
    public void read(SelectionKey key) {
        try {
            SocketChannel sc = (SocketChannel) key.channel();
            byte[] clientdata = readRequest(sc);
            Request request  = (Request)key.attachment();
            // 触发onread
            notifier.fireOnRead(request);
            //提交主线程进行写处理
            Server.processWriteRequest(key);
        } catch (Exception e){
            notifier.fireOnError("error in reader :" + e.getMessage());
        }
    }

    /**
     * 处理客户请求，管理用户的连接池，并唤醒队列中的线程进行处理
     * @param key
     */
    public static void processRequest(SelectionKey key) {
        synchronized (pool) {
            pool.add(pool.size(),key);
            pool.notifyAll();
        }
    }

    /**
     * 数组扩容
     * @param src 源数据
     * @param size 扩容的增加量
     * @return
     */
    public static  byte[] grow(byte[] src,int size) {
        byte[] temp = new byte[src.length + size];
        System.arraycopy(src,0,temp,0,src.length);
        return  temp;
    }
}
