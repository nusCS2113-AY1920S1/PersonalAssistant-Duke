import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Represents a task.  <code>Task</code> is an abstract class that can not be
 * instantiated
 */
public abstract class Task {
    /**
     * A String that represents the description of the task.
     */
    protected String description;
    /**
     * A boolean that represents the status of the task( 1 means done, 0 means not yet)
     */
    protected boolean isDone;
    /**
     * a localDateTime constructor to save the date and time
     */
    protected LocalDateTime ld;

    /**
     * Initialises the minimum fields required to setup a <code>Task</code>.
     * @param description A string that represents the description of certain task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    /**
     * Returns an icon that represents the status of the task.
     * @return Tick if completed, cross if uncompleted.
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Returns a string with the following format to be read from a local file.
     * @return A string in a specific format to be read from a local file.
     */
    abstract String txtFormat();
    /**
     * Returns a string with the following format to be stored in a local file
     * @return A string in a specific format to be stored in a local file.
     */
    abstract String writeTxt();
    /**
     * Marks the task as done.
     */
    public void markAsDone()
    {
        isDone = true;
    }
    /**
     * Returns a string with the status icon and the description of the task.
     * @return A string in a specific format with the status and description of the task.
     */
    public String printStatus()
    {
        return "[" + this.getStatusIcon() + "] " + description;
    }
    /**
     * Returns the description of the task.
     * @return A string that represents the specific activity associated with
     *          the task.
     */
    public String getDescription(){
        return description;
    }

    public String timeFormatter(String timeBeforeFormat)  {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        DateTimeFormatter stFormatter = DateTimeFormatter.ofPattern("d'st of' MMMM yyyy , ha");
        DateTimeFormatter ndFormatter = DateTimeFormatter.ofPattern("d'nd of' MMMM yyyy , ha");
        DateTimeFormatter rdFormatter = DateTimeFormatter.ofPattern("d'rd of' MMMM yyyy , ha");
        DateTimeFormatter thFormatter = DateTimeFormatter.ofPattern("d'th of' MMMM yyyy , ha");

        ld = LocalDateTime.parse(timeBeforeFormat,parser);

        String output;

        if ((ld.getDayOfMonth()%10) == 1){
            output = ld.format(stFormatter);
        }else if ((ld.getDayOfMonth()%10) == 2) {
            output = ld.format(ndFormatter);
        }else if ((ld.getDayOfMonth()%10) == 3) {
            output = ld.format(rdFormatter);
        }else{
            output = ld.format(thFormatter);;
        }

        return output;

    }


}