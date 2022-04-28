package com.pke.netty.pack;

import lombok.Data;

/**
 * @author pke
 * @data 2022/4/28 16:14
 */

@Data
public abstract class Packet {
    public Byte version = 1;
    public abstract Byte getCommand();
}
