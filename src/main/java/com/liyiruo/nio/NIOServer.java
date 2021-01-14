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

        serverSocketChannel.configureBlocking(false);
        //把serverSocketChannel 注册到selector 关心 时间为
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待
        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }

            Set<SelectionKey> selectionKeySets = selector.selectedKeys();

            //遍历集合
            Iterator<SelectionKey> iterator = selectionKeySets.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    socketChannel.register(selector, SelectionKey.OP_ACCEPT, ByteBuffer.allocate(1024));
                }
                if (selectionKey.isReadable()) {
                    //通过key反向获取到对应channel

                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(buffer);
                    System.out.println("from 客户端 "+new String(buffer.array()));
                }
            }
        }


    }
}
