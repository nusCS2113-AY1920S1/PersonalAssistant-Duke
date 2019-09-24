package task;
import parser.DateTimeExtractor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This extension of the task class will allow the user to add a task of event type.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public class Event extends Task implements Serializable, Comparable<Event>{

    public Event(String description, LocalDateTime toDate, LocalDateTime atDate) {
        super(description);
        this.endDate = toDate;
        this.startDate = atDate;
        this.remindInHowManyDays = 3;
    }
    
    /**
     * custom comparator for sorting
     */
    @Override
    public int compareTo(Event o) {
      return this.startDate.compareTo(o.startDate);
    }

    /**
     * This override of the toString function of the task class etches the different portions of the user input into a
     * single string.
     *
     * @return This function returns a string of the required task in the desired output format of string type.
     */
    @Override
    public String toString() {

        return "[E]" +  "[" + super.getStatusIcon() + "]" + this.description + "(at: "
                + this.startDate.format(DateTimeExtractor.DATE_FORMATTER) + "-" +
                this.endDate.format(DateTimeExtractor.DATE_FORMATTER) + ")";
    }

}
