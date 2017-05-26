package net.galvin.chat.server.biz.service;

import net.galvin.chat.comm.pojo.Message;

/**
 * Created by galvin on 17-5-26.
 */
public interface MessageService {

    void process(Message message);

}
