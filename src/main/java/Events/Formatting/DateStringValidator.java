//@@author Ryan-Wong-Ren-Wei
package Events.Formatting;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public interface DateStringValidator {
    static boolean isValidDateForToDo(String dateString) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        format.setLenient(false);
        try {
            format.parse(dateString);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    static boolean isValidDateForEvent(String dateString) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HHmm");
        format.setLenient(false);
        try {
            format.parse(dateString);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
