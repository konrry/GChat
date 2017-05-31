package net.galvin.chat.server.jdbc;


import net.galvin.chat.server.jdbc.comm.JdbcLogging;
import net.galvin.chat.server.jdbc.comm.JdbcUtils;
import net.galvin.chat.server.jdbc.row.mapper.BeanRowMapperImpl;
import net.galvin.chat.server.jdbc.row.mapper.RowMapper;
import net.galvin.chat.server.jdbc.row.mapper.RowMapperImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        if(connection == null){
            JdbcLogging.error("The connection is null.");
            return rowCount;
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = JdbcUtils.createStatement(sql,connection,params);
            rowCount = preparedStatement.executeUpdate();
            if(!connection.getAutoCommit()){
                connection.commit();
            }
        } catch (Exception e) {
            JdbcLogging.error(e);
        }finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    JdbcLogging.error(e);
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    JdbcLogging.error(e);
                }
            }
        }
        return rowCount;
    }

    public <T> int update(String sql,T t, Class<T> tClass){
        int rowCount = 0;
        final Connection connection = getConnection();
        if(connection == null){
            JdbcLogging.error("The connection is null.");
            return rowCount;
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = JdbcUtils.createStatement(sql,connection,t,tClass);
            rowCount = preparedStatement.executeUpdate();
            if(!connection.getAutoCommit()){
                connection.commit();
            }
        } catch (Exception e) {
            JdbcLogging.error(e);
        }finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    JdbcLogging.error(e);
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    JdbcLogging.error(e);
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
        Connection connection = getConnection();
        if(connection == null){
            JdbcLogging.error("The connection is null.");
            return target;
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = JdbcUtils.createStatement(sql,connection,params);
            ResultSet resultSet = preparedStatement.executeQuery();
            RowMapper<List<Map<String,Object>>> rowMapper = RowMapperImpl.get();
            target = rowMapper.action(resultSet);
        } catch (Exception e) {
            JdbcLogging.error(e);
        }finally {
            if(preparedStatement != null){
                if(preparedStatement != null){
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        JdbcLogging.error(e);
                    }
                }
                if(connection != null){
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        JdbcLogging.error(e);
                    }
                }
            }
        }
        return target;
    }

    public <T> List<T> queryForObjectList(String sql,Map<String,Object> params,Class<T> tClass){
        List<T> tList = null;
        Connection connection = getConnection();
        if(connection == null){
            JdbcLogging.error("The connection is null.");
            return tList;
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = JdbcUtils.createStatement(sql,getConnection(),params);
            ResultSet resultSet = preparedStatement.executeQuery();
            RowMapper<List<T>> rowMapper = new BeanRowMapperImpl<T>(tClass);
            tList = rowMapper.action(resultSet);
        } catch (Exception e) {
            JdbcLogging.error(e);
        }finally {
            if(preparedStatement != null){
                if(preparedStatement != null){
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        JdbcLogging.error(e);
                    }
                }
                if(connection != null){
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        JdbcLogging.error(e);
                    }
                }
            }
        }
        return tList;
    }

    public <T> T queryForObject(String sql,Map<String,Object> params){
        T t = null;
        return t;
    }

    private Connection getConnection(){
        if(dataSource == null){
            JdbcLogging.error("JdbcTemplate.dataSource can not be null.");
            return null;
        }
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        }catch (Exception e){
            e.printStackTrace();
            JdbcLogging.error("Failed to get db connection from datasource.");
            JdbcLogging.error(e);
        }
        return connection;
    }

}
