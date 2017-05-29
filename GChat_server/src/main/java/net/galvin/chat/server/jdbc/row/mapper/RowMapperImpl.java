package net.galvin.chat.server.jdbc.row.mapper;

import net.galvin.chat.server.jdbc.comm.JdbcUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by galvin on 17-5-28.
 */
public class RowMapperImpl implements RowMapper<List<Map<String,Object>>> {

    private static volatile RowMapper rowMapperImpl;

    public static RowMapper get(){
        if(rowMapperImpl == null){
            synchronized (RowMapperImpl.class){
                if(rowMapperImpl == null){
                    rowMapperImpl = new RowMapperImpl();
                }
            }
        }
        return rowMapperImpl;
    }

    public List<Map<String,Object>> action(ResultSet resultSet) {
        List<Map<String,Object>> resultMaplist = new ArrayList<Map<String, Object>>();
        try {
            int columnCount = resultSet.getMetaData().getColumnCount();
            List<String> columnNameList = new ArrayList<String>();
            for(int index=0;index < columnCount;index++){
                columnNameList.add(resultSet.getMetaData().getColumnName(index+1));
            }
            Map<String,Object> resultMap = new HashMap<String,Object>();
            while(resultSet.next()){
                int index=1;
                for(String columnName : columnNameList){
                    String key = JdbcUtils.covert(columnName);
                    resultMap.put(key,resultSet.getObject(index));
                    index++;
                }
                resultMaplist.add(resultMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultMaplist;
    }

}
