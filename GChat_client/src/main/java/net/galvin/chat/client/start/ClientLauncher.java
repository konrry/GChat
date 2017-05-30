package net.galvin.chat.client.start;

import net.galvin.chat.client.netty.GChatClient;
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
        String yourName = null;
        String friendName = null;
        while (true){
            if(yourName == null){
                System.out.println("Please input your name: ");
                String content = scanner.nextLine();
                if("exit".equalsIgnoreCase(content)){
                }else {
                    yourName = content;
                    Message message = new Message("SESSION_ALIVE");
                    message.setCreateTime(new Date());
                    message.setFromUser(yourName);
                    gChatClient.send(message);
                }
            }else if(friendName == null){
                System.out.println("Please input your friend's name: ");
                String content = scanner.nextLine();
                if("exit".equalsIgnoreCase(content)){
                    yourName = null;
                }else {
                    friendName = content;
                }
            }else {
                System.out.println("Please input msg: ");
                String content = scanner.nextLine();
                if("exit".equalsIgnoreCase(content)){
                    friendName = null;
                }else {
                    Message message = new Message(content);
                    message.setCreateTime(new Date());
                    message.setFromUser(yourName);
                    message.setToUser(friendName);
                    gChatClient.send(message);
                }
            }
        }

        /*synchronized (LOCK){
            while (isRunning){
                try {
                    System.out.println("I am running, and the main thread will wait.");
                    LOCK.wait();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }*/
    }


}
