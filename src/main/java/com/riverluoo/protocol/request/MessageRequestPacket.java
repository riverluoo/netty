package com.riverluoo.protocol.request;

import com.riverluoo.protocol.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.riverluoo.protocol.command.Command.MESSAGE_REQUEST;

/**
 * @author wangyang
 * @since 2019-03-21 16:36
 */
@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {

    private String toUserId;

    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
