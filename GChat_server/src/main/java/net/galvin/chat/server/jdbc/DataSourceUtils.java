package net.galvin.chat.server.jdbc;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

/**
 * Created by galvin on 17-5-26.
 */
public class DataSourceUtils {

    public static DataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("Chujing@190");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://10.113.10.190/test");
        return dataSource;
    }

}
