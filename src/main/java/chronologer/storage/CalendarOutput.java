package chronologer.storage;

import chronologer.exception.ChronologerException;
import chronologer.exception.MyLogger;
import chronologer.ui.UiTemporary;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Outputs the ICS File created by export command.
 *
 * @author Tan Yi Xiang
 * @version v1.1
 */
public class CalendarOutput {

    private static CalendarOutputter calendarOutputter = new CalendarOutputter();
    private static String filePath = System.getProperty("user.dir") + "/src/ChronologerDatabase/";
    private static MyLogger logger = new MyLogger("CalendarOutput class", "StorageErrors");

    /**
     * Process the calendar into an ics file.
     *
     * @param fileName Name of the file
     * @param calendar Calendar to be processed
     * @throws ChronologerException If there are errors writing the ics file.
     */
    public static void outputCalendar(String fileName, Calendar calendar) throws ChronologerException {
        File icsFile = new File(filePath.concat(fileName).concat(".ics"));
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(icsFile);
            calendarOutputter.output(calendar, fileOutputStream);
            UiTemporary.printOutput("Success,ics file written at src/ChronologerDatabase/" + fileName);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            UiTemporary.printOutput(ChronologerException.fileDoesNotExist());
            logger.writeLog(e.toString(), "Calendar Output");
            throw new ChronologerException(ChronologerException.fileDoesNotExist());
        } catch (IOException e) {
            UiTemporary.printOutput(ChronologerException.errorWriteCalendar());
            logger.writeLog(e.toString(), "Calendar Output");
            throw new ChronologerException(ChronologerException.errorWriteCalendar());
        }
    }

}
