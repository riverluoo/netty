package com.riverluoo.client.handler;

import com.riverluoo.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author wangyang
 * @since 2019-04-04 15:33
 */
public class MessageResponseHandler  extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        System.out.println(new Date() +" : " + msg.getMessage());
    }
}
