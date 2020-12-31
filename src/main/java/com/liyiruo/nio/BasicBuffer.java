package com.liyiruo.nio;

import java.nio.IntBuffer;

/**
 * @author liyiruo
 * @Description
 * @Date 2020/12/28 下午11:28
 */
public class BasicBuffer {
    public static void main(String[] args) {
        System.out.println("begin");
        //buffer 的使用（简单说明）
        IntBuffer intBuffer=IntBuffer.allocate(5);
        //想buffer中存放数据
       // intBuffer.put(10);
        for (int i = 0; i <intBuffer.capacity() ; i++) {
            intBuffer.put(i * 2);
        }
        System.out.println(intBuffer.hasRemaining());
        System.out.println(intBuffer.get(1));

        intBuffer.flip();

        //如果从BUFFER去数据
        while(intBuffer.hasRemaining()){
            //System.out.println("while");
            System.out.println(intBuffer.get());
            System.out.println("====>"+intBuffer.limit());
        }

    }
}
