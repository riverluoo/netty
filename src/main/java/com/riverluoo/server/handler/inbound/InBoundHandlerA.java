package com.riverluoo.server.handler.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author wangyang
 * @since 2019-04-04 11:07
 */
public class InBoundHandlerA  extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandlerA: "+ msg);
        super.channelRead(ctx, msg);
    }
}
