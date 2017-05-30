package net.galvin.chat.client.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.galvin.chat.comm.pojo.Message;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017/5/8.
 */
public class EchoClienthandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        System.out.println("Netty Client: "+message.getContent());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }
}
