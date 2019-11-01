package logic;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import common.LoggerController;
import core.Duke;
import gui.Window;
import model.Model;

//@@author AugGust
public class ReminderController {
    private static ReminderController instance;
    private Model model;
    private Thread reminderThread;
    private ReminderProcessor reminderProcessor;
    private Reminder activeReminder;
    private boolean isMissed = false;

    /**
     * Creates a new ReminderController
     *
     * @param model Model of the saved data
     */
    public ReminderController(Model model) {
        this.model = model;
        instance = this;
        refreshReminders();
    }

    /**
     * Sends a reminder to the user about the currently active reminder.
     * To be called by the reminder thread
     */
    protected static void remindNow(boolean missed) {
        String reminder;
        if (missed) {
            reminder = "You have a missed Reminder!\n";
        } else {
            reminder = "Reminder!\n";
            ReminderController.instance.playDing();
        }
        int taskIndex = ReminderController.instance.activeReminder.getIndex();
        reminder += ReminderController.instance.model.getTasksManager().getTaskById(taskIndex).toString() + '\n';
        Window.instance.setOutputArea(reminder);
        ReminderController.instance.model.getTasksManager().getTaskById(taskIndex).setReminder(null);
        ReminderController.instance.model.save();
        refreshAllReminders();
    }

    private void playDing() {
        try {
            InputStream is = Duke.class.getClassLoader().getResourceAsStream("ding.wav");
            InputStream bufferedIs = new BufferedInputStream(is);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIs);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            LoggerController.logWarning(getClass(), "Unable to play sound effect. JAR file might be corrupted!");
        }
    }

    /**
     * Reloads the reminders. Kills the reminders thread and sleeps it until time for next reminder
     */
    public void refreshReminders() {
        if (reminderThread != null) {
            reminderThread.interrupt();
        }

        activeReminder = getEarliestReminder();
        if (activeReminder == null) {
            return;
        }

        //Missed reminder
        if (activeReminder.getTime().before(new Date())) {
            isMissed = true;
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MILLISECOND, 200);
            reminderProcessor = new ReminderProcessor(cal.getTime());
        } else {
            isMissed = false;
            reminderProcessor = new ReminderProcessor(activeReminder.getTime());
        }

        reminderThread = new Thread(reminderProcessor);
        reminderThread.start();
    }

    /**
     * Gets the earliest reminder in all tasks in the model
     *
     * @return
     */
    public Reminder getEarliestReminder() {
        Reminder earliest = null;

        for (int i = 0; i < model.getTaskList().size(); i++) {
            if (model.getTaskList().get(i).getReminder() != null) {
                if (earliest == null) {
                    earliest = new Reminder(model.getTaskList().get(i).getReminder(), i);
                } else {
                    if (earliest.getTime().after(model.getTaskList().get(i).getReminder())) {
                        earliest = new Reminder(model.getTaskList().get(i).getReminder(), i);
                    }
                }
            }
        }

        return earliest;
    }

    /**
     * To refresh state of reminders, to be called statically
     */
    public static void refreshAllReminders() {
        instance.refreshReminders();
    }

    class ReminderProcessor implements Runnable {
        private long sleepDuration;

        public ReminderProcessor(Date timeToWake) {
            sleepDuration = timeToWake.getTime() - new Date().getTime();
        }

        @Override
        public void run() {
            LoggerController.logInfo(this.getClass(), "Started new reminder thread");
            LoggerController.logInfo(this.getClass(), "Sleeping for " + sleepDuration + " milliseconds");
            try {
                Thread.sleep(sleepDuration);
                while (!Window.instance.initialized) {
                    Thread.sleep(100);
                }
                ReminderController.remindNow(isMissed);

            } catch (InterruptedException e) {
                LoggerController.logInfo(this.getClass(), "Terminated reminder thread");
                return;
            }
        }

    }

    class Reminder {
        Date reminderTime;
        int index;

        /**
         * Creates new reminder object
         *
         * @param reminderTime time to be reminded
         * @param index        index of task to be reminded
         */
        public Reminder(Date reminderTime, int index) {
            this.reminderTime = reminderTime;
            this.index = index;
        }

        /**
         * gets the time of the reminder
         *
         * @return time of reminder
         */
        public Date getTime() {
            return this.reminderTime;
        }

        /**
         * gets the index of the task for the reminder
         *
         * @return index of task
         */
        public int getIndex() {
            return this.index;
        }
    }
}
