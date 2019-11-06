package leduc;

import leduc.exception.NonExistentDateException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a date and contains a GregorianCalendar type field.
 */
public class Date {
    private LocalDateTime date;

    /**
     * Constructor of leduc.Date.
     *
     * @param date LocalDateTime which is a date.
     */
    public Date(LocalDateTime date) {
        this.date = date;
    }

    /**
     *  Constructor of leduc.Date
     * @param date the String date
     * @throws NonExistentDateException Exception caught when the task to delete does not exist.
     */
    public Date( String date ) throws NonExistentDateException {
        LocalDateTime d1 = null;
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.ENGLISH);
            d1 = LocalDateTime.parse(date.trim(), formatter);
        }catch(Exception e){
            throw new NonExistentDateException();
        }
        this.date = d1;
    }
    /**
     * Allow to snooze the date
     * Fixed to 30 minutes
     */
    public void snoozeLocalDateTime() {
        this.date = this.date.plusMinutes(30);
    }

    /**
     * Setter of the LocalDateTime date
     *
     * @param date the new value of date
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Getter of the LocalDateTime date
     *
     * @return date the LocalDateTime date
     */
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Returns a String representing a date.
     *
     * @return a String representation of date.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dateFormatted = this.date.format(formatters);
        return dateFormatted; //no need secondes and time zone
    }

}

