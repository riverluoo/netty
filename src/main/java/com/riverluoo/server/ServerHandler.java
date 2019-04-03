package com.riverluoo.server;

import com.riverluoo.protocol.Packet;
import com.riverluoo.protocol.PacketCodeC;
import com.riverluoo.protocol.request.LoginRequestPacket;
import com.riverluoo.protocol.request.MessageRequestPacket;
import com.riverluoo.protocol.response.LoginResponsePacket;
import com.riverluoo.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @author wangyang
 * @since 2019-04-03 11:09
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);

        if(packet instanceof LoginRequestPacket){
            System.out.println(new Date()+" : 收到客户登录请求");
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            if(valid(loginRequestPacket)){
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date()+" : 登录成功");
            }else {
                loginResponsePacket.setReason("账户密码错误");
                loginResponsePacket.setSuccess(false);
                System.out.println(new Date()+" : 登录失败");
            }

            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }else if(packet instanceof MessageRequestPacket){
            // 消息
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(new Date()+" : 收到客户端消息"+messageRequestPacket.getMessage());
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复 : "+ messageRequestPacket.getMessage());
            ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(byteBuf);
        }


    }

    private boolean valid(LoginRequestPacket loginRequestPacket){
        return true;
    }
}
