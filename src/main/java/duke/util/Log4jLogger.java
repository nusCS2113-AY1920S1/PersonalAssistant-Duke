//@@author kkeejjuunn

package duke.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jLogger {
    private static Logger _logger;

    public static void logException(Class className, String message) {
        _logger = LogManager.getLogger(className);
        _logger.error(message);
    }
}
