package Commands;

import DukeExceptions.DukeException;
import DukeExceptions.DukeInvalidDateTimeException;
import Commons.Reminder;
import Commons.Storage;
import Commons.UserInteraction;
import Parser.DateTimeParser;
import Tasks.Assignment;
import Tasks.TaskList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Represents the command to set a reminder for the user.
 */
public class RemindCommand extends Command {

    private Assignment task;
    private Date time;
    private boolean remind;
    private Reminder reminder;

    /**
     * Creates RemindCommand object.
     * @param task Task to have a reminder set
     * @param time Time for the reminder to be set at
     * @param remind Whether a reminder needs to be set
     */
    public RemindCommand(Assignment task, Date time, boolean remind) {
        this.task = task;
        this.time = time;
        this.remind = remind;
    }

    /**
     * Sets a reminder pop-up for task user wants to set a reminder to.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the done task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display remind message
     * @throws DukeException On invalid task and time input
     */
    @Override
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage) throws Exception {
        reminder = storage.getReminderObject();
        reminder.setDeadlines(deadlines);
        HashMap<String, HashMap<String, ArrayList<Assignment>>> deadlineMap = deadlines.getMap();
        HashMap<Date, Assignment> remindMap = reminder.getRemindMap();
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
        if (task.getDescription().isEmpty()) {
            ArrayList<String> remindList = new ArrayList<>();
            for (Date date : remindMap.keySet()) {
                String reminderDate = dateFormat.format(date);
                String modCode = remindMap.get(date).getModCode();
                String description = remindMap.get(date).getDescription();
                String taskDate = remindMap.get(date).getDateTime();
                remindList.add("ModCode: " + modCode + "\n" + "Description: " + description + "\n"
                        + "Deadline date: " + taskDate + "\n" + "Reminder date: " + reminderDate);
            }
            return ui.showListOfReminder(remindList);
        }
        String reminderTime = dateFormat.format(time);
        String taskDateTimeString = task.getDateTime();
        Date taskDateTime = DateTimeParser.deadlineTaskStringToDate(taskDateTimeString);
        if (taskDateTime.before(currentDate)) {
            throw new DukeInvalidDateTimeException("Sorry, your selected task has already passed!");
        }
        if (!remind) {
            if (!remindMap.containsKey(time)) {
                throw new DukeInvalidDateTimeException("Sorry, you have no such reminder at that inputted time.");
            } else if (!remindMap.get(time).getDescription().equals(task.getDescription())) {
                throw new DukeInvalidDateTimeException("Sorry, you have no such reminder with inputted description at that time.");
            } else if (!remindMap.get(time).getDateTime().equals(task.getDateTime())) {
                throw new DukeInvalidDateTimeException("Sorry you have no such reminder task with inputted date and time.");
            }
            reminder.removeTimerTask(task, time, reminderTime);
            return ui.showCancelReminder(task, reminderTime);
        }
        if (this.time.before(currentDate)) {
            throw new DukeInvalidDateTimeException("Sorry, you cannot set a time that has already passed!");
        } else if (!deadlineMap.containsKey(task.getModCode())) {
            throw new DukeException("Sorry, you have no such mod entered in your deadline table!");
        } else if (!deadlineMap.get(task.getModCode()).containsKey(task.getDate())) {
            throw new DukeException("Sorry, you have no such timing entered in your deadline table!");
        } else if (remindMap.containsKey(time)) {
            Assignment remindedTask = remindMap.get(time);
            throw new DukeInvalidDateTimeException("Sorry, you have a reminder set for " + remindedTask.getModCode() + " " + remindedTask.getDescription() + " by: " + task.getDateTime());
        } else {
            Date inputTaskDate = dateFormat.parse(task.getDateTime());
            if (inputTaskDate.before(time)) {
                throw new DukeInvalidDateTimeException("Sorry, you cannot set a reminder after the date of the task.");
            }
            ArrayList<Assignment> allTaskInDate = deadlineMap.get(task.getModCode()).get(task.getDate());
            boolean hasTask = false;
            for (Assignment taskInList : allTaskInDate) {
                if (taskInList.getDescription().equals(task.getDescription())) {
                    hasTask = true;
                    break;
                }
            }
            if (!hasTask) {
                throw new DukeException("Sorry, there are no such task description in your deadline table!");
            }
        }
        reminder.setReminderThread(time, task);
        return ui.showReminder(task, reminderTime);
    }
}