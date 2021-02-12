package com.liyiruo.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;

/**
 * @author liyiruo
 * @Description 自定义的handler
 * @Date 2021/2/5 下午10:16
 */
public class NettyServerHandler  extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程=》"+Thread.currentThread().getName());
        System.out.println("server ctx=" + ctx);
        Channel channel = ctx.channel();
        //双向链表
        ChannelPipeline pipeline = ctx.pipeline();
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息："+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址是" + ctx.channel().remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello客户端～",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
