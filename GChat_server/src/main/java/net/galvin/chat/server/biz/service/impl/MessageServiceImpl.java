package net.galvin.chat.server.biz.service.impl;

import net.galvin.chat.comm.pojo.Message;
import net.galvin.chat.comm.utils.ResUtils;
import net.galvin.chat.server.biz.dao.MessageDao;
import net.galvin.chat.server.biz.service.MessageService;
import net.galvin.chat.server.netty.Session;
import net.galvin.chat.server.netty.SessionManager;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by galvin on 17-5-26.
 */
public class MessageServiceImpl implements MessageService {

    private static MessageService messageService;

    public static MessageService get(){
        if(messageService == null){
            synchronized (MessageServiceImpl.class){
                if(messageService == null){
                    messageService = new MessageServiceImpl();
                }
            }
        }
        return messageService;
    }

    private MessageServiceImpl(){
        messageDao = MessageDao.get();
    }

    private MessageDao messageDao;


    public void process(Message message) {

        //保存消息发送消息
        message.setReceiveTime(new Date());
        message.setStatus(ResUtils.MESSAGE_STATUS.TO_SEND.name());
        this.insert(message);
        Session session = SessionManager.get(message.getToUser());
        if(session != null){
            session.send(message);
            message.setStatus(ResUtils.MESSAGE_STATUS.SENT.name());
            message.setSendTime(new Date());
            this.update(message);
        }

        //发消息人的历史消息。
        session = SessionManager.get(message.getFromUser());
        if(session != null){
            List<Message> messageList = this.messageDao.query(null,null,ResUtils.MESSAGE_STATUS.TO_SEND.name(),null,message.getFromUser());
            for(Message tempMsg : messageList){
                session.send(tempMsg);
                tempMsg.setStatus(ResUtils.MESSAGE_STATUS.SENT.name());
                tempMsg.setSendTime(new Date());
                this.update(tempMsg);
            }
        }

    }

    private int insert(Message message) {
        return this.messageDao.insert(message);
    }

    private int update(Message message) {
        return this.messageDao.update(message);
    }

}
