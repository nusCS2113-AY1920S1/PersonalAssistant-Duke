// @@author namiwa

package planner.util.logger.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LoggerFormatter extends Formatter {

    @Override
    public String format(LogRecord logRecord) {
        StringBuffer buff = new StringBuffer(1000);
        buff.append("Logging Event: ");
        buff.append(new Date().toString());
        buff.append(" ");
        if (logRecord.getLevel().intValue() >= Level.WARNING.intValue()) {
            buff.append("Status Level: ");
            buff.append(logRecord.getLevel());
            buff.append(logRecord.getMessage());
        }
        buff.append("\n");
        return buff.toString();
    }

    /**
     * Setting head of logging session.
     * @param h handler to the logger instance.
     * @return String to indicate start of logging session.
     */
    public String getHead(Handler h) {
        return "--Start: Session Begin- "
                + new Date().toString()
                + "\n";
    }

    private String calculateDate(long millis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HHmm");
        Date resultDate = new Date(millis);
        return dateFormat.format(resultDate);
    }

    /**
     * Setting head of logging session.
     * @param h handler to the logger instance.
     * @return String to indicate start of logging session.
     */
    public String getTail(Handler h) {
        return "--End: Session End- "
                + new Date().toString()
                + "\n";
    }
}
