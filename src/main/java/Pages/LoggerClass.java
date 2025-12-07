package Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerClass {

    public static Logger getLogger() {
        return LogManager.getLogger("AutomationLogger");
    }
}