package com.nullok.server;

import com.nullok.server.handler.DispatcherRequestHandler;
import com.nullok.server.handler.DownloadFileHandler;
import com.nullok.server.handler.ParseRequestHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 16:19
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 获取处理管道
        ChannelPipeline pipeline = ch.pipeline();
        // 加入处理器
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new ParseRequestHandler());
        pipeline.addLast(new DispatcherRequestHandler());

//        pipeline.addLast(new DownloadFileHandler());
//        pipeline.addLast(new DemoHandler());
    }
}
