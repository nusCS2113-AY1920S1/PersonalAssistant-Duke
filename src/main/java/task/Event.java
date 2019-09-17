package task;
import exception.DukeException;
import parser.DateTimeExtractor;

import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * This extension of the task class will allow the user to add a task of event type.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public class Event extends Task implements Serializable{

    private String date;

    public Event(String description, LocalDateTime toDate, LocalDateTime fromDate) {
        super(description);
        this.toDate = toDate;
        this.fromDate = fromDate;
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
                + this.fromDate.format(DateTimeExtractor.DATE_FORMATTER) + "-" +
                this.toDate.format(DateTimeExtractor.DATE_FORMATTER) + ")";
    }

}
