package net.galvin.chat.client.start;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.galvin.chat.comm.pojo.Message;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017/5/8.
 */
public class EchoClienthandler extends ChannelInboundHandlerAdapter {

    private AtomicInteger count = new AtomicInteger(1);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Message message = new Message("EchoClienthandler.channelActive.");
        ctx.write(message);
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        String msg2Send = "ClientMsg=="+count.getAndIncrement();
        System.out.println("Client Receive: "+message+", Send: "+msg2Send);
        if(count.get() <= 50){
            ctx.write(new Message(msg2Send));
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
