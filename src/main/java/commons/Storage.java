package commons;

import dukeexceptions.DukeIOException;
import dukeexceptions.DukeInvalidDateTimeException;
import tasks.Assignment;
import tasks.TaskList;
import tasks.Event;
import tasks.Deadline;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Deals with loading or saving tasks to and from a file.
 */
public class Storage {
    private File filePath;
    private String filePathEvent;
    private String filePathDeadline;
    private Reminder reminder;
    private LookupTable lookupTable = LookupTable.getInstance();
    private final Logger logger = DukeLogger.getLogger(Storage.class);
    private HashMap<String, HashMap<String, ArrayList<Assignment>>> map;
    private HashMap<Date, Assignment> reminderMap;
    private static final int LENGTH_TO_DATE = 4;
    private static final int LENGTH_TO_REMINDER_DATE = 3;
    private static final int LENGTH_TO_DESCRIPTION = 5;
    private static final int LENGTH_TO_START_TIME = 6;
    private static final int LENGTH_TO_END_MODCODE = 1;
    private static final int LENGTH_TO_END_DESCRIPTION = 2;
    private static final int START_OF_DATA_STRING = 0;
    private static final String DEADLINE_DATA_DATE_START_KEYWORD = "by:";
    private static final String EVENT_DATA_DATE_START_KEYWORD = "at:";
    private static final String EVENT_DATA_TIME_START_KEYWORD = "time:";
    private static final String EVENT_DATA_TIME_END_KEYWORD = "to:";

    private final String[] deadlineDelimiter = {"[D]", "by:", "[<R", "/R>]"};
    private final String[] eventDelimiter = {"[E]", "at:", "time:", "[<R", "/R>]", "to:"};

    /**
     * Creates Storage object.
     */
    public Storage() {
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

    /**
     * Saves the TaskList of events into event.txt.
     * @param list TaskList of events
     */
    public void updateEventList(TaskList list) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(filePathEvent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.severe("event.txt file not found");
        }
        map = list.getMap();
        Set<String> allMods = map.keySet();
        for (String mod : allMods) {
            Set<String> allDates = map.get(mod).keySet();
            for (String date : allDates) {
                ArrayList<Assignment> temp = map.get(mod).get(date);
                for (Assignment task : temp) {
                    outputStream.println(task.toString());
                }
            }
        }
        outputStream.close();
    }

    /**
     * Reads and populates the TaskList of events from event.txt.
     * @param list TaskList of events
     * @throws DukeIOException when event.txt is not found
     */
    public void readEventList(TaskList list) throws DukeIOException {
        ArrayList<String> temp;
        try {
            File eventFile = new File(filePathEvent);
            eventFile.createNewFile();
            temp = new ArrayList<>(Files.readAllLines(Paths.get(filePathEvent)));
        } catch (IOException e) {
            logger.severe("There is no event.txt to read from");
            throw new DukeIOException(DukeConstants.NO_EVENT_TXT);
        }
        for (String string : temp) {
            if (string.isEmpty()) {
                continue;
            }
            boolean isValid = true;
            for (int i = 0; i < eventDelimiter.length; i++) {
                if (!string.contains(eventDelimiter[i])) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                Assignment task = stringToTask(string);
                if (task == null) {
                    break;
                }
                list.addTask(task);
            }
        }
    }

    /**
     * Saves the TaskList of deadlines into deadline.txt.
     * @param list TaskList of deadlines
     */
    public void updateDeadlineList(TaskList list) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(filePathDeadline, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.severe("deadline.txt not found");
        }
        map = list.getMap();
        Set<String> allMods = map.keySet();
        for (String mod : allMods) {
            Set<String> allDates = map.get(mod).keySet();
            for (String date : allDates) {
                ArrayList<Assignment> temp = map.get(mod).get(date);
                for (Assignment task : temp) {
                    outputStream.println(task.toString());
                }
            }
        }
        outputStream.close();
    }

    /**
     * Reads and populates the TaskList of deadlines from deadline.txt.
     * @param list TaskList of deadlines
     * @throws DukeIOException when deadline.txt is not found
     */
    public void readDeadlineList(TaskList list) throws DukeIOException {
        ArrayList<String> temp;
        try {
            File deadlineFile = new File(filePathDeadline);
            deadlineFile.createNewFile();
            temp = new ArrayList<>(Files.readAllLines(Paths.get(filePathDeadline)));
        } catch (IOException e) {
            logger.severe("There is no deadline.txt to read from");
            throw new DukeIOException(DukeConstants.NO_DEADLINE_TXT);
        }
        for (String string : temp) {
            boolean isValid = true;
            for (int i = 0; i < deadlineDelimiter.length; i++) {
                if (!string.contains(deadlineDelimiter[i])) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                Assignment task = stringToTask(string);
                if (task == null) {
                    break;
                }
                list.addTask(task);
                if (task.getIsReminder()) {
                    Date date = null;
                    try {
                        date = DukeConstants.DEADLINE_DATE_FORMAT.parse(task.getRemindTime());
                    } catch (ParseException e) {
                        logger.severe("Reminder time is wrongly recorded");
                    }
                    reminderMap.put(date, task);
                }
            }
        }
    }

    /**
     * Retrieves reminderMap.
     */
    public HashMap<Date, Assignment> getReminderMap() {
        return this.reminderMap;
    }

    protected Assignment stringToTask(String string) {
        Assignment line = null;
        try {
            if (string.contains(DukeConstants.DEADLINE_INDICATOR)) {
                String dateFromData = string.substring(string.indexOf(DEADLINE_DATA_DATE_START_KEYWORD)
                        + LENGTH_TO_DATE, string.indexOf(DukeConstants.DATA_TIME_STRING_TERMINATOR)).trim();
                String remindTime = string.substring(string.indexOf(DukeConstants.REMINDER_TIME_START_KEYWORD)
                        + LENGTH_TO_REMINDER_DATE, string.indexOf(DukeConstants.REMINDER_TIME_END_KEYWORD));
                Date date = DukeConstants.DEADLINE_DATE_FORMAT.parse(dateFromData);
                String dateString = DukeConstants.DAY_DATE_FORMAT.format(date);
                String timeString = DukeConstants.TWELVE_HOUR_TIME_FORMAT.format(date);
                String modCode = string.substring(START_OF_DATA_STRING, string.indexOf(DukeConstants.DEADLINE_INDICATOR)
                        - LENGTH_TO_END_MODCODE);
                String description = string.substring(string.indexOf(DukeConstants.REMINDER_TIME_END_KEYWORD)
                        + LENGTH_TO_DESCRIPTION, string.indexOf(DEADLINE_DATA_DATE_START_KEYWORD)
                        - LENGTH_TO_END_DESCRIPTION);
                line = new Deadline(modCode + DukeConstants.BLANK_SPACE + description, dateString, timeString);
                line.setRemindTime(remindTime);
            } else {
                String dateFromData = string.substring(string.indexOf(EVENT_DATA_DATE_START_KEYWORD) + LENGTH_TO_DATE,
                        string.indexOf(EVENT_DATA_TIME_START_KEYWORD)).trim();
                String startTimeFromData = string.substring(string.indexOf(EVENT_DATA_TIME_START_KEYWORD)
                        + LENGTH_TO_START_TIME, string.indexOf(EVENT_DATA_TIME_END_KEYWORD));
                String endTimeFromData = string.substring(string.indexOf(EVENT_DATA_TIME_END_KEYWORD)
                        + LENGTH_TO_REMINDER_DATE, string.indexOf(DukeConstants.DATA_TIME_STRING_TERMINATOR)).trim();
                Date startTime = DukeConstants.TWELVE_HOUR_TIME_FORMAT.parse(startTimeFromData);
                Date endTime = DukeConstants.TWELVE_HOUR_TIME_FORMAT.parse(endTimeFromData);
                Date date = DukeConstants.DAY_DATE_FORMAT.parse(dateFromData);
                String dateString = DukeConstants.DAY_DATE_FORMAT.format(date);
                String startTimeString = DukeConstants.TWELVE_HOUR_TIME_FORMAT.format(startTime);
                String endTimeString = DukeConstants.TWELVE_HOUR_TIME_FORMAT.format(endTime);
                String modCode = string.substring(START_OF_DATA_STRING, string.indexOf(DukeConstants.EVENT_INDICATOR)
                        - LENGTH_TO_END_MODCODE);
                String description = string.substring(string.indexOf(DukeConstants.REMINDER_TIME_END_KEYWORD)
                        + LENGTH_TO_DESCRIPTION, string.indexOf(EVENT_DATA_DATE_START_KEYWORD)
                        - LENGTH_TO_END_DESCRIPTION);
                line = new Event(modCode + DukeConstants.BLANK_SPACE + description, dateString,
                        startTimeString, endTimeString);
            }
            if (string.contains(DukeConstants.DONE_INDICATOR)) {
                line.setDone(true);
            }
            if (string.contains(DukeConstants.HAS_REMINDER_INDICATOR)) {
                line.setReminder(true);
            }
            String taskDateString = line.getDate();
            Date taskDate = DukeConstants.DAY_DATE_FORMAT.parse(taskDateString);
            taskDateString = DukeConstants.EVENT_DATE_INPUT_FORMAT.format(taskDate);
            String nullChecker = lookupTable.getValue(taskDateString);
            if (nullChecker == null) {
                logger.severe("Date does not exist in LookupTable. Check date again");
                return null;
            }
        } catch (ParseException | StringIndexOutOfBoundsException | NullPointerException e) {
            logger.severe("Unable to parse data from event.txt or deadline.txt");
        }
        return line;
    }

    /**
     * Starts the thread on existing reminders set from deadline.txt.
     * @throws DukeInvalidDateTimeException On setReminderThread invalid date parameter
     */
    public void setReminderOnStart() throws DukeInvalidDateTimeException {
        Set<Date> dateKey = reminderMap.keySet();
        for (Date date : dateKey) {
            Date remindDate = new Date();
            Date currentDate = new Date();
            Assignment task = reminderMap.get(date);
            String remindTime = task.getRemindTime();
            try {
                remindDate = DukeConstants.DEADLINE_DATE_FORMAT.parse(remindTime);
            } catch (ParseException e) {
                logger.severe("Reminder date is wrong in deadline.txt. Unable to parse");
            }
            if (remindDate.after(currentDate)) {
                reminder.setReminderThread(remindDate, task);
            }
        }
        if (!reminderMap.isEmpty()) {
            reminder.reminderOnStartAlert();
        }
    }
}
