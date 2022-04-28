package com.pke.netty;


import com.pke.netty.handler.*;
import com.pke.netty.pack.MessageRequestPacket;
import com.pke.netty.util.LoginUtil;
import com.pke.netty.util.PacketCodeC;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.local.LocalEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author pke
 * @data 2022/4/28 22:06
 */
public class NettyClient {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup work = new NioEventLoopGroup(1);
        bootstrap.group(work)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast(new PacketDecoder());
                        pipeline.addLast(new PacketEncoder());
                        pipeline.addLast(new LoginResponseHandler());
                        pipeline.addLast(new MessageResponseHandler());
                    }
                });

        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.TCP_NODELAY,true)
                .option(ChannelOption.SO_KEEPALIVE,true);

        connect(bootstrap, "127.0.0.1",8080,3);
    }

    public static void connect(Bootstrap bootstrap,String host, int port, int retry){
        bootstrap.connect(host,port).addListener((future)->{
            if (future.isSuccess()){
                System.out.println("connect successs! enjoy it");
                Channel channel = ((ChannelFuture) future).channel();

                // 连接成功之后，启动控制台线程
                startConsoleThread(channel);

            }else{
                System.out.println("connect failed retrying...");
                if (retry > 0){
                    connect(bootstrap,host,port,retry-1);
                }
            }
        });

    }

    private static void startConsoleThread(Channel channel) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()->{
            while (!Thread.interrupted()){
                if (LoginUtil.hasLogin(channel)){
                    System.out.println("输入消息发送到服务器：");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();
                    MessageRequestPacket requestPacket = new MessageRequestPacket(line);
                    channel.writeAndFlush(requestPacket);
                }
            }
        });
    }
}
