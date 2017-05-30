package net.galvin.chat.server.start;

import net.galvin.chat.server.netty.GChatServer;

/**
 * Created by galvin on 17-5-24.
 */
public class ServerLauncher {

    private volatile static boolean isRunning = true;
    private final static Object LOCK = new Object();
    private static GChatServer gChatServer;

    public static void main(String[] args) {
        gChatServer = new GChatServer();
        gChatServer.launch();

        while (isRunning){
            synchronized (LOCK){
                try {
                    System.out.println("I am running, and the main thread will wait.");
                    LOCK.wait(10000);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
