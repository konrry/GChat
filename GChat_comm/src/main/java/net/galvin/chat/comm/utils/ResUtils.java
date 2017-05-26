package net.galvin.chat.comm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by galvin on 17-5-25.
 */
public class ResUtils {

    private static final String[] RANDOM_CHAR_ARR = {
            "0","1","2","3","4","5","6","7","8","9",
            "A","B","C","D","E","F","G",
            "H","I","J","K","L","M","N",
            "O","P","Q","R","S","T",
            "U","V","W","X","Y","Z",
            "a","b","b","d","e","f","g",
            "h","i","j","k","l","m","n",
            "o","p","q","r","s","t",
            "u","v","w","x","y","z"
    };

    public static final String getID() {
        StringBuilder idBuilder = new StringBuilder("GCHAT").append("_");
        idBuilder.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())).append("_");
        Random random = new Random();
        for(int i=0;i<20;i++){
            idBuilder.append(RANDOM_CHAR_ARR[random.nextInt(RANDOM_CHAR_ARR.length-1)]);
        }
        return idBuilder.toString();
    }

    public enum MESSAGE_STATUS {
        TO_SEND("待发送"),
        WAIT_ACK("等待应答"),
        SENT("已经应答"),
        DISCARD("已丢弃");

        private String cName;

        MESSAGE_STATUS(String cName) {
            this.cName = cName;
        }
    }

}
