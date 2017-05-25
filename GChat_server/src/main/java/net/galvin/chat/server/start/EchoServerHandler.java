package net.galvin.chat.server.start;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.galvin.chat.comm.pojo.Message;

/**
 * Created by galvin on 17-5-4.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        System.out.println("Server Receive: "+message.getContent()+", and Send: "+"Server has receive "+message.getContent());
        ctx.write(new Message("Server has receive "+message.getContent()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
