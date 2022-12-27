package com.bezkoder.spring.jpa.h2.controller;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Log4j2 {

    private static Logger logger = LogManager.getLogger(Log4j2.class);

    public static void main(String[] args) {
        logger.error("Error log message");
        logger.info("Info log message");
        logger.debug("Debug log message");
        logger.trace("Trace log message");
    }
}
