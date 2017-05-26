package net.galvin.chat.server.netty;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by galvin on 17-5-26.
 */
public class SessionManager {

    private final static Map<String,Session> sessionMap = new ConcurrentHashMap<String, Session>();

    public static Session get(String key){
        Session session = null;
        try {
            session = sessionMap.get(key);
        }catch (Exception e){

        }

        return session != null && session.isValid() ? session : null;
    }

    public static Session put(String user, Channel channel){
        sessionMap.put(user,new Session(user,channel));
        return sessionMap.get(user);
    }



}
