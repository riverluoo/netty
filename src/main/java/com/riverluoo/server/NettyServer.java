package com.riverluoo.server;

import com.riverluoo.codec.PacketDecoder;
import com.riverluoo.codec.PacketEncode;
import com.riverluoo.server.handler.AuthHandler;
import com.riverluoo.server.handler.LifeCycleHandler;
import com.riverluoo.server.handler.LoginRequestHandler;
import com.riverluoo.server.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

/**
 *
 *  1： 线程模型
 *  2： IO模型
 *  3： 连接读写处理逻辑
 *
 * @author wangyang
 * @since 2019-03-21 14:36
 */
public class NettyServer {

    private static final int PORT = 7000;

    public static void main(String[] args) {
        // 监听端口 接收新连接的线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();

        // 处理每一条连接的数据的请求的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 引导类
        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        final AttributeKey<Object> clientKey = AttributeKey.newInstance("clientKey");
        serverBootstrap
                // 两大线程组
                .group(bossGroup, workerGroup)
                // 指定服务端类型为NIO
                .channel(NioServerSocketChannel.class)
                // 给NioServerSocketChannel 维护一个map属性
                .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                // 可以给每一条连接指定自定义属性，然后后续我们可以通过channel.attr()取出该属性。
                .childAttr(clientKey, "clientValue")
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                // 定义后续每条连接的读写
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                     //  nioSocketChannel.pipeline().addLast(new ServerHandler());
//                        nioSocketChannel.pipeline().addLast(new InBoundHandlerA());
////                        nioSocketChannel.pipeline().addLast(new InBoundHandlerB());
////                        nioSocketChannel.pipeline().addLast(new InBoundHandlerC());
////
////                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerA());
////                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerB());
////                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerC());
                        nioSocketChannel.pipeline().addLast(new LifeCycleHandler());
                        nioSocketChannel.pipeline().addLast(new PacketDecoder());
                        nioSocketChannel.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        nioSocketChannel.pipeline().addLast(AuthHandler.INSTANCE);
                        nioSocketChannel.pipeline().addLast(MessageRequestHandler.INSTANCE);
                        nioSocketChannel.pipeline().addLast(new PacketEncode());

                    }
                });

        bind(serverBootstrap, PORT);

    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
                bind(serverBootstrap, port + 1);
            }
        });
    }

}
