package com.riverluoo.protocol.request;

import com.riverluoo.protocol.Packet;
import lombok.Data;

import static com.riverluoo.protocol.command.Command.LOGIN_REQUEST;

/**
 * @author wangyang
 * @since 2019-03-21 16:33
 */
@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String userName;

    private String password;


    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
