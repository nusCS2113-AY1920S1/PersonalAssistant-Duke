package leduc;

import leduc.exception.NonExistentDateException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a date and contains a GregorianCalendar type field.
 */
public class Date {
    private LocalDateTime d;

    /**
     * Constructor of leduc.Date.
     *
     * @param d LocalDateTime which is a date.
     */
    public Date(LocalDateTime d) {
        this.d = d;
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
        this.d = d1;
    }
    /**
     * Allow to snooze the date
     * Fixed to 30 minutes
     */
    public void snoozeLocalDateTime() {
        this.d = this.d.plusMinutes(30);
    }

    /**
     * Setter of the LocalDateTime d
     *
     * @param d the new value of date
     */
    public void setD(LocalDateTime d) {
        this.d = d;
    }

    /**
     * Getter of the LocalDateTime d
     *
     * @return d the LocalDateTime d
     */
    public LocalDateTime getD() {
        return this.d;
    }

    /**
     * Returns a String representing a date.
     *
     * @return a String representation of date.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dateFormatted = this.d.format(formatters);
        return dateFormatted; //no need secondes and time zone
    }

}

