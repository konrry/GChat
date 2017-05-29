package net.galvin.chat.server.jdbc.row.mapper;

import net.galvin.chat.server.jdbc.comm.JdbcUtils;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by galvin on 17-5-28.
 */
public class BeanRowMapperImpl<T> implements RowMapper<List<T>> {

    private Class<T> tClass;

    public BeanRowMapperImpl(Class<T> tClass){
        this.tClass = tClass;
    }

    public List<T> action(ResultSet resultSet) {
        List<T> beanList = new ArrayList<T>();
        try {
            Map<String,Method> methodMap = new HashMap<String, Method>();
            for(Method method : tClass.getMethods()){
                methodMap.put(method.getName(),method);
            }
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()){
                T target = tClass.newInstance();
                for(int index=0;index < columnCount;index++){
                    String columnName = resultSet.getMetaData().getColumnName(index+1);
                    String setMethodName = JdbcUtils.covert("set",columnName);
                    Method setMethod = methodMap.get(setMethodName);
                    if(setMethod != null){
                        setMethod.invoke(target,resultSet.getObject(columnName));
                        beanList.add(target);
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return beanList;
    }

}
