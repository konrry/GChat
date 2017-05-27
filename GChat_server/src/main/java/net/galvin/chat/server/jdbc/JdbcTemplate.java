package net.galvin.chat.server.jdbc;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
        final Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        if(connection != null){
            preparedStatement = JdbcUtils.createStatement(sql,connection,params);
            try {
                rowCount = preparedStatement.executeUpdate();
                if(!connection.getAutoCommit()){
                    connection.commit();
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
        }
        return rowCount;
    }

    public <T> int update(String sql,T t, Class<T> tClass){
        int rowCount = 0;
        final Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        if(connection != null){
            preparedStatement = JdbcUtils.createStatement(sql,connection,t,tClass);
            try {
                rowCount = preparedStatement.executeUpdate();
                if(!connection.getAutoCommit()){
                    connection.commit();
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
        }
        return rowCount;
    }


    public <T> Map<String,Object> queryForMap(String sql,T t,Class<T> tClass){
        Map<String,Object> target = null;
        return target;
    }

    public <T> List<Map<String,Object>> queryForMapList(String sql,T t,Class<T> tClass){
        List<Map<String,Object>> target = null;
        return target;
    }

    public <T> List<T> queryForObjectList(String sql,T t, Class<T> tClass){
        List<T> tList = null;
        return tList;
    }

    public <T> T queryForObject(String sql,T t, Class<T> tClass){
        T tt = null;
        return tt;
    }

    public <T> Map<String,Object> queryForMap(String sql,Map<String,Object> params){
        Map<String,Object> target = null;
        return target;
    }

    public <T> List<Map<String,Object>> queryForMapList(String sql,Map<String,Object> params){
        List<Map<String,Object>> target = null;
        return target;
    }

    public <T> List<T> queryForObjectList(String sql,Map<String,Object> params){
        List<T> tList = null;
        return tList;
    }

    public <T> T queryForObject(String sql,Map<String,Object> params){
        T t = null;
        return t;
    }

    private Connection getConnection(){
        if(dataSource == null){
            return null;
        }
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(connection == null){
            return null;
        }
        return connection;
    }

}
