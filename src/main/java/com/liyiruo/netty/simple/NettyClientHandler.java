package com.liyiruo.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author liyiruo
 * @Description
 * @Date 2021/2/5 下午10:55
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    //通道就绪触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client==>" + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,server:🐱", CharsetUtil.UTF_8));
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器回复的消息：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址位：" + ctx.channel().remoteAddress());
    }
}
