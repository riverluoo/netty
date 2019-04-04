package com.riverluoo.client.handler;

import com.riverluoo.protocol.response.LoginResponsePacket;
import com.riverluoo.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * @author wangyang
 * @since 2019-04-04 15:33
 */
public class LoginResponseHandler  extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 创建登录对象
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setUserId(UUID.randomUUID().toString());
        loginResponsePacket.setUserName("jurry");
        loginResponsePacket.setPassWord("pwd");

        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if(msg.isSuccess()){
            System.out.println(new Date() +" :  客户端登录成功");
            LoginUtil.markAsLogin(ctx.channel());
        }else {
            System.out.println(new Date() + " : 客户端登录失败 "+ msg.getReason());
        }

    }
}
