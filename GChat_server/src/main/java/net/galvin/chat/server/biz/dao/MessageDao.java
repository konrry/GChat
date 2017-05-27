package net.galvin.chat.server.biz.dao;

import net.galvin.chat.comm.pojo.Message;
import net.galvin.chat.server.jdbc.JdbcTemplate;
import net.galvin.chat.server.jdbc.JdbcTemplateFactory;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by galvin on 17-5-26.
 */
public class MessageDao {

    private static MessageDao messageDao;
    private JdbcTemplate jdbcTemplate;

    private MessageDao(){
        jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
    }

    public static MessageDao get(){
        if(messageDao == null){
            synchronized (MessageDao.class){
                messageDao = new MessageDao();
            }
        }
        return messageDao;
    }

    public int insert(Message message){
        String insertSql = "insert into MESSAGE (id,content,create_time,send_time,receive_time,status,from_user,to_user) values (:id,:content,:createTime,:sendTime,:receiveTime,:status,:fromUser,:toUser)";
        return this.jdbcTemplate.update(insertSql,message,Message.class);
    }

    public int update(Message message){
        if(message != null && message.getId() != null){
            StringBuilder updateSqlBuilder = new StringBuilder("update MESSAGE set id = :id");
            if(StringUtils.isNotEmpty(message.getContent())){
                updateSqlBuilder.append(" ,content = :content");
            }
            if(message.getCreateTime() != null){
                updateSqlBuilder.append(" ,create_time = :createTime");
            }
            if(message.getSendTime() != null){
                updateSqlBuilder.append(" ,send_time = :sendTime");
            }
            if(message.getReceiveTime() != null){
                updateSqlBuilder.append(" ,receive_time = :receiveTime");
            }
            if(StringUtils.isNotEmpty(message.getStatus())){
                updateSqlBuilder.append(" ,status = :status");
            }
            if(StringUtils.isNotEmpty(message.getFromUser())){
                updateSqlBuilder.append(" ,from_user = :fromUser");
            }
            if(StringUtils.isNotEmpty(message.getToUser())){
                updateSqlBuilder.append(" ,to_user = :toUser");
            }
            updateSqlBuilder.append(" where id = :id");
            return this.jdbcTemplate.update(updateSqlBuilder.toString(),message,Message.class);
        }
        return 0;
    }

    public static void main(String[] args) {
        Message message = new Message("123");
        get().insert(message);
    }

}
