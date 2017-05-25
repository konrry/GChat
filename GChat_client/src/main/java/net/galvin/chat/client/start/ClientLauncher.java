package net.galvin.chat.client.start;

/**
 * Created by galvin on 17-5-24.
 */
public class ClientLauncher {

    private volatile static boolean isRunning = true;
    private final static Object LOCK = new Object();
    private static GChatClient gChatClient;

    public static void main(String[] args) {
        gChatClient = new GChatClient();
        gChatClient.launch();

        synchronized (LOCK){
            while (isRunning){
                try {
                    System.out.println("I am running, and the main thread will wait.");
                    LOCK.wait();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
