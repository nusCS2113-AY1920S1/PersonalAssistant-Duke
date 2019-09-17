package leduc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a date and contains a GregorianCalendar type field.
 */
public class Date {
    private LocalDateTime d;

    /**
     * Constructor of leduc.Date.
     * @param d GregorianCalendar which is a date.
     */
    public Date(LocalDateTime d){
        this.d = d;
    }

    /**
     * Allow to snooze the date
     * Fixed to 30 minutes
     */
    public void snoozeLocalDateTime(){
        this.d = this.d.plusMinutes(30);
    }

    /**
     * Returns a String representing a date.
     * @return a String representation of date.
     */
    @Override
    public String toString(){
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dateFormatted = this.d.format(formatters);
        return  dateFormatted; //no need secondes and time zone
    }
}
