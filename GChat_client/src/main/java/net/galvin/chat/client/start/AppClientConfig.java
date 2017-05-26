package net.galvin.chat.client.start;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by galvin on 17-5-24.
 */
final public class AppClientConfig {

    public static String HOST = "127.0.0.1";
    public static Integer PORT = 3690;

    static {
        InputStream inputStream = AppClientConfig.class.getClassLoader()
                .getResourceAsStream("application-client.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
    }

}
