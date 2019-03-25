package com.riverluoo.protocol.request;

import com.riverluoo.protocol.Packet;
import lombok.Data;

import static com.riverluoo.protocol.command.Command.MESSAGE_REQUEST;

/**
 * @author wangyang
 * @since 2019-03-21 16:36
 */
@Data
public class MessageRequestPacket extends Packet {

    private String message;


    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
