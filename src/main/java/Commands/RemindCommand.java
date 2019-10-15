package Commands;

import Interface.DukeException;
import Interface.Storage;
import Interface.Ui;
import Tasks.Task;
import Tasks.TaskList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.Notifications;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RemindCommand extends Command {

    private Task task;
    private Date time;
    private Timer timer;
    private HashMap<Date, Timer> timerHashMap;
    private Image img;
    private boolean remind;

    public RemindCommand (Task task, Date time, boolean remind) {
        this.task = task;
        this.time = time;
        timerHashMap = new HashMap<>();
        timer = new Timer();
        img = new Image("/images/DaDuke.png");
        this.remind = remind;
    }

    /**
     * Sets a reminder pop-up for task user wants to set a reminder to.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the done task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display remind message
     * @throws ParseException On date parsing error
     */
    @Override
    public String execute(TaskList events, TaskList deadlines, Ui ui, Storage storage) throws DukeException, FileNotFoundException {
        HashMap<String, HashMap<String, ArrayList<Task>>> deadlineMap = deadlines.getMap();
        HashMap<Date, Task> reminderMap = storage.getReminderMap();
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
        String reminderTime = dateFormat.format(time);
        if (!remind) {
            timer = timerHashMap.get(time);
            timer.cancel();
            timerHashMap.remove(time);
            return ui.showCancelReminder(task, reminderTime);
        }
        if (this.time.before(currentDate)) {
            throw new DukeException("Sorry, you cannot set a time that has already passed!");
        } else if (this.time.after(currentDate)) {
            long seconds = time.getTime() - currentDate.getTime();
            if (timerHashMap.containsKey(time)) {
                Task remindedTask = reminderMap.get(time);
                throw new DukeException("Sorry, you have a reminder set for " + remindedTask.getDescription() + " at: " + task.getDateTime());
            } else if (!deadlineMap.containsKey(task.getModCode())) {
                throw new DukeException("Sorry, you have no such mod entered in your deadline table!");
            } else if (!deadlineMap.get(task.getModCode()).containsKey(task.getDateTime())) {
                throw new DukeException("Sorry, you have no such timing entered in your deadline table!");
            } else {
                ArrayList<Task> allTaskInDate = deadlineMap.get(task.getModCode()).get(task.getDateTime());
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
            deadlines.setReminder(task , reminderTime, remind);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Notifications notificationBuilder = Notifications.create()
                            .title("REMINDER!!!")
                            .graphic(new ImageView(img))
                            .text(task.getModCode() + " " + task.getDescription() + "\n" + task.getDateTime())
                            .darkStyle()
                            .position(Pos.BOTTOM_RIGHT);
                    notificationBuilder.show();
                    timer.cancel();
                    deadlines.setReminder(task , reminderTime, false);
                }
            }, seconds);
            timerHashMap.put(time, timer);
        }
        storage.updateDeadlineList(deadlines);
        return ui.showReminder(task, reminderTime);
    }
}