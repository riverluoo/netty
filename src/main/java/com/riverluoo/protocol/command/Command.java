package com.riverluoo.protocol.command;

/**
 * @author wangyang
 * @since 2019-03-21 16:30
 */
public interface Command {

    Byte LOGIN_REQUEST=1;

    Byte LOGIN_RESPONSE=2;

    Byte MESSAGE_REQUEST=3;

    Byte MESSAGE_RESPONSE=4;

}
