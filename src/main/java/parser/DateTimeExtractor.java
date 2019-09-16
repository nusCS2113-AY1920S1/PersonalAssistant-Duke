package parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeExtractor {

    private static SimpleDateFormat DateFormatterEvent = new SimpleDateFormat("dd/MM/yyyy HHmm");
    private static SimpleDateFormat DateFormatterDeadline = new SimpleDateFormat("dd/MM/yyyy HHmm");
    private static Date dateEvent;
    private static Date dateDeadline;

    public static Date extractDateTime(String dateTimeFromUser, String command) throws ParseException {

        if(command.equals("event")) {
            dateEvent = DateFormatterEvent.parse(dateTimeFromUser);
            return dateEvent;
        }
        else if(command.equals("deadline")) {
            dateDeadline = DateFormatterDeadline.parse(dateTimeFromUser);
            return dateDeadline;
        }

        Date dateUnknown = new Date();
        return dateUnknown;
    }
}
