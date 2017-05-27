package net.galvin.chat.server.jdbc;

import org.apache.commons.lang3.StringUtils;

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
 * Created by galvin on 17-5-27.
 */
public class JdbcUtils {


    private static SqlWrapper analysisSql(String sql){
        SqlWrapper sqlWrapper = new SqlWrapper();
        if(StringUtils.isEmpty(sql)){
            return sqlWrapper;
        }
        Pattern pattern = Pattern.compile(":[1-9a-zA-Z_]+");
        Matcher matcher = pattern.matcher(sql);
        int index = 0;
        List<SqlParam> sqlParamList = new ArrayList<SqlParam>();
        while (matcher.find()){
            index++;
            String group = matcher.group();
            sql.indexOf(group);
            sql = sql.replaceFirst(group,"?");
            String name = group.substring(1,group.length());
            SqlParam sqlParam = new SqlParam();
            sqlParam.setIndex(index);
            sqlParam.setName(name);
            sqlParamList.add(sqlParam);
            matcher = pattern.matcher(sql);
        }
        sqlWrapper.setSql(sql);
        sqlWrapper.setSqlParamList(sqlParamList);
        sqlWrapper.setValid(true);
        return sqlWrapper;
    }

    private static  <T> void initParamValue(SqlWrapper sqlWrapper, T t, Class<T> tClass){
        Map<String,Method> methodMap = new HashMap<String,Method>();
        Method[] methodArr = tClass.getMethods();
        for(Method method : methodArr){
            methodMap.put(method.getName(),method);
        }
        List<SqlParam> sqlParamList = sqlWrapper.getSqlParamList();
        for(SqlParam sqlParam : sqlParamList){
            String fieldName = sqlParam.getName();
            String methodName = "get"+
                    (fieldName.length() == 1 ? fieldName.substring(0,fieldName.length()).toUpperCase()
                            : fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length()));
            Method method =  methodMap.get(methodName);
            if(method == null){
                System.out.println("The sql's "+fieldName+" does not get method.");
                sqlWrapper.setValid(false);
                return;
            }
            try {
                Object value = method.invoke(t,null);
                sqlParam.setValue(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> PreparedStatement createStatement(String sql, Connection connection,T t, Class<T> tClass){
        PreparedStatement preparedStatement = null;
        SqlWrapper sqlWrapper = analysisSql(sql);
        if(!sqlWrapper.isValid()){
            return preparedStatement;
        }
        initParamValue(sqlWrapper,t,tClass);
        if(!sqlWrapper.isValid()){
            return preparedStatement;
        }
        if(connection != null){
            try {
                preparedStatement = connection.prepareStatement(sqlWrapper.getSql());
                for(SqlParam sqlParam : sqlWrapper.getSqlParamList()){
                    preparedStatement.setObject(sqlParam.getIndex(),sqlParam.getValue());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return preparedStatement;
    }

    private static  void initParamValue(SqlWrapper sqlWrapper, Map<String,Object> params){
        List<SqlParam> sqlParamList = sqlWrapper.getSqlParamList();
        for(SqlParam sqlParam : sqlParamList){
            String fieldName = sqlParam.getName();
            try {
                Object value = params.get(fieldName);
                if(value == null){
                    System.out.println("The sql's "+fieldName+" does not has value.");
                    sqlWrapper.setValid(false);
                    return;
                }
                sqlParam.setValue(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static PreparedStatement createStatement(String sql, Connection connection,Map<String,Object> params){
        PreparedStatement preparedStatement = null;
        SqlWrapper sqlWrapper = analysisSql(sql);
        if(!sqlWrapper.isValid()){
            return preparedStatement;
        }
        initParamValue(sqlWrapper,params);
        if(!sqlWrapper.isValid()){
            return preparedStatement;
        }
        if(connection != null){
            try {
                preparedStatement = connection.prepareStatement(sqlWrapper.getSql());
                for(SqlParam sqlParam : sqlWrapper.getSqlParamList()){
                    preparedStatement.setObject(sqlParam.getIndex(),sqlParam.getValue());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return preparedStatement;
    }

}
