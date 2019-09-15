package task;

import java.io.Serializable;

/**
 * This extension of the task class will allow the user to add a task of event type.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public class Event extends Task implements Serializable {

    private String date;

    public Event(String description, String date) {
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
        return "[E]" +  "[" + super.getStatusIcon() + "]" + this.description + "(at: " + date + ")";
    }
}
