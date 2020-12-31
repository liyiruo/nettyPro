package com.liyiruo.nio;

import java.nio.ByteBuffer;

/**
 * @author liyiruo
 * @Description
 * @Date 2020/12/31 下午5:56
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer=ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            buffer.put((byte) i);
        }
        buffer.flip();
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }

        //设置为只读的以后，就只能读取，不能修改了。
       // readOnlyBuffer.put((byte) 100);

    }
}
