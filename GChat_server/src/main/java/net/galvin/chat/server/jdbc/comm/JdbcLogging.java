package net.galvin.chat.server.jdbc.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


}
