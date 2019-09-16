package task;
import exception.DukeException;
import parser.DateTimeExtractor;
import java.io.Serializable;
import java.text.ParseException;

/**
 * This extension of the task class will allow the user to add a task of deadline type.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public class Deadline extends Task implements Serializable{

    private String date;

    public Deadline(String description, String date){
        super(description);
        this.date = date;
    }

    /**
     * This override of the toString function of the task class etches the different portions of the user input into a
     * single string.
     *
     * @return This function returns a string of the required task in the desired output format of string type.
     */
    @Override
    public String toString() {
        String dateToPrint = "ERROR NO DATE";
        try {
            dateToPrint = DateTimeExtractor.extractDateTime(date, "deadline");
        } catch (ParseException e) {
            DukeException.EMPTY_DATE_OR_TIME();
        }
        return "[D]" + "[" + super.getStatusIcon() + "]" + this.description + "(by: " + dateToPrint+ ")";

    }

    @Override
    public String toStringForCheck() {
        return "[D]" + "[" + super.getStatusIcon() + "]" + this.description + "(by: " + date;
    }
}

