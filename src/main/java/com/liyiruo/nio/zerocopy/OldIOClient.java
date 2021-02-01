package com.liyiruo.nio.zerocopy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author liyiruo
 * @Description
 * @Date 2021/2/1 上午8:56
 */
public class OldIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 7001);
        String fileName = "/Users/liyiruo/Desktop/ShadowsocksX-NG-R8.dmg";
        FileInputStream fileInputStream = new FileInputStream(fileName);
        DataOutputStream dataInputStream = new DataOutputStream(socket.getOutputStream());
        byte[] bytes = new byte[4096];
        long readCount;
        long total = 0;
        long startTime = System.currentTimeMillis();
        while ((readCount = fileInputStream.read(bytes)) != -1) {
            total += readCount;
            dataInputStream.write(bytes);
        }
        System.out.println("发送总字节数： " + total + ", 耗时： " + (System.currentTimeMillis() - startTime));
        dataInputStream.close();
        fileInputStream.close();
        socket.close();

    }
}
