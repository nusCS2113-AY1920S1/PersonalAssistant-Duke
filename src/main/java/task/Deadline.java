package task;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Task containing information of a deadline.
 */

public class Deadline extends Task implements Serializable {
    //protected String by;
    private static SimpleDateFormat dataformat = new SimpleDateFormat("dd/MM/yyyy HHmm");

    /**
     * Creates a Deadline instance and initialises the required attributes.
     * @param description Description of the deadline.
     * @param by Deadline of the task in format "dd/MM/yyyy HHmm".
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = null;
        this.type = "D";
        this.inVoice = false;
    }

    /**
     * set the value of inVoice as true.
     */
    public void SetInVoice(){
        this.inVoice = true;
        setBy(inVoice);
    }

//    public void setBy(boolean inVoice){
//        if(inVoice==true){
//            Date date = new Date(System.currentTimeMillis());
//            java.util.Calendar calendar = java.util.Calendar.getInstance();
//            calendar.setTime(date);
//            calendar.add(Calendar.DAY_OF_MONTH,30);
//            Date newDate = calendar.getTime();
//            this.by = dataformat.format(newDate);
//        }
//
//    }

    /**
     * return the boolean value (true or false) of the invoice
     * @return the task's invoice value
     */、

    /**
     * Returns a string status of the Deadline task.
     * @return The task's status icon, description and deadline.
     */
    @Override
    public String giveTask() {
        return "[D]" + super.giveTask() + "(by: " + by + ")";
    }
}
