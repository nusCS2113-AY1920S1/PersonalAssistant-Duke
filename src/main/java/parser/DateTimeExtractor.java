package parser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeExtractor {

    private static SimpleDateFormat DATE_FORMATTER_EVENT = new SimpleDateFormat("dd/MM/yyyy HHmm-HHmm");
    private static SimpleDateFormat DATE_FORMATTER_DEADLINE = new SimpleDateFormat("dd/MM/yyyy HHmm");
    private static String dateEvent;
    private static String dateDeadline;

    public static String extractDateTime(String dateTimeFromUser, String command) throws ParseException {

        if(command.equals("event")) {
            System.out.println(command);
            dateEvent = (DATE_FORMATTER_EVENT.parse(dateTimeFromUser)).toString();
            return dateEvent;
        }
        else if(command.equals("deadline")) {
            dateDeadline = (DATE_FORMATTER_DEADLINE.parse(dateTimeFromUser)).toString();
            return dateDeadline;
        }

        String dateUnknown = "00/00/0000";
        return dateUnknown;
    }
}
