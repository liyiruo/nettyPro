package com.liyiruo.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author liyiruo
 * @Description
 * @Date 2020/12/31 下午4:31
 */
public class NioFileChannel03 {
    public static void main(String[] args) throws IOException {
        String out="/Users/liyiruo/Desktop/nio-data.txt";
        String in="/Users/liyiruo/Desktop/nio-data3.txt";
        FileInputStream fileInputStream = new FileInputStream(in);
        FileOutputStream fileOutputStream = new FileOutputStream(out);

        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true){
            buffer.clear();
            int read = inputStreamChannel.read(buffer);
            if (read == -1) {
                break;
            }
            buffer.flip();
            fileOutputStreamChannel.write(buffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
