package gazeeebo.logger;

import java.io.IOException;
import java.util.logging.*;

public class LogCenter {
    public static void setUpLogger(Logger logger) {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);

        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE);
        logger.addHandler(ch);

        try {
            FileHandler fh = new FileHandler("gazeeeboLogger.txt", true);
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(Level.WARNING);
            logger.addHandler(fh);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Can't log to file", e);
        }

    }
}
