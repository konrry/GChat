package net.galvin.chat.server.biz.dao;

import net.galvin.chat.comm.pojo.Message;
import net.galvin.chat.comm.utils.ResUtils;
import net.galvin.chat.server.jdbc.JdbcTemplate;
import net.galvin.chat.server.jdbc.JdbcTemplateFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Message> query(Date startCreateTime,Date endCreateTime,String status,String fromUser,String toUser){
        final Map<String,Object> params = new HashMap<String,Object>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM MESSAGE WHERE 1 = 1");
        if(startCreateTime != null){
            sqlBuilder.append(" ,create_time >= :startCreateTime");
            params.put("startCreateTime",startCreateTime);
        }
        if(endCreateTime != null){
            sqlBuilder.append(" ,create_time <= :endCreateTime");
            params.put("endCreateTime",endCreateTime);
        }
        if(StringUtils.isNotEmpty(status)){
            sqlBuilder.append(" and status = :status");
            params.put("status",status);
        }
        if(StringUtils.isNotEmpty(fromUser)){
            sqlBuilder.append(" and from_user = :fromUser");
            params.put("fromUser",fromUser);
        }
        if(StringUtils.isNotEmpty(toUser)){
            sqlBuilder.append(" and to_user = :toUser");
            params.put("toUser",toUser);
        }
        return this.jdbcTemplate.queryForObjectList(sqlBuilder.toString(),params,Message.class);
    }

    public static void main(String[] args) {
        Message message = new Message("123");
        List<Message> messageList =  get().query(null,null, ResUtils.MESSAGE_STATUS.TO_SEND.name(),"chuqiang","chuqiang");
        System.out.println(messageList);
    }

}
