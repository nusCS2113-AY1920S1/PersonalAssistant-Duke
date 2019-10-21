package Commands;

import Interface.*;
import Tasks.Task;
import Tasks.TaskList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemindCommand extends Command {

    private Task task;
    private Date time;
    private Timer timer;
    private HashMap<Date, Timer> timerHashMap;
    private boolean remind;
    private Reminder reminder;
    private static final Logger LOGGER = Logger.getLogger(RemindCommand.class.getName());

    /**
     * Creates RemindCommand object
     * @param task Task to have a reminder set
     * @param time Time for the reminder to be set at
     * @param remind Whether a reminder needs to be set
     */
    public RemindCommand (Task task, Date time, boolean remind) {
        this.task = task;
        this.time = time;
        timerHashMap = new HashMap<>();
        timer = new Timer();
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
    public String execute(TaskList events, TaskList deadlines, Ui ui, Storage storage) throws DukeException {
        reminder = storage.getReminderObject();
        reminder.setDeadlines(deadlines);
        HashMap<String, HashMap<String, ArrayList<Task>>> deadlineMap = deadlines.getMap();
        HashMap<Date, Task> remindMap = reminder.getRemindMap();
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
        String reminderTime = dateFormat.format(time);
        if (!remind) {
            if (!remindMap.containsKey(time)) {
                throw new DukeException("Sorry, you have no such reminder at that inputted time.");
            } else if (!remindMap.get(time).getDescription().equals(task.getDescription())) {
                throw new DukeException("Sorry, you have no such reminder with inputted description at that time");
            }
            reminder.removeTimerTask(task, time, reminderTime);
            return ui.showCancelReminder(task, reminderTime);
        }
        if (this.time.before(currentDate)) {
            throw new DukeException("Sorry, you cannot set a time that has already passed!");
        } else if (this.time.after(currentDate)) {
            if (timerHashMap.containsKey(time)) {
                Task remindedTask = remindMap.get(time);
                throw new DukeException("Sorry, you have a reminder set for " + remindedTask.getDescription() + " at: " + task.getDateTime());
            } else if (!deadlineMap.containsKey(task.getModCode())) {
                throw new DukeException("Sorry, you have no such mod entered in your deadline table!");
            } else if (!deadlineMap.get(task.getModCode()).containsKey(task.getDate())) {
                throw new DukeException("Sorry, you have no such timing entered in your deadline table!");
            } else {
                ArrayList<Task> allTaskInDate = deadlineMap.get(task.getModCode()).get(task.getDate());
                boolean hasTask = false;
                for (Task taskInList : allTaskInDate) {
                    if (taskInList.getDescription().equals(task.getDescription())) {
                        hasTask = true;
                        break;
                    }
                }
                if (!hasTask) {
                    throw new DukeException("Sorry, there are no such task description in your deadline table!");
                }
            }
        }
        reminder.setReminderThread(time, task);
        return ui.showReminder(task, reminderTime);
    }
}