package net.galvin.chat.server.jdbc;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

/**
 * Created by galvin on 17-5-26.
 */
public class JdbcTemplateFactory {

    private static volatile JdbcTemplate jdbcTemplate;
    private static final Object lock = new Object();

    public static JdbcTemplate getJdbcTemplate(){
        if(jdbcTemplate == null){
            synchronized (lock){
                if(jdbcTemplate == null){
                    DataSource dataSource = DataSourceUtils.getDataSource();
                    jdbcTemplate = new JdbcTemplate(dataSource);
                }
            }
        }
        return jdbcTemplate;
    }

}
