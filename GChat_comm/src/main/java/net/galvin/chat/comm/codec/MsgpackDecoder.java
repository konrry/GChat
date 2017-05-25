package net.galvin.chat.comm.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.galvin.chat.comm.pojo.Message;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        final byte[] array;
        final int length = byteBuf.readableBytes();
        array = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(),array,0,length);
        MessagePack messagePack = new MessagePack();
        messagePack.register(Message.class);
        Message message = messagePack.read(array,Message.class);
        out.add(message);
    }

}
