package net.galvin.chat.client.start;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import net.galvin.chat.comm.codec.MsgpackDecoder;
import net.galvin.chat.comm.codec.MsgpackEncoder;
import net.galvin.chat.comm.pojo.Message;

/**
 * Created by Administrator on 2017/5/8.
 */
public class GChatClient {

    private ChannelFuture channelFuture;

    public void launch(){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
                            ch.pipeline().addLast("msgpack decoder",new MsgpackDecoder());
                            ch.pipeline().addLast("frameEncoderr",new LengthFieldPrepender(2));
                            ch.pipeline().addLast("msgpack encoder",new MsgpackEncoder());
                            ch.pipeline().addLast(new EchoClienthandler());
                        }
                    });
            channelFuture = b.connect(AppClientConfig.HOST,AppClientConfig.PORT);
        }catch (Exception e){
            e.printStackTrace();
            if(group != null){
                group.shutdownGracefully();
            }
        }
    }

    public void send(Message message){
        this.channelFuture.channel().writeAndFlush(message);
    }

}
