package com.riverluoo.server.handler;

import com.riverluoo.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author wangyang
 * @since 2019-04-10 15:47
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {

    public static final AuthHandler INSTANCE = new AuthHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!LoginUtil.hasLogin(ctx.channel())){
            ctx.channel().close();
        }else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx,msg);
        }

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
       if(LoginUtil.hasLogin(ctx.channel())){
           System.out.println("当前连接登录验证完毕");
       }else {
           System.out.println("无登录验证，强制关闭连接");
       }
    }
}
