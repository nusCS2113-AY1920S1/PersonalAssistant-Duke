package Interface;
import Tasks.*;

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

/**
 * Deals with loading or saving tasks to and from a file.
 */
public class Storage {
    private File filePath;
    private String filePathEvent;
    private String filePathDeadline;
    private Reminder reminder;

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

    public void updateEventList(TaskList list) throws FileNotFoundException {
        PrintWriter outputStream = new PrintWriter(filePathEvent);
        map = list.getMap();
        Set<String> allMods = map.keySet();
        for (String mod : allMods) {
            Set<String> allDates = map.get(mod).keySet();
            for (String date : allDates) {
                ArrayList<Task> temp = map.get(mod).get(date);
                for(Task task : temp) {
                    outputStream.println(task.toString());
                }
            }
        }
        outputStream.close();
    }

    public void readEventList(TaskList list) throws IOException, ParseException {
        ArrayList<String> temp = new ArrayList<>(Files.readAllLines(Paths.get(filePathEvent)));
        for (String string : temp) {
            Task task = stringToTask(string);
            list.addTask(task);
        }
    }

    public void updateDeadlineList(TaskList list) throws FileNotFoundException {
        PrintWriter outputStream = new PrintWriter(filePathDeadline);
        map = list.getMap();
        Set<String> allMods = map.keySet();
        for (String mod : allMods) {
            Set<String> allDates = map.get(mod).keySet();
            for (String date : allDates) {
                ArrayList<Task> temp = map.get(mod).get(date);
                for(Task task : temp) {
                    outputStream.println(task.toString());
                }
            }
        }
        outputStream.close();
    }

    public void readDeadlineList(TaskList list) throws IOException, ParseException {
        ArrayList<String> temp = new ArrayList<>(Files.readAllLines(Paths.get(filePathDeadline)));
        for (String string : temp) {
            DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
            Task task = stringToTask(string);
            list.addTask(task);
            if (task.getIsReminder()) {
                Date date = dateFormat.parse(task.getRemindTime());
                reminderMap.put(date, task);
            }
        }
    }

    public HashMap<Date, Task> getReminderMap() {
        return this.reminderMap;
    }

    private static Task stringToTask(String string) throws ParseException {
        if (string.trim().isEmpty()) {

        }
        Task line;
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
            Date date = format.parse(string.substring(string.indexOf("at:") + 4, string.indexOf("time:")));
            String dateString = format.format(date);
            Date startTime = timeFormat.parse(string.substring(string.indexOf("time:") + 6, string.indexOf("to")));
            String startTimeString = timeFormat.format(startTime);
            Date endTime = timeFormat.parse(string.substring(string.indexOf("to") + 3, string.indexOf(')')).trim());
            String endTimeString = timeFormat.format(endTime);
            line = new Event(string.substring(0, string.indexOf("[E]") - 1) + " " + string.substring(string.indexOf("/R>]") + 5, string.indexOf("at:")-2), dateString, startTimeString, endTimeString);
        }
        if(string.contains("\u2713")) {
            line.setDone(true);
        }
        if (string.contains("[HR]")) {
            line.setReminder(true);
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
                e.printStackTrace();
            }
            if(remindDate.after(currentDate)) {
                reminder.setReminderThread(remindDate, task);
            }
        }
    }
}
