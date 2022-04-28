package com.pke.netty.handler;

import com.pke.netty.pack.LoginRequestPacket;
import com.pke.netty.pack.LoginResponsePacket;
import com.pke.netty.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.Instant;
import java.util.UUID;

/**
 *
 * 登录回复处理器
 * @author pke
 * @data 2022/4/28 16:35
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(Instant.now().toString() + ": client login...");
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString())
                            .setUserName("pke")
                            .setPassword("password");
        ctx.writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponePacket) throws Exception {
        if (loginResponePacket.isSuccess()){
            LoginUtil.markAsLogin(channelHandlerContext.channel());
            System.out.println(Instant.now().toString()+": 客户端登录成功");
        }else{
            System.out.println(Instant.now().toString()+": 客户端登录失败，原因：" + loginResponePacket.getReason());
        }
    }
}
