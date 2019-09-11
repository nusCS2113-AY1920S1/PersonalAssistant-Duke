/**
 * Deadline object inherits Task.
 * Is a type of task available for use.
 */
public class Deadline extends Task{
    /**
     * Contains the date & time in a String.
     */
    protected String date;

    /**
     * Creates deadline
     * @param description Description of task.
     * @param date Deadline date & time.
     */
    public Deadline(String description, String date){
        super(description);
        this.date = date;
    }

    /**
     * Creates deadline with boolean attached, so as to read from file correctly.
     * @param description Description of task.
     * @param date Deadline date & time.
     * @param isDone Boolean defining if the task is completed or not.
     */
    public Deadline(String description, String date, boolean isDone){
        super(description, isDone);
        this.date = date;
    }

    /**
     * Converts deadline type task to string format for printing.
     * @return Formatted string representing the deadline and its date.
     */
    @Override
    public String toString(){
        return "[D]" + super.toString() + "(by: " + date + ")";
    }
}
