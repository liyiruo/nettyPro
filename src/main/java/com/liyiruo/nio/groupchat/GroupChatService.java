package com.liyiruo.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author liyiruo
 * @Description
 * @Date 2021/1/19 上午1:03
 */
public class GroupChatService {
    //定义相关的属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    public GroupChatService() {
        try {
            System.out.println("服务器已就绪……");
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false);
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听
     */
    public void listen() {
        try {
            while (true) {
                int count = selector.select();
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress() + " 上线了");
                        }
                        if (key.isReadable()) {
                            //处理读 todo 之前一直没有处理消息，是因为这里没有调用方法
                            readData(key);
                        }
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待……");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //发生异常处理
        }
    }

    private void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //根据count的值做处理
            if (count > 0) {
                String msg = new String(buffer.array());
                System.out.println("from客户端===》" + msg);
                //向其他客户端发送消息
                sendInfoToOtherClient(msg, channel);
            }
        } catch (Exception e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 转发消息
     *
     * @param msg
     * @param self
     * @throws IOException
     */
    private void sendInfoToOtherClient(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器发送消息中……");
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                SocketChannel dest = (SocketChannel) targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }
    }


    public static void main(String[] args) {
        GroupChatService service = new GroupChatService();
        service.listen();
    }
}
