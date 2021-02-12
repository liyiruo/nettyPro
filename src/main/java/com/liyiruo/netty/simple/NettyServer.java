package com.liyiruo.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author liyiruo
 * @Description
 * @Date 2021/2/5 下午9:50
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {

        //创建bossGroup 和workGroup
        //创建2个线程组bossGroup 和  workGroup
        //bossGroup这是处理连接请求 work 处理业务
        //2个都是无限循环
        //bossGroup 和 workGroup 含有子线程（ NioEventLoopGroup）的数量 默认cpu和数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            //使用链式编程来进行设置
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("服务器is ready……");
            //启动服务器
            ChannelFuture cf = bootstrap.bind(6668).sync();
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
