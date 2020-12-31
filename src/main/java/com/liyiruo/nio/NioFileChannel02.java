package com.liyiruo.nio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author liyiruo
 * @Description
 * @Date 2020/12/30 下午4:47
 */
public class NioFileChannel02 {
    public static void main(String[] args) throws IOException {
        String s = "/Users/liyiruo/Desktop/nio-data0.txt";
        String str = "你好啊 这世界";
        File file = new File(s);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
