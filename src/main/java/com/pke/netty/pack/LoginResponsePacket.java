package com.pke.netty.pack;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author pke
 * @data 2022/4/28 16:28
 */

@Data
@Accessors(chain = true)
public class LoginResponsePacket extends Packet{
    private boolean success;

    private String reason;
    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
