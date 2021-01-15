package com.liyiruo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author liyiruo
 * @Description
 * @Date 2021/1/1 下午3:34
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        //得到一个通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        Selector selector = Selector.open();
        //提供服务器端的IP 和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        //连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作");
            }
        }
        //如果成功就发送数据
        String str = "你好，陌生人";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        //发送数据将buffer数据写入channel
        socketChannel.write(byteBuffer);
        System.in.read();

    }
}
