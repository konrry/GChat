package net.galvin.chat.server.jdbc;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

/**
 * Created by galvin on 17-5-26.
 */
public class DataSourceUtils {

    public static String USER_NAME = "root";
    public static String PASSWORD = "Chujing@190";
    public static String URL = "jdbc:mysql://10.113.10.190/test";
    private static String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    public static DataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(USER_NAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUrl(URL);
        return dataSource;
    }

}
