package com.riverluoo.codec;

import com.riverluoo.protocol.Packet;
import com.riverluoo.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author wangyang
 * @since 2019-04-04 15:28
 */
public class PacketEncode extends MessageToByteEncoder<Packet> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodeC.INSTANCE.encode(out,msg);
    }
}
