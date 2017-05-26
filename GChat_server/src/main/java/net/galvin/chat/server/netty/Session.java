package net.galvin.chat.server.netty;

import io.netty.channel.Channel;
import net.galvin.chat.comm.pojo.Message;

/**
 * Created by galvin on 17-5-26.
 */
public class Session {

    private Channel channel;
    private String key;
    private long start;
    private long expire;

    public Session(String key, Channel channel) {
        this.channel = channel;
        this.key = key;
        this.start = System.currentTimeMillis();
        this.expire = this.start + 600000;
    }

    public boolean isValid(){
        return System.currentTimeMillis() < this.expire;
    }

    public void send(Message message){
        try {
            this.channel.writeAndFlush(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
