package com.riverluoo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyang
 * @since 2019-03-21 15:18
 */
public class NettyClient {

    private static  final int MAX_RETRY=5;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap=new Bootstrap();

        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .attr(AttributeKey.newInstance("clientName"),"nettyClinet")
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        System.out.println("initChannel");
                    }
                });

        connect(bootstrap, "127.0.0.1", 7000, MAX_RETRY);
    }


    private static void connect(Bootstrap bootstrap,String host,int port,int retry){
        bootstrap.connect(host,port).addListener(future -> {
            if(future.isSuccess()){
                System.out.println("连接成功");
            }else if(retry ==0 ){
                System.out.println("尝试次数已用完");
            }else {
                int order =(MAX_RETRY - retry)+1;
                int delay = 1 << order;
                System.out.println(new Date() + " 第"+order + "次重连");
                bootstrap.config().group().schedule(()-> connect(bootstrap,host,port,retry-1),delay, TimeUnit.SECONDS);
            }
        });
    }



}
