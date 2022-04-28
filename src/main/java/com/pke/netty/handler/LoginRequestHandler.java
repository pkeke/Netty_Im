package com.pke.netty.handler;

import com.pke.netty.pack.LoginRequestPacket;
import com.pke.netty.pack.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author pke
 * @data 2022/4/28 16:10
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("a client is connected.");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        LoginResponsePacket loginResponePacket = new LoginResponsePacket();
        loginResponePacket.setVersion(loginRequestPacket.getVersion());

        // 登录校验

        if (vaild(loginRequestPacket)){
            System.out.println("login valid success");
            // 校验成功
            loginResponePacket.setSuccess(true);
        }
        else{
            // 校验失败
            loginResponePacket.setSuccess(false).setReason("账号密码校验失败");
        }
        channelHandlerContext.writeAndFlush(loginResponePacket);
    }

    private boolean vaild(LoginRequestPacket loginRequestPacket){
        return true;
    }
}
