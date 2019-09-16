package task;
import java.io.Serializable;
import java.util.Date;
/**
 * This extension of the task class will allow the user to add a task of deadline type.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public class Deadline extends Task implements Serializable{

    private Date date;

    public Deadline(String description, Date date){
        super(description);
        this.date = date;
    }
    /**
     * gets date of Deadline
     * 
     * @return the date
     */
    @Override
    public Date getDate() {
      return date;
    }
    /**
     * This override of the toString function of the task class etches the different portions of the user input into a
     * single string.
     *
     * @return This function returns a string of the required task in the desired output format of string type.
     */
    @Override
    public String toString() {
        return "[D]" + "[" + super.getStatusIcon() + "]" + this.description + "(by: " + date.toString() + ")";
    }
}

