package com.pke.netty.handler;

import com.pke.netty.pack.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.Instant;

/**
 * @author pke
 * @data 2022/4/28 17:32
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) throws Exception {
        String message = messageResponsePacket.getMessage();
        System.out.println(Instant.now().toString() + "收到服务端消息：" + message);
    }
}
