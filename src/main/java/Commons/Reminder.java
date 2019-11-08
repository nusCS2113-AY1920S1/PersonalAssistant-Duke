package Commons;

import DukeExceptions.DukeException;
import DukeExceptions.DukeInvalidDateTimeException;
import UserInterface.AlertBox;
import Tasks.Assignment;
import Tasks.TaskList;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.HashMap;
import java.util.TimerTask;

/**
 * This class sets or removes a reminder thread.
 */
public class Reminder {

    private Timer timer;
    private HashMap<Date, Timer> timerMap;
    private HashMap<Date, Assignment> remindMap;
    private Storage storage;
    private Image img;
    private TaskList deadlines;

    /**
     * Creates reminder object.
     */
    public Reminder() {
        timerMap = new HashMap<>();
        storage = new Storage();
        remindMap = storage.getReminderMap();
    }

    /**
     * Removes thread and sets the reminder to false for update.
     * @param task Task with reminder set
     * @param date Date of reminder
     * @param reminderTime String version of the date of reminder
     */
    public void removeTimerTask(Assignment task, Date date, String reminderTime) {
        timerMap.get(date).cancel();
        timerMap.remove(date);
        deadlines.setReminder(task, reminderTime, false);
        storage.updateDeadlineList(deadlines);
        remindMap.remove(date);
    }

    public void setDeadlines(TaskList deadlines) {
        this.deadlines = deadlines;
    }

    public HashMap<Date, Assignment> getRemindMap() {
        return this.remindMap;
    }

    public void reminderOnStartAlert() {
        AlertBox.display("Reminder!!!", "",
                "Your previous reminders have been automatically set. To see more, please type remind/check",
                Alert.AlertType.INFORMATION);
    }

    /**
     * Creates a new thread when a reminder is set.
     * @param date The time set for the thread to run
     * @param task The task where the reminder would be set
     * @throws DukeException On invalid date parameter
     */
    public void setReminderThread(Date date, Assignment task) throws DukeInvalidDateTimeException {
        if (timerMap.containsKey(date)) {
            throw new DukeInvalidDateTimeException("You already have a reminder set at that time. Please remove reminder and set again");
        }
        img = new Image("/images/DaDuke.png");
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
        String reminderTime = dateFormat.format(date);
        long seconds = date.getTime() - currentDate.getTime();
        deadlines.setReminder(task, reminderTime, true);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    Stage owner = new Stage(StageStyle.TRANSPARENT);
                    StackPane root = new StackPane();
                    root.setStyle("-fx-background-color: TRANSPARENT");
                    Scene scene = new Scene(root, 1, 1);
                    scene.setFill(Color.TRANSPARENT);
                    owner.setScene(scene);
                    owner.setWidth(1);
                    owner.setHeight(1);
                    owner.toBack();
                    owner.show();
                    Notifications notificationBuilder = Notifications.create()
                            .title("REMINDER!!!")
                            .graphic(new ImageView(img))
                            .text(task.getModCode() + " " + task.getDescription() + "\n" + task.getDateTime())
                            .darkStyle()
                            .position(Pos.BOTTOM_RIGHT)
                            .hideAfter(Duration.seconds(20))
                            .onAction(event -> {
                                owner.close();
                            });
                    notificationBuilder.show();
                    AlertBox.display("Reminder!!!", "",
                            "Please be reminded you have this task: " + task.getDescription() + " For mod: " + task.getModCode() + " by: " + task.getDateTime(),
                            Alert.AlertType.INFORMATION);
                    timer.cancel();
                    deadlines.setReminder(task, reminderTime, false);
                    remindMap.remove(date);
                    storage.updateDeadlineList(deadlines);
                });
            }
        }, seconds);
        timerMap.put(date, timer);
        remindMap.put(date, task);
        storage.updateDeadlineList(deadlines);
    }
}
