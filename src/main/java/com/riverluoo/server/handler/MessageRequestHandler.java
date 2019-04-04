package com.riverluoo.server.handler;

import com.riverluoo.protocol.request.MessageRequestPacket;
import com.riverluoo.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author wangyang
 * @since 2019-04-04 15:51
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        System.out.println(new Date() + " : "+ msg.getMessage());
        messageResponsePacket.setMessage("服务端回复 "+ msg.getMessage());

        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
