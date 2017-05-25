package net.galvin.chat.client.start;

/**
 * Created by galvin on 17-5-24.
 */
final public class AppClientConfig {

    public Integer PORT = 3690;
    public String HOST = "127.0.0.1";

    public static AppClientConfig get(){
        return new AppClientConfig();
    }

    private AppClientConfig(){
        init();
    }

    private void init(){

    }

}
