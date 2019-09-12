package duke;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * duke.Time is a class that handles all time related methods.
 */
public class Time {

    /**
     * Returns a LocalDateTime variable converted from the specified String timeStr that
     * was in the format 'dd/MM/yyyy HHmm'.
     * @param timeStr String that should be converted into DateTImeFormatter type.
     * @return A LocalDateTime variable that the computer can understand as time.
     */
    public static LocalDateTime readTime(String timeStr) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm", Locale.ENGLISH);
            LocalDateTime time = LocalDateTime.parse(timeStr, formatter);
//            System.out.println(time);
            return time;

    }
}
