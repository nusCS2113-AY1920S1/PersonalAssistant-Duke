//@@author yueyuu

package gazeeebo.logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogCenter {
    /**
     * Set up the logger.
     * @param logger Message that is logged.
     */

    public static void setUpLogger(Logger logger) {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);

        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE);
        logger.addHandler(ch);

        try {
            FileHandler fh = new FileHandler("GazeeeboLogger.log", true);
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(Level.WARNING);
            logger.addHandler(fh);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Can't log to file", e);
        }

    }
}
