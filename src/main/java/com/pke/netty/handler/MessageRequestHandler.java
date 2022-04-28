package com.pke.netty.handler;

import com.pke.netty.pack.MessageRequestPacket;
import com.pke.netty.pack.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.Instant;

/**
 * @author pke
 * @data 2022/4/28 17:19
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        System.out.println(Instant.now().toString() + ": 收到客户端消息：" + messageRequestPacket.getMessage());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket("服务端回复【" + messageRequestPacket.getMessage() + "】");
        channelHandlerContext.writeAndFlush(messageResponsePacket);
    }
}
