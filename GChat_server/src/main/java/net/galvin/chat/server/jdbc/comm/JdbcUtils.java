package net.galvin.chat.server.jdbc.comm;

import org.apache.commons.lang3.StringUtils;

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


    /**
     * 讲sql解析未占位符的，并且讲参数保存在一个参数定义列表中。
     * @param sql
     * @return
     */
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

    /**
     * 参数的值和占位符的位置相对应
     * @param sqlWrapper
     * @param t
     * @param tClass
     * @param <T>
     */
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

    /**
     * 解析sql，初始化参数，生成statement。
     * @param sql
     * @param connection
     * @param t
     * @param tClass
     * @param <T>
     * @return
     */
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

    /**
     * 参数的值和占位符的位置相对应
     * @param sqlWrapper
     * @param params
     */
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

    /**
     * 解析sql，初始化参数，生成statement。
     * @param sql
     * @param connection
     * @param params
     * @return
     */
    public static PreparedStatement createStatement(String sql, Connection connection,Map<String,Object> params){
        PreparedStatement preparedStatement = null;
        if(params == null){
            try {
                preparedStatement = connection.prepareStatement(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return preparedStatement;
        }
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


    /**
     * 表中的列明转换成字段名称。
     * @param columnName
     * @return
     */
    public static String covert(String columnName){
        if(StringUtils.isEmpty(columnName)){
            return null;
        }
        if(columnName.contains("_")){
            StringBuilder sb = new StringBuilder();
            String[] tempNameArr = columnName.split("_");
            for(int i=0;i < tempNameArr.length;i++){
                String tempName = tempNameArr[i];
                if(i == 0){
                    sb.append(tempName.toLowerCase());
                }else {
                    sb.append(tempName.length() == 1 ? tempName.toUpperCase() :
                            tempName.substring(0,1).toUpperCase()+tempName.substring(1).toLowerCase());
                }
            }
            return sb.toString();
        }else {
            return columnName.toLowerCase();
        }
    }

    /**
     * 讲表中的列明转换成set get 方法名称。
     * @param methodPre
     * @param columnName
     * @return
     */
    public static String covert(String methodPre,String columnName){
        if(StringUtils.isEmpty(columnName)){
            return null;
        }
        if(columnName.contains("_")){
            StringBuilder sb = new StringBuilder(methodPre);
            String[] tempNameArr = columnName.split("_");
            for(int i=0;i < tempNameArr.length;i++){
                String tempName = tempNameArr[i];
                sb.append(tempName.length() == 1 ? tempName.toUpperCase() :
                        tempName.substring(0,1).toUpperCase()+tempName.substring(1).toLowerCase());
            }
            return sb.toString();
        }else {
            String tempName = columnName.length() == 1 ? columnName.toUpperCase() :
                    columnName.substring(0,1).toUpperCase()+columnName.substring(1).toLowerCase();
            return methodPre+tempName;
        }
    }
}