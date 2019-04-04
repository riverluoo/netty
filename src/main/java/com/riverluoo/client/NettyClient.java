package com.riverluoo.client;

import com.riverluoo.protocol.PacketCodeC;
import com.riverluoo.protocol.request.MessageRequestPacket;
import com.riverluoo.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 *  1：创建一个引导类
 *  2：指定线程模型
 *  3：IO模型
 *  4： 连接读写处理逻辑
 *
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
                // 绑定自定义的属性
                .attr(AttributeKey.newInstance("clientName"),"nettyClinet")
                // 设置tcp 连接的属性
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                // pipeline 采用责任链模式
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                       ch.pipeline().addLast(new ClientHandler());
                    }
                });

        connect(bootstrap, "127.0.0.1", 7000, MAX_RETRY);
    }


    private static void connect(Bootstrap bootstrap,String host,int port,int retry){
        bootstrap.connect(host,port).addListener(future -> {
            if(future.isSuccess()){
                System.out.println("连接成功");
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
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


    private static void startConsoleThread(Channel channel){
        new Thread(() ->{
            while (!Thread.interrupted()){
                if(LoginUtil.hasLogin(channel)){
                    System.out.println("请输入消息到服务端");
                    Scanner scanner = new Scanner(System.in);
                    String nextLine = scanner.nextLine();

                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(nextLine);
                    ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(), packet);
                    channel.writeAndFlush(byteBuf);
                }

            }
        }).start();
    }

}
