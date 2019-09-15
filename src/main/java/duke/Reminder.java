package duke;

import duke.core.TaskList;
import duke.core.Ui;
import duke.task.Task;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Reminder implements Runnable {
    /**
     * A constant represents the seconds in a day.
     */
    static final int SECONDS_IN_A_DAY = 86400;
    /**
     * A TaskList object that deals with add, delete, mark as done,
     * find functions of a list of tasks.
     */
    private TaskList tasks;
    /**
     * A Ui object that deals with interactions with the user.
     */
    private  Ui ui;
    /**
     * Constructs a Reminder object with a TaskList and Ui object.
     * @param returnTask A TaskList object that deals with add, delete and other functions.
     * @param returnUi A Ui object that deals with interactions with the user.
     */
    public Reminder(TaskList returnTask, Ui returnUi) {
        this.tasks = returnTask;
        this.ui = returnUi;
    }

    /**
     * Execute the reminder program.
     * The methods inside will be triggered base on the Timer's time interval.
     */
    public void run() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                ArrayList<Task> tempTask = new ArrayList<>();
                for (Task v : tasks.fullTaskList()) {
                    if (!(v.getDateTime() == null))
                    {
                        int seconds = (int) ChronoUnit.SECONDS.between(v.getDateTime(), LocalDateTime.now());
                        if ((!v.isDone()) && seconds < SECONDS_IN_A_DAY) {
                            tempTask.add(v);
                        }
                    }
                }
                if (!tempTask.isEmpty()) {
                    ui.taskReminder(tempTask);
                }
            }
        };

        Timer timer = new Timer("Timer");
        long delay = 20000L;
        long period = 20000L;
        timer.scheduleAtFixedRate(timerTask, delay, period);
    }

}
