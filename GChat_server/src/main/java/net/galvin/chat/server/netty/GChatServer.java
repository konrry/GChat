package net.galvin.chat.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import net.galvin.chat.comm.codec.MsgpackDecoder;
import net.galvin.chat.comm.codec.MsgpackEncoder;

/**
 * Created by galvin on 17-5-25.
 */
public class GChatServer {

    ChannelFuture channelFuture;

    /**
     * 启动
     */
    public void launch(){

        //初始化两个线程池
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,2,0,2))
                                    .addLast("frameEncoder",new LengthFieldPrepender(2))
                                    .addLast(new MsgpackDecoder())
                                    .addLast(new MsgpackEncoder())
                                    .addLast(new EchoServerHandler());
                        }
                    });
            channelFuture = bootstrap.bind(AppServerConfig.HOST,AppServerConfig.PORT);
        }catch (Exception e){
            e.printStackTrace();
            if(workerGroup != null){
                workerGroup.shutdownGracefully();
            }
            if(bossGroup != null){
                bossGroup.shutdownGracefully();
            }
        }

    }

}
