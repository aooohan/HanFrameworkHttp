package com.nullok.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * http服务器
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 16:09
 */
public class HttpServer {
    // 分发线程
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup();
    // 工作线程
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup();
    // 端口号
    private static final int PORT = 80;
    // 地址
    private static final String HOST = "127.0.0.1";
    // 启动器
    private static final ServerBootstrap serverBootstrap = new ServerBootstrap();
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(HttpServer.class);

    public static void run() {
        try {
            // 配置启动器
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(HOST,PORT).sync();
            channelFuture.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    logger.info("Netty服务器启动，地址: http://" + HOST + ":" + PORT);
                } else {
                    logger.error("Netty服务器启动失败");
                }
            });
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("Netty服务器启动失败，错误原因:{}", e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
