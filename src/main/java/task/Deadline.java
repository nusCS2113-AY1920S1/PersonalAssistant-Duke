package task;
import parser.DateTimeExtractor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This extension of the task class will allow the user to add a task of deadline type.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public class Deadline extends Task implements Serializable{

    public Deadline(String description, LocalDateTime atDate){
        super(description);
        this.atDate = atDate;
    }

    /**
     * This override of the toString function of the task class etches the different portions of the user input into a
     * single string.
     *
     * @return This function returns a string of the required task in the desired output format of string type.
     */
    @Override
    public String toString() {
        return "[D]" + "[" + super.getStatusIcon() + "]" + this.description + "(by: "
                + this.atDate.format(DateTimeExtractor.DATE_FORMATTER);
    }

}

