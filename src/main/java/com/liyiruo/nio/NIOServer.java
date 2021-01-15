package com.liyiruo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author liyiruo
 * @Description
 * @Date 2021/1/1 上午11:52
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        //创建serverSocket -> serverSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //创建一个selector对象
        Selector selector = Selector.open();
        //绑定一个端口6666，在服务端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把serverSocketChannel 注册到selector 关心 时间为
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待客户端连接
        while (true) {
            if (selector.select(1000) == 0) {
                //这里等待一秒
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }
            //如果返回的>0，就获取到相关的selectKey集合
            //1。如果返回的大于0，表示获取到相关的selectionKey集合
            //2。selector.selectedKeys()返回关注事件的集合
            //通过selectKey获取反向通道
            Set<SelectionKey> selectionKeySets = selector.selectedKeys();
            //遍历集合
            Iterator<SelectionKey> iterator = selectionKeySets.iterator();

            while (iterator.hasNext()) {
                //获取到selectKey
                SelectionKey selectionKey = iterator.next();
                //根据key对应的通道f发生的事件做出相应处理
                if (selectionKey.isAcceptable()) {
                    //该客户端生成一个socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功 生成一个socketChannel " +
                            socketChannel.hashCode());

                    socketChannel.configureBlocking(false);
                    //将socketChannel 注册到selector，关注事件为OP_READ,同时给socketChannel关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                }

                if (selectionKey.isReadable()) {
                    //通过key反向获取到对应channel
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(buffer);
                    System.out.println("from 客户端  " + new String(buffer.array()));
                }
                iterator.remove();

            }
        }


    }
}
