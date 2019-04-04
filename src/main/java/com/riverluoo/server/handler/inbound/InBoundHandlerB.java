package com.riverluoo.server.handler.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author wangyang
 * @since 2019-04-04 14:49
 */
public class InBoundHandlerB  extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandlerB" + msg);
        super.channelRead(ctx, msg);
    }
}
