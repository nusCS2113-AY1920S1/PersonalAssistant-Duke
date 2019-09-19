package command;


import process.DukeException;
import process.Storage;
import process.Ui;
import task.Deadline;
import task.Event;
import task.Task;
import task.TaskList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
/*
    Represents a list of the task that needs to be reminded
 */
public class ReminderCommand extends Command {



    private int DaysUntilDeadline;
    public ReminderCommand(int days) {
        this.DaysUntilDeadline = days;
    }
    public ReminderCommand(){
        this.DaysUntilDeadline = 7;  //if not used to check
    }

    /**
     * Executes the ReminderCommand
     */
    public String execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {

        if(tasks.size() <= 0) throw new DukeException(( "There are no Reminders"));

        String output = " Reminder List";

        //Check the current date
        DateFormat df = new SimpleDateFormat(" dd/M/yyyy hhmm");
        Date CurrentDate = new Date();
        System.out.println(df.format(CurrentDate));

        int ctr = 0;
        Iterator<Task> itr = tasks.iterator();
        while (itr.hasNext()) {

            Task element = itr.next();
            if (element instanceof Deadline) {

                Date DueDate = ((Deadline) element).by;
                long differenceDays = getDifferenceDays(CurrentDate,DueDate); //gets the difference in date

                if( differenceDays < this.DaysUntilDeadline){

                    output += "\n" + "\t" + ("[" + Integer.toString(ctr + 1) + "] :");
                    output += element.toString();
                    output += (" |-> There are " +  differenceDays + " days left to complete the task");
                    ctr ++;
                }

            } else if (element instanceof Event) {

                Date DueDate = ((Event) element).at;
                long differenceDays = getDifferenceDays(CurrentDate,DueDate);
                if(differenceDays  < this.DaysUntilDeadline){

                    output += "\n" + "\t" + ("[" + Integer.toString(ctr + 1) + "] :");
                    output += element.toString();
                    output += (" |-> There are " +  differenceDays + " days left to complete the task");
                    ctr ++;
                }
            }
            else{

                output += "\n" + "\t" + ("[" + Integer.toString(ctr + 1) + "] :");
                output += element.toString();
                ctr ++;
            }
        }
        ui.print_this(output);
        return output; //such a pointless return statement
    }


    /**
     * Gets the difference in the number of days
     * @param d1 date1
     * @param d2 date2
     * @return difference between the two days
     */
    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

}
