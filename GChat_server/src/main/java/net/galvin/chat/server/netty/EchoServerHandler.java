package net.galvin.chat.server.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.galvin.chat.comm.pojo.Message;
import net.galvin.chat.server.biz.service.MessageService;
import net.galvin.chat.server.biz.service.impl.MessageServiceImpl;

/**
 * Created by galvin on 17-5-4.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private MessageService messageService = MessageServiceImpl.get();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        SessionManager.put(message.getFromUser(),ctx.channel());
        messageService.process(message);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }
}
