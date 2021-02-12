package com.liyiruo.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author liyiruo
 * @Description
 * @Date 2021/2/12 上午9:13
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("My HttpServerCodec", new HttpServerCodec());
        //增加一个自定义的handle
        pipeline.addLast("MyTestHttpServerHandle", new TestHttpServerHandle());
    }
}
