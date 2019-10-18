package seedu.duke.task.entity;

import org.w3c.dom.Text;
import seedu.duke.CommandParser;
import seedu.duke.Duke;
import seedu.duke.common.command.InvalidCommand;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Super class of all kinds of tasks, with the basic functionality that all tasks share.
 */
public class Task {
    /**
     * The name of the task.
     */
    protected String name;

    /**
     * The flag whether is task is already done. Can only be set from false to true.
     */
    protected boolean isDone;

    /**
     * The type of the task, following the enumeration declared.
     */
    protected TaskType taskType;

    protected String doAfterDescription;

    /**
     * The tag list that the task has.
     */
    protected ArrayList<String> tags;

    /**
     * The priority assigned to the task.
     */
    protected String priority;

    /**
     * A date format that is shared by all tasks to parse and out the date involved in the task.
     */
    protected static DateTimeFormatter format = DateTimeFormatter
            .ofPattern("dd/MM/uuuu HHmm", Locale.ENGLISH)
            .withResolverStyle(ResolverStyle.STRICT);


    /**
     * The enumeration of all task type.
     */
    public enum TaskType {
        ToDo, Deadline, Event
    }

    /**
     * Instantiation of a task with the name and the default false value if isDone attribute.
     *
     * @param name the name of the task
     */
    public Task(String name) {
        this.name = name;
        this.isDone = false;
        this.doAfterDescription = null;
        this.tags = new ArrayList<>();
        this.priority = null;
    }

    /**
     * Marks the isDone as true.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Gets the status whether the task is done.
     *
     * @return the isDone attribute
     */
    public boolean getDone() {
        return this.isDone;
    }

    /**
     * The function that returns a human readable string of the basic information of the task.
     *
     * @return the human readable string of the basic information the task.
     */
    protected String getStatus() {
        if (this.isDone) {
            return "[✓] " + this.name;
        } else {
            return "[✗] " + this.name;
        }
    }

    public TaskType getTaskType() {
        return this.taskType;
    }

    /**
     * Converts the task to a human readable string. This will return the same string as the get status
     * function for a basic task, but is overridden by more advanced task class to add more information.
     *
     * @return a human readable string that contains all important information of a task.
     */
    public String toString() {
        return this.getStatus();
    }

    /**
     * The function returns a string that contains full information of the task which is used to be stored in
     * a file for future usage.
     *
     * @return a string containing full information of the task.
     */
    public String toFileString() {
        return this.toString();
    }

    /**
     * Checks if the input is a short form for a day of the week
     *
     * @param input an input to be checked
     * @return false if the input is not short form or not a day of the week
     */
    public static boolean isCorrectNaturalDate(String input) {
        DayOfWeek[] dayOfWeeks = DayOfWeek.values();
        for (int i = 0; i < dayOfWeeks.length; i++) {
            String day = dayOfWeeks[i].getDisplayName(TextStyle.SHORT, Locale.US);
            if (day.equals(input)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts the input from a string that contains a day of the week to the next nearest date corresponding
     * to the day of the week. If they day has already passed in that week, the next date corresponding to the
     * day will be returned.
     *
     * @param parsedDay    an input that contains the day of the task to be done.
     * @param parsedTiming an input that contains the time of the task to be done.
     * @return dateTime that gives the date and time of the input.
     * @throws CommandParser.UserInputException an exception when the parsing is failed, most likely due to a
     *                                          wrong format
     */
    public static LocalDateTime convertNaturalDate(String parsedDay, String parsedTiming) throws CommandParser.UserInputException {
        LocalDate date = LocalDate.now();
        LocalDateTime dateTime = null;
        try {
            if (parsedTiming == null || parsedTiming.isEmpty()) { //if no timing is inputted, set time as 0000
                dateTime = date.atStartOfDay();
            } else {
                LocalTime timing = LocalTime.parse(parsedTiming, DateTimeFormatter.ofPattern("HHmm"));
                dateTime = date.atTime(timing);
            }
            DayOfWeek dow = dateTime.getDayOfWeek();
            String day = dow.getDisplayName(TextStyle.SHORT, Locale.US);
            while (!day.contains(parsedDay)) {
                dateTime = dateTime.plusDays(1);
                dow = dateTime.getDayOfWeek();
                day = dow.getDisplayName(TextStyle.SHORT, Locale.US);
            }
        } catch (DateTimeParseException e) {
            throw new CommandParser.UserInputException("Wrong Date Time format");
        }
        return dateTime;
    }

    /**
     * The function is used to parse the input string to a Date that is used by the tasks with time involved.
     * The function can be called before the initialization of a Task so that the Data can be directly passed
     * to the constructor.
     *
     * @param dateString an input string to be parsed
     * @return parsed result from the input string
     * @throws CommandParser.UserInputException an exception when the parsing is failed, most likely due to a
     *                                          wrong format
     */
    public static LocalDateTime parseDate(String dateString) throws CommandParser.UserInputException {
        try {
            return LocalDateTime.parse(dateString, format);
        } catch (DateTimeParseException e) {
            throw new CommandParser.UserInputException("Wrong Date Time format");
        }
    }

    /**
     * The function checks whether this task, when converted to string, contains the keyword specified.
     *
     * @param keyword search target string
     * @return a flag whether the keyword is found in the task string
     */
    public boolean matchKeyword(String keyword) {
        return this.toString().contains(keyword);
    }

    /**
     * The default function determining whether the deadline or event is near enough. It will be overridden in
     * deadline or event, but not todo.
     *
     * @param dayLimit the maximum number of days from now for a task to be considered as near
     * @return whether the task is near enough
     */
    public boolean isNear(int dayLimit) {
        return false;
    }

    public void setDoAfterDescription(String description) {
        this.doAfterDescription = description;
    }

    public void snooze() {
    }

    public boolean isClash(Task task) {
        return false;
    }

    public String getName() {
        return name;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getDoAfterDescription() {
        return doAfterDescription;
    }

    public void setPriorityTo(String priority) {
        this.priority = priority;
    }
}
