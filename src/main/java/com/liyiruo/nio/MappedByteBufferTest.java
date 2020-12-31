package com.liyiruo.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * @author liyiruo
 * @Description
 * @Date 2020/12/31 下午8:45
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/liyiruo/Desktop/nio-data.txt", "rw");
        FileChannel accessFileChannel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = accessFileChannel.map(MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) 'k');
        //mappedByteBuffer.put(5, (byte) 'y');
        randomAccessFile.close();
        System.out.println("修改成功～～");
    }
}
