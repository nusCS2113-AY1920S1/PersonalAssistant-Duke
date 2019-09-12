package duke;

import duke.exceptions.DateFormatException;
import duke.exceptions.DateValueException;
import duke.exceptions.DukeException;

public class DateTime {
    private int day;
    private int month;
    private int year;
    private int hour;
    private int min;

    /**
     * Constructor for simple dateTime object.
     * @param dateTime A string in the format DD/MM/YYYY.
     * @throws DukeException Exception triggered if values are invalid or formatting is incorrect.
     */
    public DateTime(String dateTime) throws DukeException {
        try {
            String[] components = dateTime.split(" ");
            String[] dateComponents = components[0].split("/");
            this.day = Integer.parseInt(dateComponents[0]);
            this.month = Integer.parseInt(dateComponents[1]);
            this.year = Integer.parseInt(dateComponents[2]);

            int timeComponents = Integer.parseInt(components[1]);
            this.min = timeComponents % 100;
            this.hour = timeComponents / 100;
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new DateFormatException("Please enter valid dates and times, as DD/MM/YYYY HHMM");
        }

        if ((day < 1 || day > 31) || (month < 1 || month > 12)
                || (year < 0) || (hour < 0 || hour > 23) || (min < 0 || min > 59)) {
            throw new DateValueException("Please enter valid dates and times, as DD/MM/YYYY HHMM");
        }
    }

    /**
     * Converts the dateTime object back to the string version - this can be reused to create an
     * identical dateTime object.
     * @return String equivalent of dateTime object.
     */
    public String toString() {
        String dayStr = (this.day < 10) ? "0" + Integer.toString(this.day) : Integer.toString(this.day);
        String monthStr = (this.month < 10) ? "0" + Integer.toString(this.month) : Integer.toString(this.month);
        String yearStr = (this.year < 10) ? "000" + Integer.toString(this.year)
                : (this.year < 100) ? "00" + Integer.toString(this.year)
                : (this.year < 1000) ? "0" + Integer.toString(this.year) : Integer.toString(this.year);
        String hourStr = (this.hour < 10) ? "0" + Integer.toString(this.hour) : Integer.toString(this.hour);
        String minStr = (this.min < 10) ? "0" + Integer.toString(this.min) : Integer.toString(this.min);
        return dayStr + "/" + monthStr + "/" + yearStr + " " + hourStr + minStr;
    }
}
