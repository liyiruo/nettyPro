package com.liyiruo.nio;

import java.nio.ByteBuffer;

/**
 * @author liyiruo
 * @Description
 * @Date 2020/12/31 下午5:50
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        //类型化方式放入数据
        buffer.putInt(100);
        buffer.putLong(9);
        buffer.putChar('你');
        buffer.putShort((short) 4);

        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
    }
}
