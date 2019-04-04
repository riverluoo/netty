package com.riverluoo.server.handler;

import com.riverluoo.protocol.request.LoginRequestPacket;
import com.riverluoo.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author wangyang
 * @since 2019-04-04 15:51
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        System.out.println(new Date() +": 收到客户端登录请求" );
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());
        if(this.vaild(msg)){

        }


    }

    private boolean vaild(LoginRequestPacket loginRequestPacket){
        return true;
    }


}
