package net.galvin.chat.server.start;

/**
 * Created by galvin on 17-5-24.
 */
final public class AppServerConfig {

    public Integer BROKER_ID = 1;

    public Integer PORT = 3690;

    public static AppServerConfig get(){
        return new AppServerConfig();
    }

    private AppServerConfig(){
        BROKER_ID = 1;
    }

}
