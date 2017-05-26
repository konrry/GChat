package net.galvin.chat.server.jdbc;

import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by galvin on 17-5-26.
 */
public class JdbcTemplate {

    private DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int update(String sql, Map<String,Object> params){
        int rowCount = 0;
        if(StringUtils.isEmpty(sql)){
            JdbcLogging.error("The sql can not be empty!!!");
            return rowCount;
        }
        Connection connection = getConnection();
        if(connection == null){
            JdbcLogging.error("Can not acheive the connection!!!");
            return rowCount;
        }
        PreparedStatement preparedStatement = null;
        try {
            Pattern pattern = Pattern.compile(":[1-9a-zA-Z_]+");
            Matcher matcher = pattern.matcher(sql);
            int count = 0;
            List<Object> paramList = new ArrayList<Object>();
            while (matcher.find()){
                count++;
                String group = matcher.group();
                sql.indexOf(group);
                sql = sql.replaceFirst(group,"?");
                paramList.add(params.get(group.substring(1,group.length())));
                matcher = pattern.matcher(sql);
            }
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<count;i++){
                preparedStatement.setObject(i+1,paramList.get(i));
            }
            preparedStatement.executeUpdate();
            if(!connection.getAutoCommit()){
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JdbcLogging.error(e.getMessage());
        }finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JdbcLogging.error(e.getMessage());
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JdbcLogging.error(e.getMessage());
                }
            }
        }
        return rowCount;
    }

    public <T> int update(String sql,T t, Class<T> tClass){
        Map<String,Object> params = new HashMap<String,Object>();
        Map<String,Method> methodMap = new HashMap<String,Method>();
        Method[] methodArr = tClass.getMethods();
        for(Method method : methodArr){
            methodMap.put(method.getName(),method);
        }
        Field[] fieldArr = tClass.getDeclaredFields();
        for(Field field : fieldArr){
            String fieldName = field.getName();
            String methodName = "get"+
                                (fieldName.length() == 1 ? fieldName.substring(0,fieldName.length()).toUpperCase()
                                        : fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length()));
            Method method =  methodMap.get(methodName);
            if(method != null){
                try {
                    Object value = method.invoke(t,null);
                    if(value != null){
                        params.put(fieldName,value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return update(sql, params);
    }


    private Connection getConnection(){
        if(dataSource == null){
            return null;
        }
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        }catch (Exception e){

        }
        if(connection == null){
            return null;
        }
        return connection;
    }

}
