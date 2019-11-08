package Commons;
import DukeExceptions.DukeIOException;
import DukeExceptions.DukeInvalidDateTimeException;
import Tasks.Assignment;
import Tasks.TaskList;
import Tasks.Event;
import Tasks.Deadline;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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
    private final Logger LOGGER = DukeLogger.getLogger(Storage.class);
    private HashMap<String, HashMap<String, ArrayList<Assignment>>> map;
    private HashMap<Date, Assignment> reminderMap;

    /**
     * Creates Storage object.
     */
    public Storage(){
        filePath = new File(System.getProperty("user.dir") + File.separator + "data");
        filePath.mkdir();
        filePathEvent = System.getProperty("user.dir") + File.separator + "data" + File.separator + "event.txt";
        filePathDeadline = System.getProperty("user.dir") + File.separator + "data" + File.separator + "deadline.txt";
        reminderMap = new HashMap<>();
        map = new HashMap<>();
    }

    /**
     * Sets the reminder object in Storage as the one in Duke.
     * @param reminder reminder object from duke
     */
    public void setReminderObject(Reminder reminder) {
        this.reminder = reminder;
    }

    /**
     * Retrieves the reminder object from Storage, which is the same as reminder object in Duke.
     */
    public Reminder getReminderObject() {
        return this.reminder;
    }

    public void updateEventList(TaskList list) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(filePathEvent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.severe("event.txt file not found");
        }
        map = list.getMap();
        Set<String> allMods = map.keySet();
        for (String mod : allMods) {
            Set<String> allDates = map.get(mod).keySet();
            for (String date : allDates) {
                ArrayList<Assignment> temp = map.get(mod).get(date);
                for(Assignment task : temp) {
                    outputStream.println(task.toString());
                }
            }
        }
        outputStream.close();
    }

    public void readEventList(TaskList list) throws DukeIOException {
        ArrayList<String> temp = null;
        try {
            File eventFile = new File(filePathEvent);
            eventFile.createNewFile();
            temp = new ArrayList<>(Files.readAllLines(Paths.get(filePathEvent)));
        } catch (IOException e) {
            LOGGER.severe("There is no event.txt to read from");
            throw new DukeIOException("There is no event.txt file to read from. Please create one.");
        }
        for (String string : temp) {
            if (string.isEmpty()) {
                continue;
            }
            Assignment task = stringToTask(string);
            list.addTask(task);
        }
    }

    public void updateDeadlineList(TaskList list) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(filePathDeadline, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.severe("deadline.txt not found");
        }
        map = list.getMap();
        Set<String> allMods = map.keySet();
        for (String mod : allMods) {
            Set<String> allDates = map.get(mod).keySet();
            for (String date : allDates) {
                ArrayList<Assignment> temp = map.get(mod).get(date);
                for(Assignment task : temp) {
                    outputStream.println(task.toString());
                }
            }
        }
        outputStream.close();
    }

    public void readDeadlineList(TaskList list) throws DukeIOException {
        ArrayList<String> temp;
        try {
            File deadlineFile = new File(filePathDeadline);
            deadlineFile.createNewFile();
            temp = new ArrayList<>(Files.readAllLines(Paths.get(filePathDeadline)));
        } catch (IOException e) {
            LOGGER.severe("There is no deadline.txt to read from");
            throw new DukeIOException("There is no deadline.txt file to read from. Please create one.");
        }
        for (String string : temp) {
            DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
            Assignment task = stringToTask(string);
            list.addTask(task);
            if (task.getIsReminder()) {
                Date date = null;
                try {
                    date = dateFormat.parse(task.getRemindTime());
                } catch (ParseException e) {
                    LOGGER.severe("Reminder time is wrongly recorded");
                }
                reminderMap.put(date, task);
            }
        }
    }

    public HashMap<Date, Assignment> getReminderMap() {
        return this.reminderMap;
    }

    private Assignment stringToTask(String string) {
        Assignment line = null;
        try {
            if (string.contains("[D]")) {
                DateFormat format = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy");
                String dateFromData = string.substring(string.indexOf("by:") + 4, string.indexOf(')')).trim();
                String remindTime = string.substring(string.indexOf("[<R") + 3, string.indexOf("/R>]"));
                Date date = format.parse(dateFromData);
                String dateString = dateFormat.format(date);
                String timeString = timeFormat.format(date);
                String modCode = string.substring(0, string.indexOf("[D]") - 1);
                String description = string.substring(string.indexOf("/R>]") + 5, string.indexOf("by:") - 2);
                line = new Deadline(modCode + " " + description, dateString, timeString);
                line.setRemindTime(remindTime);
            } else {
                DateFormat format = new SimpleDateFormat("E dd/MM/yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                String dateFromData = string.substring(string.indexOf("at:") + 4, string.indexOf("time:")).trim();
                String startTimeFromData = string.substring(string.indexOf("time:") + 6, string.indexOf("to:"));
                String endTimeFromData = string.substring(string.indexOf("to:") + 3, string.indexOf(')')).trim();
                Date startTime = timeFormat.parse(startTimeFromData);
                Date endTime = timeFormat.parse(endTimeFromData);
                Date date = format.parse(dateFromData);
                String dateString = format.format(date);
                String startTimeString = timeFormat.format(startTime);
                String endTimeString = timeFormat.format(endTime);
                String modCode = string.substring(0, string.indexOf("[E]") - 1);
                String description = string.substring(string.indexOf("/R>]") + 5, string.indexOf("at:") - 2);
                line = new Event( modCode+ " " + description, dateString, startTimeString, endTimeString);
            }
            if (string.contains("\u2713")) {
                line.setDone(true);
            }
            if (string.contains("[HR]")) {
                line.setReminder(true);
            }
        } catch (ParseException | StringIndexOutOfBoundsException | NullPointerException e) {
            LOGGER.severe("Unable to parse data from event.txt or deadline.txt");
        }
        return line;
    }

    /**
     * Starts the thread on existing reminders set from deadline.txt
     * @throws DukeInvalidDateTimeException On setReminderThread invalid date parameter
     */
    public void setReminderOnStart() throws DukeInvalidDateTimeException {
        Set<Date> dateKey = reminderMap.keySet();
        for(Date date : dateKey) {
            Date remindDate = new Date();
            Date currentDate = new Date();
            Assignment task = reminderMap.get(date);
            String remindTime = task.getRemindTime();
            DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
            try {
                remindDate = dateFormat.parse(remindTime);
            } catch (ParseException e) {
                LOGGER.severe("Reminder date is wrong in deadline.txt. Unable to parse");
            }
            if(remindDate.after(currentDate)) {
                reminder.setReminderThread(remindDate, task);
            }
        }
        if (!reminderMap.isEmpty()) {
            reminder.reminderOnStartAlert();
        }
    }
}
