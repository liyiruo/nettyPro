package com.liyiruo.nio.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author liyiruo
 * @Description
 * @Date 2021/2/1 上午8:47
 */
public class OldIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(7001);
        while (true) {
            Socket accept = socket.accept();
            DataInputStream dataInputStream = new DataInputStream(accept.getInputStream());
            try {
                byte[] bytes = new byte[1024];
                while (true){
                    int count = dataInputStream.read(bytes, 0, bytes.length);
                    if (count==-1){
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
/*
int le=0;
 byte[] bytes = new byte[1024];
while((le=dataInputStream.read())!=-1){
le+=le;
}
 */