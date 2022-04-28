package com.pke.netty.pack;

/**
 *
 * 命令参数
 * @author pke
 * @data 2022/4/28 16:11
 */
public interface Command {

    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;
    Byte MESSAGE_REQUEST = 3;
    Byte MESSAGE_RESPONSE = 4;
}
