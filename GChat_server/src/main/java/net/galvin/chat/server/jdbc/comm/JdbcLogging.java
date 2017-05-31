package net.galvin.chat.server.jdbc.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * JdbcLogging
 */
final public class JdbcLogging {

    private final static Logger LOGGER = LoggerFactory.getLogger(JdbcLogging.class);

    public static void debug(String info){
        LOGGER.debug(info);
    }

    public static void info(String info){
        LOGGER.info(info);
    }

    public static void warn(String info){
        LOGGER.warn(info);
    }

    public static void error(String info){
        LOGGER.error(info);
    }

    public static void error(Exception e){
        if(e == null){
            return;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        e.printStackTrace(pout);
        String info = new String(out.toByteArray());
        try {
            pout.close();
            out.close();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        LOGGER.error(info);
    }


}
