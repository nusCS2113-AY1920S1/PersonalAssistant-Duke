package leduc;

import leduc.exception.NonExistentDateException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a date and contains a LocalDateTime type field.
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
     *  A verification of the existence of the date is done.
     * @param date the String date
     * @throws NonExistentDateException  Exception caught when the date given does not exist.
     */
    public Date( String date ) throws NonExistentDateException {
        verifyDate(date);
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
     * This method allows verify if the date exist.
     * @param date String which represents a date.
     * @thorws NonExistentDateException Exception caught when the task to delete does not exist.
     */
    public void verifyDate(String date) throws NonExistentDateException {
        String regex ="[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9] [0-9][0-9]:[0-9][0-9]";
        if (!date.matches(regex)) {
            throw new NonExistentDateException();
        }
        String[] dateString = date.split(" ");
        String[] daysString = dateString[0].split("/");
        String[] hoursString = dateString[1].split(":");
        int day;
        int month;
        int year;
        int hrs;
        int min;
        try {
            day = Integer.parseInt(daysString[0]);
            month = Integer.parseInt(daysString[1]) -1 ;// Convention of Gregorian Calendar Jan= 0; Feb =1; Dec =11;
            year = Integer.parseInt(daysString[2]);
            hrs= Integer.parseInt(hoursString[0]) ;
            min = Integer.parseInt(hoursString[1]);
        }
        catch (Exception e ){
            throw new NonExistentDateException();
        }
        if (min<0 || min >59){
            throw new NonExistentDateException();
        }
        if (hrs <0 || hrs >23){
            throw new NonExistentDateException();
        }
        switch( month){
            case 0: case 2: case 4: case 6: case 7: case 9: case 11: // month with 31 days : 11 for december
                if (day > 31 || day <0) {
                    throw new NonExistentDateException();
                }
                break;
            case 3 : case 5: case 8: case 10: // month with 31 days
                if (day > 30 || day <0) {
                    throw new NonExistentDateException();
                }
                break;
            case 1 : // February
                // second part : no leap year and day==29
                if ((day >29 || day < 0) || ((!((year % 4 ==0 && year % 100 != 0) || year % 400 == 0))&& day==29) ){
                    throw new NonExistentDateException();
                }
                break;
            default:
                throw new NonExistentDateException();
        }
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

