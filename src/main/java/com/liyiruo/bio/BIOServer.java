package com.liyiruo.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liyiruo
 * @Description
 * @Date 2020/12/27 下午10:15
 */
public class BIOServer {
    public static void main(String[] args) throws IOException {
        System.out.println("服务器启动了*****");
        //线程池机制
        //创建一个线程池
        //如果有客户端连接，就创建一个线程池，与之通讯
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        //创建serverSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        while (true) {
            final Socket socket = serverSocket.accept();
            newCachedThreadPool.execute(() -> {
                //通讯
                handler(socket);
            });
        }
    }

    //编写一个handler方法，和客户端通讯
    public static void handler(Socket socket) {
        System.out.println("接受到一个客户端==》threadId" + Thread.currentThread().getId() + "threadName==》" + Thread.currentThread().getName());
        try {
            byte[] bytes = new byte[1024];
            //通过socket获取一个输入流
            final InputStream inputStream = socket.getInputStream();
            while (true) {
                System.out.println("消息来自于：" + Thread.currentThread().getId() + "threadName==》" + Thread.currentThread().getName());
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
