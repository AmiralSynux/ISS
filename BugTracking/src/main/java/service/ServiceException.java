package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceException extends RuntimeException {
    private static final Logger logger= LogManager.getLogger();
    public ServiceException(String s) {
        super(s);
        logger.error(s);
    }
}
