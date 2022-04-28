package com.pke.netty.pack;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author pke
 * @data 2022/4/28 16:13
 */
@Data
@Accessors(chain = true)
public class LoginRequestPacket extends Packet{
    private String userId;
    private String userName;
    private String password;
    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
