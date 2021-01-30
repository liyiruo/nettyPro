package com.liyiruo.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author liyiruo
 * @Description
 * @Date 2021/1/19 上午10:13
 */
public class GroupChatClient {
    private final String HOST = "localhost";
    private final int PORT = 6667;
    private final Selector selector;
    private SocketChannel socketChannel;
    private final String username;

    public GroupChatClient() throws IOException {
        this.selector = Selector.open();
        //连接服务器
        this.socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        //设置非阻塞
        this.socketChannel.configureBlocking(false);
        //将Channel注册到selector
        this.socketChannel.register(selector, SelectionKey.OP_READ);
        this.username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + "is ok……");
    }

    /**
     * 向服务器发送消息
     *
     * @param info
     */
    public void sendInfo(String info) {
        info = username + " 说：" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readInfo() {
        try {
            int readChannels = selector.select();
            if (readChannels > 0) {//有事件发生的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        //得到相关的通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        //得到一个buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //读取
                        sc.read(buffer);
                        //把读到的数据转化为字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                }
                iterator.remove();
            } else {
                System.out.println("没有可用的通道……");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        GroupChatClient client = new GroupChatClient();

        new Thread(() -> {
            while (true) {
                client.readInfo();
                //每隔3秒读一次
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //发送消息给服务器
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            client.sendInfo(s);
        }
    }
}
