package com.riverluoo.protocol.response;

import com.riverluoo.protocol.Packet;
import lombok.Data;

import static com.riverluoo.protocol.command.Command.LOGIN_RESPONSE;

/**
 * @author wangyang
 * @since 2019-03-21 16:26
 */
@Data
public class LoginResponsePacket extends Packet {

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
