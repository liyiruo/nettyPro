package com.liyiruo.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author liyiruo
 * @Description
 * @Date 2020/12/31 下午4:52
 */
public class NioFileChannel04 {
    public static void main(String[] args) throws IOException {
        String out = "/Users/liyiruo/Desktop/2.jpg";
        String in = "/Users/liyiruo/Desktop/1.jpg";

        FileInputStream fileInputStream = new FileInputStream(in);
        FileOutputStream fileOutputStream = new FileOutputStream(out);

        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        outputStreamChannel.transferFrom(inputStreamChannel, 0, inputStreamChannel.size());

        outputStreamChannel.close();
        inputStreamChannel.close();
        fileInputStream.close();
        fileOutputStream.close();

    }
}
