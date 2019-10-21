package Interface;
import Tasks.*;
import javafx.application.Platform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Deals with loading or saving tasks to and from a file.
 */
public class Storage {
    private File filePath;
    private String filePathEvent;
    private String filePathDeadline;
    private Reminder reminder;
    private static final Logger LOGGER = Logger.getLogger(Storage.class.getName());
    private HashMap<String, HashMap<String, ArrayList<Task>>> map;
    private HashMap<Date, Task> reminderMap;

    /**
     * Creates Storage object.
     */
    public Storage(){
        filePath = new File(System.getProperty("user.dir") + "\\data");
        filePath.mkdir();
        filePathEvent = System.getProperty("user.dir") + "\\data\\event.txt";
        filePathDeadline = System.getProperty("user.dir") + "\\data\\deadline.txt";
        reminderMap = new HashMap<>();
        map = new HashMap<>();
    }

    public void setReminderObject(Reminder reminder) {
        this.reminder = reminder;
    }

    public Reminder getReminderObject() {
        return this.reminder;
    }

    public void updateEventList(TaskList list) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(filePathEvent);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        map = list.getMap();
        Set<String> allMods = map.keySet();
        for (String mod : allMods) {
            Set<String> allDates = map.get(mod).keySet();
            for (String date : allDates) {
                ArrayList<Task> temp = map.get(mod).get(date);
                for(Task task : temp) {
                    assert outputStream != null;
                    outputStream.println(task.toString());
                }
            }
        }
        assert outputStream != null;
        outputStream.close();
    }

    public void readEventList(TaskList list) {
        ArrayList<String> temp = null;
        try {
            temp = new ArrayList<>(Files.readAllLines(Paths.get(filePathEvent)));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        for (String string : temp) {
            if (string.isEmpty()) {
                continue;
            }
            Task task = stringToTask(string);
            list.addTask(task);
        }
    }

    public void updateDeadlineList(TaskList list) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(filePathDeadline);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        map = list.getMap();
        Set<String> allMods = map.keySet();
        for (String mod : allMods) {
            Set<String> allDates = map.get(mod).keySet();
            for (String date : allDates) {
                ArrayList<Task> temp = map.get(mod).get(date);
                for(Task task : temp) {
                    assert outputStream != null;
                    outputStream.println(task.toString());
                }
            }
        }
        assert outputStream != null;
        outputStream.close();
    }

    public void readDeadlineList(TaskList list) {
        ArrayList<String> temp = null;
        try {
            temp = new ArrayList<>(Files.readAllLines(Paths.get(filePathDeadline)));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        assert temp != null;
        for (String string : temp) {
            DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
            Task task = stringToTask(string);
            list.addTask(task);
            if (task.getIsReminder()) {
                Date date = null;
                try {
                    date = dateFormat.parse(task.getRemindTime());
                } catch (ParseException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }
                reminderMap.put(date, task);
            }
        }
    }

    public HashMap<Date, Task> getReminderMap() {
        return this.reminderMap;
    }

    private static Task stringToTask(String string) {
        Task line = null;
        try {
            if (string.contains("[D]")) {
                DateFormat format = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
                Date date = format.parse(string.substring(string.indexOf("by:") + 4, string.indexOf(')')).trim());
                String remindTime = string.substring(string.indexOf("[<R") + 3, string.indexOf("/R>]"));
                DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy");
                String dateString = dateFormat.format(date);
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                String timeString = timeFormat.format(date);
                line = new Deadline(string.substring(0, string.indexOf("[D]") - 1) + " " + string.substring(string.indexOf("/R>]") + 5, string.indexOf("by:") - 2), dateString, timeString);
                line.setRemindTime(remindTime);
            } else {
                DateFormat format = new SimpleDateFormat("E dd/MM/yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                Date date = format.parse(string.substring(string.indexOf("at:") + 4, string.indexOf("time:")).trim());
                String dateString = format.format(date);
                Date startTime = timeFormat.parse(string.substring(string.indexOf("time:") + 6, string.indexOf("to")));
                String startTimeString = timeFormat.format(startTime);
                Date endTime = timeFormat.parse(string.substring(string.indexOf("to") + 3, string.indexOf(')')).trim());
                String endTimeString = timeFormat.format(endTime);
                line = new Event(string.substring(0, string.indexOf("[E]") - 1) + " " + string.substring(string.indexOf("/R>]") + 5, string.indexOf("at:") - 2), dateString, startTimeString, endTimeString);
            }
            if (string.contains("\u2713")) {
                line.setDone(true);
            }
            if (string.contains("[HR]")) {
                line.setReminder(true);
            }
        } catch (ParseException | StringIndexOutOfBoundsException | NullPointerException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        return line;
    }

    public void setReminderOnStart() throws DukeException {
        Set<Date> dateKey = reminderMap.keySet();
        for(Date date : dateKey) {
            Date remindDate = new Date();
            Date currentDate = new Date();
            Task task = reminderMap.get(date);
            String remindTime = task.getRemindTime();
            DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
            try {
                remindDate = dateFormat.parse(remindTime);
            } catch (ParseException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
            if(remindDate.after(currentDate)) {
                reminder.setReminderThread(remindDate, task);
            }
        }
    }
}
