package com.riverluoo.client;

import com.riverluoo.protocol.Packet;
import com.riverluoo.protocol.PacketCodeC;
import com.riverluoo.protocol.request.LoginRequestPacket;
import com.riverluoo.protocol.response.LoginResponsePacket;
import com.riverluoo.protocol.response.MessageResponsePacket;
import com.riverluoo.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

/**
 * @author wangyang
 * @since 2019-03-21 16:16
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() +" 客户端开始登陆");

        LoginRequestPacket loginRequestPacket=new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUserName("test");
        loginRequestPacket.setPassword("123456");

        // 编码
        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        ctx.channel().writeAndFlush(byteBuf);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if(packet instanceof LoginResponsePacket ){
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if(loginResponsePacket.isSuccess()){
                System.out.println(new Date()+" : 客户端登录成功!");
                LoginUtil.markAsLogin(ctx.channel());
            }else {
                System.out.println(new Date()+ "客户端登录失败 ,原因 :"+ loginResponsePacket.getReason());
            }
        }else if(packet instanceof MessageResponsePacket){
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date()+"： 收到服务端消息 : "+ messageResponsePacket.getMessage());
        }



    }
}
