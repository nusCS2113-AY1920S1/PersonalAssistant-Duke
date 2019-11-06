package oof.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import oof.Ui;
import oof.exception.command.CommandException;
import oof.exception.command.InvalidArgumentException;
import oof.exception.command.MissingArgumentException;
import oof.model.module.SemesterList;
import oof.model.task.Event;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Represents a Command to add Event objects
 * to the TaskList.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "event";
    protected ArrayList<String> arguments;
    static final int INDEX_DESCRIPTION = 0;
    static final int INDEX_DATE_TIME_START = 1;
    static final int INDEX_DATE_TIME_END = 2;
    static final int ARRAY_SIZE_DATE_TIME_START = 2;
    static final int ARRAY_SIZE_DATE_TIME_END = 3;

    /**
     * Constructor for AddEventCommand.
     *
     * @param arguments Command inputted by user.
     */
    public AddEventCommand(ArrayList<String> arguments) {
        super();
        this.arguments = arguments;
    }

    /**
     * Adds an event task to taskList.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws CommandException if user input contains missing or invalid arguments.
     */
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws CommandException {
        if (arguments.get(INDEX_DESCRIPTION).isEmpty()) {
            throw new MissingArgumentException("OOPS!!! The event needs a description.");
        } else if (arguments.size() < ARRAY_SIZE_DATE_TIME_START || arguments.get(INDEX_DATE_TIME_START).isEmpty()) {
            throw new MissingArgumentException("OOPS!!! The event needs a start date.");
        } else if (arguments.size() < ARRAY_SIZE_DATE_TIME_END || arguments.get(INDEX_DATE_TIME_END).isEmpty()) {
            throw new MissingArgumentException("OOPS!!! The event needs an end date.");
        }
        String description = arguments.get(INDEX_DESCRIPTION);
        String startDateTime = parseDateTime(arguments.get(INDEX_DATE_TIME_START));
        String endDateTime = parseDateTime(arguments.get(INDEX_DATE_TIME_END));
        if (exceedsMaxLength(description)) {
            throw new InvalidArgumentException("Task exceeds maximum description length!");
        } else if (!isDateValid(startDateTime)) {
            throw new InvalidArgumentException("OOPS!!! The start date is invalid.");
        } else if (!isDateValid(endDateTime)) {
            throw new InvalidArgumentException("OOPS!!! The end date is invalid.");
        } else {
            ArrayList<Event> eventClashes = checkClashes(taskList, startDateTime, endDateTime);
            ui.printClashWarning(eventClashes);
            Event event = new Event(description, startDateTime, endDateTime);
            taskList.addTask(event);
            ui.addTaskMessage(event, taskList.getSize());
            storageManager.writeTaskList(taskList);
        }
    }

    /**
     * Checks if event being added clashes with other events.
     *
     * @param taskList      Instance of TaskList that stores Task objects
     * @param startDateTime String containing event start date and time
     * @param endDateTime   String containing event end date and time
     * @return ArrayList containing events that clashes with event being added
     * @throws CommandException if start date is after end date or if timestamp is invalid.
     */
    protected ArrayList<Event> checkClashes(TaskList taskList, String startDateTime, String endDateTime)
            throws CommandException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            Date newStartDateTime = format.parse(startDateTime);
            Date newEndDateTime = format.parse(endDateTime);
            if (!isStartDateBeforeEndDate(newStartDateTime, newEndDateTime)) {
                throw new InvalidArgumentException("OOPS!!! The start date cannot be after the end date.");
            }
            return compareEvents(taskList, newStartDateTime, newEndDateTime);
        } catch (ParseException e) {
            throw new InvalidArgumentException("Timestamp given is invalid! Please try again.");
        }
    }

    /**
     * Compares the start date time and end date time of two events.
     *
     * @param taskList         Instance of TaskList that stores Task objects
     * @param newStartDateTime Date object containing event start date and time
     * @param newEndDateTime   Date object containing event end date and time
     * @return ArrayList containing events that clashes with event being added.
     * @throws ParseException if timestamp given is invalid
     */
    protected ArrayList<Event> compareEvents(TaskList taskList, Date newStartDateTime, Date newEndDateTime)
            throws ParseException {
        ArrayList<Event> eventClashes = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        for (Task task : taskList.getTaskList()) {
            if (task instanceof Event) {
                Event event = (Event) task;
                Date currStartDateTime = format.parse(event.getStartDateTime());
                Date currEndDateTime = format.parse(event.getEndDateTime());
                if (isClash(newStartDateTime, newEndDateTime, currStartDateTime, currEndDateTime)) {
                    eventClashes.add(event);
                }
            }
        }
        return eventClashes;
    }

    /**
     * Checks if start and end date are chronologically accurate.
     *
     * @param startTime Start time of event being added.
     * @param endTime   End time of event being added.
     * @return true if start date occurs before end date, false otherwise.
     */
    protected boolean isStartDateBeforeEndDate(Date startTime, Date endTime) {
        return startTime.compareTo(endTime) < 0;
    }

    /**
     * Checks if there is an overlap of event timing.
     *
     * @param newStartTime  Start time of event being added.
     * @param newEndTime    End time of event being added.
     * @param currStartTime Start time of event being compared.
     * @param currEndTime   End time of event being added.
     * @return true if there is an overlap of event timing.
     */
    protected boolean isClash(Date newStartTime, Date newEndTime, Date currStartTime, Date currEndTime) {
        return (newStartTime.compareTo(currStartTime) >= 0 && newStartTime.compareTo(currEndTime) < 0)
                || (newEndTime.compareTo(currStartTime) > 0 && newEndTime.compareTo(currEndTime) <= 0);
    }
}
