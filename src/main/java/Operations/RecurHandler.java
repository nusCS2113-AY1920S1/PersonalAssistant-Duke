package Operations;

import CustomExceptions.RoomShareException;
import Enums.RecurrenceScheduleType;
import Model_Classes.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * This class deals with operations for Recurring Tasks
 * will perform operations such as add, list, find
 * also checks for recurrence of tasks
 */
public class RecurHandler {
    public static final String DATE_ERROR_SET_AS_NOT_DONE = "Error in parsing date, will be setting the task to not done instead";
    private TaskList taskList;
    private Parser parser = new Parser();

    /**
     * Constructor for RecurHandler class
     * @param recurringList The TaskList to be operated on using RecurHandler
     */
    public RecurHandler(TaskList recurringList) {
        this.taskList = recurringList;
    }
    /**
     * Checks for recurrences based on the date.
     * if there is a recurrence, replaces the old recurring task with a new one
     * new recurring task will have an updated recurrence date.
     * Returns a boolean value that determines if there was any recurrence triggered.
     * @return A boolean value where true indicates a recurrence was triggered, and false being otherwise.
     */
    public boolean checkRecurrence() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatterNow = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String currentTime = now.format(dateTimeFormatterNow);
        int index = 0;
        boolean isEdited = false;
        for (Task check : TaskList.currentList()) {
            if (check.hasRecurring()) {
                // task is a recurring task
                RecurrenceScheduleType type;
                String description = check.getDescription();
                type = check.getRecurrenceSchedule();
                // check if recurrence date has passed
                if (dateHasPassedOthers(currentTime, check, isEdited)) {
                    if (check instanceof Assignment) {
                        Assignment recurringAssignment= new Assignment(description, getNewDate(check));
                        recurringAssignment.setRecurrenceSchedule(type);
                        recurringAssignment.setPriority(check.getPriority());
                        recurringAssignment.setAssignee(check.getUser());
                        taskList.replace(index, recurringAssignment);
                        isEdited = true;
                    } else {
                        Meeting recurringMeeting= new Meeting(description, getNewDate(check));
                        recurringMeeting.setRecurrenceSchedule(type);
                        recurringMeeting.setPriority(check.getPriority());
                        recurringMeeting.setAssignee(check.getUser());
                        taskList.replace(index, recurringMeeting);
                        isEdited = true;
                    }
                }
            }
            // move on to next item in the task list
            index += 1;
        }
        return isEdited;
    }

    /**
     * Returns a new Date object with the date stored in the task object class
     * If any error occurs, the Date object will be set to the current date instead.
     * @param check Task object containing the Date information to be extracted
     * @return newDate, containing a the date information of the task object class.
     */
    private Date getNewDate(Task check) {
        Date newDate = new Date();
        try {
            Calendar calendar = Calendar.getInstance();
            String date = new Storage().convertForStorage(check);
            date = date.substring(0, 15);
            Date storedDate = parser.formatDate(date);
            calendar.setTime(storedDate);
            if (check.getRecurrenceSchedule().equals(RecurrenceScheduleType.day)) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            } else if (check.getRecurrenceSchedule().equals(RecurrenceScheduleType.week)) {
                calendar.add(Calendar.WEEK_OF_MONTH, 1);
            } else {
                calendar.add(Calendar.MONTH, 1);
            }
            newDate = calendar.getTime();
        } catch (RoomShareException e) {
            System.out.println();
        }
        return newDate;
    }

    /**
     * checks if the recurrence date has passed for a RecurringDeadline or RecurringEvent object
     * if date has passed, returns true
     * if date has not passed, returns false.
     * @param currentTime current time of the system as a string
     * @param check Task to be time checked
     * @param isEdited boolean variable describing if the task list has been edited in anyway
     * @return
     */
    private boolean dateHasPassedOthers(String currentTime, Task check, boolean isEdited) {
        boolean isPassed = false;
        try {
            Date current = parser.formatDate(currentTime);
            Date newDate = getNewDate(check);
            if (newDate.compareTo(current) < 0) {
                // date has passed
                isPassed = true;
            }
        } catch (RoomShareException e) {
            System.out.println(DATE_ERROR_SET_AS_NOT_DONE);
            check.setDone(false);
            isEdited = true;
        }
        return isPassed;
    }
}
