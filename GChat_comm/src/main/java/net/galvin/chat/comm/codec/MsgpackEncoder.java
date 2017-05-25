package net.galvin.chat.comm.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.galvin.chat.comm.pojo.Message;
import org.msgpack.MessagePack;

/**
 * Created by Administrator on 2017/5/8.
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        MessagePack msgPack = new MessagePack();
        try {
            msgPack.register(Message.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        byte[] raw = msgPack.write(msg);
        out.writeBytes(raw);
    }

}
