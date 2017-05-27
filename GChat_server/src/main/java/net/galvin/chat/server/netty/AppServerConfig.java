package net.galvin.chat.server.netty;

import net.galvin.chat.server.jdbc.DataSourceUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by galvin on 17-5-24.
 */
public class AppServerConfig {

    public static Integer BROKER_ID = 1;

    public static Integer PORT = 3690;

    public static String HOST = "127.0.0.1";

    static {
        InputStream inputStream = AppServerConfig.class.getClassLoader()
                .getResourceAsStream("application-server.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // BROKER_ID
        String brokerId = properties.getProperty("g.chat.broker.id");
        BROKER_ID = Integer.parseInt(brokerId);

        // HOST  PORT
        String listeners = properties.getProperty("g.chat.listeners");
        String[] listenerArr = listeners.split(":");
        if(listenerArr.length == 1
                && StringUtils.isNotEmpty(listenerArr[0])){
            HOST = listenerArr[0];
        }else if(listenerArr.length == 2
                && StringUtils.isNotEmpty(listenerArr[0])
                && StringUtils.isNotEmpty(listenerArr[1])){
            HOST = listenerArr[0];
            PORT = Integer.parseInt(listenerArr[1]);
        }

        // DataSource
        String userName = properties.getProperty("datasource.username");
        String password = properties.getProperty("datasource.password");
        String url = properties.getProperty("datasource.url");

        DataSourceUtils.USER_NAME = userName;
        DataSourceUtils.PASSWORD = password;
        DataSourceUtils.URL = url;

    }

}
