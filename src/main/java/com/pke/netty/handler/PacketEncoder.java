package com.pke.netty.handler;

import com.pke.netty.pack.Packet;
import com.pke.netty.util.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author pke
 * @data 2022/4/28 22:03
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        PacketCodeC.getInstance().encode(byteBuf,packet);
    }
}
