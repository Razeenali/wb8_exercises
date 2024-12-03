package com.pluralsight;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    final static Logger logger = LogManager.getLogger(Main.class); // Fixed the logger initialization

    public static void main(String[] args) {
        System.out.println("Hello world!");
        logMeLikeYouDo("!"); // Corrected syntax
    }

    private static void logMeLikeYouDo(String input) {
        if (logger.isDebugEnabled()) {
            logger.debug("This is debug : " + input);
        }

        if (logger.isInfoEnabled()) {
            logger.info("This is info : " + input);
        }

        logger.warn("This is warn : " + input);
        logger.error("This is error : " + input);
        logger.fatal("This is fatal : " + input);
    }
}
