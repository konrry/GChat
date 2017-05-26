package net.galvin.chat.client.start;

import net.galvin.chat.comm.pojo.Message;

import java.util.Date;
import java.util.Scanner;

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


        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input your name: ");
        String yourName = scanner.nextLine();
        System.out.println("Please input your friend's name: ");
        String friendName = scanner.nextLine();

        Message message = new Message("SESSION_ALIVE");
        message.setCreateTime(new Date());
        message.setFromUser(yourName);
        gChatClient.send(message);

        System.out.println("Please input msg: ");
        while (scanner.hasNext()){
            String content = scanner.nextLine();
            message = new Message(content);
            message.setCreateTime(new Date());
            message.setFromUser(yourName);
            message.setToUser(friendName);
            gChatClient.send(message);
            System.out.println("Please input: ");
        }

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
