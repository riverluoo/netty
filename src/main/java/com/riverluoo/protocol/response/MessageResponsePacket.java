package com.riverluoo.protocol.response;

import com.riverluoo.protocol.Packet;
import lombok.Data;

import static com.riverluoo.protocol.command.Command.MESSAGE_RESPONSE;

/**
 * @author wangyang
 * @since 2019-03-21 16:48
 */
@Data
public class MessageResponsePacket extends Packet {

    private String message;


    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
