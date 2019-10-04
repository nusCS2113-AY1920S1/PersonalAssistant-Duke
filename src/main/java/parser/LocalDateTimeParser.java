package parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LocalDateTimeParser {
    /**
     * Converts a LocalDateTime to a user readable string.
     * @param localDateTime LocalDateTime object that we wish to convert
     * @return String that is a formatted date and time
     */
    public String toString(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        String formatted = localDateTime.format(dateTimeFormatter);
        return formatted;
    }
}
