package compal.commons;


import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Configures and manages loggers and handlers, including their logging level.
 * Named {@link Logger}s can be obtained from this class<br>
 * Loggers have been configured to output messages to the console and a {@code .log} file by default,
 * at the {@code INFO} level.
 */
public class LogUtils {
    private static final String LOG_FILE_NAME = "COMPal.log";
    private static Level currentLogLevel = Level.INFO;
    private static FileHandler fileHandler;


    /**
     * Creates a logger with the given name.
     */
    public static Logger getLogger(String name) {
        Logger logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);

        try {
            if (fileHandler == null) {
                FileHandler fileHandlerTemp = new FileHandler(LOG_FILE_NAME, true);
                fileHandlerTemp.setFormatter(new SimpleFormatter());
                fileHandlerTemp.setLevel(currentLogLevel);
                fileHandler = fileHandlerTemp;
            }
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.warning("Error adding file handler for logger.");
        }

        return Logger.getLogger(name);
    }

    /**
     * Creates a Logger for the given class name.
     */
    public static <T> Logger getLogger(Class<T> clasz) {
        if (clasz == null) {
            return Logger.getLogger("");
        }
        return getLogger(clasz.getSimpleName());
    }
}
