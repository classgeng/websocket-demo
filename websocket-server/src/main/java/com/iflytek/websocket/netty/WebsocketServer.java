package com.iflytek.websocket.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.concurrent.ExecutorService;

/**
 * websocket服务，监听给定端口的ws请求
 * Created by xfgeng on 2018/5/18.
 */
public class WebsocketServer {

    /**
     * 启动netty监听ws端口
     * @param port
     * @throws Exception
     */
    public void start(int port) throws Exception {
        //netty IO主线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //netty 数据处理线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpResponseEncoder());
                            // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
                            pipeline.addLast(new HttpRequestDecoder());
                            // 用于处理HTTP报文转换
                            pipeline.addLast(new HttpObjectAggregator(65535));
                            // 用于处理大文件传输
                            pipeline.addLast(new ChunkedWriteHandler());
                            //websocket
                            pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
                            // 用于处理请求，执行请求拦截器
                            pipeline.addLast(new WebSocketRequestHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 1024)//设置TCP BACKLOG参数
                    .childOption(ChannelOption.SO_KEEPALIVE, true);//设置TCP KEEPALIVE参数
            // 绑定、监听端口
            ChannelFuture f = b.bind(port).sync();
            // 成功绑定到端口之后,给channel增加一个 管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程
            f.channel().closeFuture().sync();
        } finally {
            // 通道关闭时释放线程组资源
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }


}