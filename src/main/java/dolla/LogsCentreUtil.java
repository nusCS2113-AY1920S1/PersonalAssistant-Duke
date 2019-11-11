package dolla;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class LogsCentreUtil {

    public static final Logger setLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * SEVERE > WARNING > INFO > CONFIG > FINE > FINER > FINEST
     * This method will setup the log to be stored in data.
     */
    public static void logSetter() {
        LogManager.getLogManager().reset();
        setLogger.setLevel(Level.ALL);
        try {
            FileHandler fh = new FileHandler("./data/dollaLogger.log",true);
            fh.setLevel(Level.INFO);
            fh.setFormatter(new SimpleFormatter());
            setLogger.addHandler(fh);
        } catch (IOException e) {
            setLogger.log(Level.SEVERE,"File logger not working", e);
        }
    }

}
