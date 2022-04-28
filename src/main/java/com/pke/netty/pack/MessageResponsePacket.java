package com.pke.netty.pack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pke
 * @data 2022/4/28 17:18
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponsePacket extends Packet{
    private String message;
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
