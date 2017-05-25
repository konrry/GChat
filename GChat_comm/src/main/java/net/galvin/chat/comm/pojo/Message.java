package net.galvin.chat.comm.pojo;

import net.galvin.chat.comm.utils.ResUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息对象
 */
public class Message implements Serializable {

    private static final long serialVersionUID = -8617242594556909472L;

    private String id;
    private String content;
    private Date receiveTime;
    private Date sendTime;
    private String status;

    public Message(){}

    public Message(String content) {
        this.id = ResUtils.getID();
        this.content = content;
        this.receiveTime = new Date();
        this.status = ResUtils.MESSAGE_STATUS.TO_SEND.name();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.content;
    }
}