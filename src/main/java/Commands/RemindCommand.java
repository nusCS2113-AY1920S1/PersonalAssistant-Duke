package Commands;

import Interface.Storage;
import Interface.Ui;
import Tasks.Task;
import Tasks.TaskList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RemindCommand extends Command {

    /**
     * Checks if date of tasks is within the current week.
     */
    public boolean withinWeek(Date date){
        //Solution below adapted from https://stackoverflow.com/questions/23930216/how-to-check-if-the-date-belongs-to-current-week-or-not/23930578#23930578
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.MILLISECOND,0);

        Date startOfWeek = c.getTime();
        Date startOfFollowingWeek = new Date(startOfWeek.getTime()+7*24*60*60*1000);
        if (date.after(startOfWeek) && date.before(startOfFollowingWeek)) {
            return true;
        }
        else return false;
    }

    /**
     * Finds the tasks that are not done and within the current week.
     * @param ui The Ui object to display the done task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display remind message
     * @throws ParseException On date parsing error
     */
    @Override
    public String execute(TaskList todos, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws ParseException {
        TaskList reminder = new TaskList();
        ArrayList<Task> todosList = todos.getList();
        ArrayList<Task> eventsList = events.getList();
        ArrayList<Task> deadlinesList = deadlines.getList();
        ArrayList<Task> temp = new ArrayList<>();
        temp.addAll(todosList);
        temp.addAll(eventsList);
        temp.addAll(deadlinesList);
        for(Task task : temp){
            if(task.toString().contains("[T]") && task.toString().contains("\u2718")){
                reminder.addTask(task);
            }
            else if(task.toString().contains("[D]") && task.toString().contains("\u2718")){
                DateFormat format = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
                Date currentDate = format.parse(task.toString().substring(task.toString().indexOf("by:") + 4, task.toString().indexOf(')')).trim());
                if(withinWeek(currentDate)){
                    reminder.addTask(task);
                }
            }
            else if(task.toString().contains("[E]") && task.toString().contains("\u2718")) {
                DateFormat format = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
                Date currentDate = format.parse(task.toString().substring(task.toString().indexOf("at:") + 4, task.toString().indexOf(')')).trim());
                if(withinWeek(currentDate)){
                    reminder.addTask(task);
                }
            }
        }
        return ui.showReminder(reminder);
    }
}
