package com.liyiruo.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author liyiruo
 * @Description
 * @Date 2020/12/30 下午4:35
 */
public class NioFileChannel01 {
    public static void main(String[] args) throws IOException {
        String s="/Users/liyiruo/Desktop/nio-data.txt";
        File file = new File(s);
        FileInputStream fileInputStream = new FileInputStream(file);
        //从流里获取通道
        FileChannel fileChannel = fileInputStream.getChannel();
        //声明一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        //通道的数据读取到buffer
        fileChannel.read(byteBuffer);
        //将byteBuffer里的数据转化为数组
        System.out.println(new String(byteBuffer.array()));
    }
}
