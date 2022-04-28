package com.pke.netty;

import com.pke.netty.handler.*;
import com.pke.netty.serializer.Serializer;
import com.sun.javafx.iio.png.PNGImageLoader2;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import static jdk.nashorn.internal.objects.NativeFunction.bind;

/**
 * @author pke
 * @data 2022/4/28 22:25
 */
public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        serverBootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast(new PacketDecoder());
                        pipeline.addLast(new PacketEncoder());
                        pipeline.addLast(new LoginRequestHandler());
                        pipeline.addLast(new MessageRequestHandler());
                    }
                });
        serverBootstrap.option(ChannelOption.SO_BACKLOG,1024);
        bind(serverBootstrap,8080);
    }

    public static void bind(ServerBootstrap serverBootstrap, int port){
        ChannelFuture channelFuture = serverBootstrap.bind(port);
        channelFuture.addListener((future)->{
            if (future.isSuccess()){
                System.out.println("bind success at port" + port);
            }else{
                bind(serverBootstrap,port+1);
            }
        });
    }
}
