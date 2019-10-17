package Operations;

import CustomExceptions.RoomShareException;
import Enums.ExceptionType;
import Enums.RecurrenceScheduleType;
import Enums.TaskType;
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
    private static final String ENTER_TASK_TYPE = "please enter a todo, deadline or event task type!";
    private static final String RECURRENCE_SYMBOL = "(R:";
    private static final String EMPTY_SEARCH = "\tYour search returned no results.... Try searching with another keyword!";
    private static final String TODO_MARKER = "[T]";
    private static final String DAY = "day";
    private static final String WEEK = "week";
    private static final String MONTH = "month";
    private static final String DEADLINE_MARKER = "[D]";
    public static final String DATE_ERROR_GENERATE_NEW_RECURRING_TASK = "Error in parsing dates, generating new recurring task";
    public static final String DATE_ERROR_SET_AS_NOT_DONE = "Error in parsing date, will be setting the task to not done instead";
    private TaskList taskList;
    private Ui ui = new Ui();
    private Parser parser = new Parser();

    /**
     * Constructor for RecurHandler class
     * @param recurringList The TaskList to be operated on using RecurHandler
     */
    public RecurHandler(TaskList recurringList) {
        this.taskList = recurringList;
    }

    /**
     * Constructs and adds a Recurring type task into the task list
     * Creates new types of tasks called RecurringDeadline, RecurringToDo, RecurringEvent
     * and adds them into the task list
     * Will prompt user on the recurrence schedule before any creation of tasks.
     */
    public void addBasedOnOperation() {
        ui.promptForRecurrence();
        String recurrence = parser.getRecurrence();
        ui.promptForTask();
        //get the command to create a recurring task
        String command = parser.getCommand();
        TaskType taskType;
        try {
            taskType = TaskType.valueOf(command);
        } catch (IllegalArgumentException e) {
            taskType = TaskType.others;
        }
        switch (taskType) {
        case todo:
             try {
                 RecurringToDo temp = new RecurringToDo(parser.getDescription(), recurrence);
                 taskList.add(temp);
                 ui.showAddRecur();
             } catch (RoomShareException e) {
                 ui.showEmptyDescriptionError();
             }
             break;

        case deadline:
            try {
                String[] deadlineArray = parser.getDescriptionWithDate();
                Date by = parser.formatDate(deadlineArray[1]);
                RecurringDeadline temp = new RecurringDeadline(deadlineArray[0], by, recurrence);
                taskList.add(temp);
                ui.showAddRecur();
            } catch (RoomShareException e) {
                ui.showDateError();
            }
            break;

        case event:
            try {
                String[] eventArray = parser.getDescriptionWithDate();
                Date at = parser.formatDate(eventArray[1]);
                if(CheckAnomaly.checkTime(at)) {
                    RecurringEvent temp = new RecurringEvent(eventArray[0], at, recurrence);
                    taskList.add(temp);
                    ui.showAddRecur();
                } else {
                    throw new RoomShareException(ExceptionType.timeClash);
                }
            } catch (RoomShareException e) {
                ui.showDateError();
            }
            break;

        default:
            System.out.println(ENTER_TASK_TYPE);
            break;
        }
    }

    /**
     * Lists out all recurring tasks in the task list
     */
    public void listRecurring() {
        ui.showListRecur();
        int listCount = 1;
        for (Task output : TaskList.currentList()) {
            if (output.toString().contains(RECURRENCE_SYMBOL)) {
                System.out.println("\t" + listCount + ". " + output.toString());
                listCount += 1;
            }
        }
    }

    /**
     * Finds all recurring tasks with any mention of the keyword
     * Lists all these tasks to the user.
     * @param key keyword that is being queried in all the recurring tasks
     */
    public void findRecurring(String key) {
        ui.showListRecur();
        int queryCount = 1;
        for (Task query : TaskList.currentList()) {
            if (query.toString().toLowerCase().contains(key.trim()) && query.toString().contains(RECURRENCE_SYMBOL)) {
                // task contains the keyword and is a recurring task.
                System.out.println("\t" + queryCount + ". " + query.toString());
            }
            queryCount += 1;
        }
        if (queryCount == 1) {
            System.out.println(EMPTY_SEARCH);
        }
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
            if (check instanceof RecurringToDo || check instanceof RecurringEvent || check instanceof RecurringDeadline) {
                String temp = check.toString();
                String[] array = temp.split("\\(");
                String recurrenceSchedule;
                RecurrenceScheduleType type;
                String description = check.getDescription();
                if (check instanceof RecurringToDo) {
                    // extract the recurrence schedule.
                    recurrenceSchedule = array[2].substring(3, array[2].length() - 1);
                    type = RecurrenceScheduleType.valueOf(recurrenceSchedule);
                    switch (type) {
                    case day:
                        // check if recurrence date has passed
                        if (dateHasPassedToDo(currentTime, check.getCreated(), DAY, description, index, isEdited)) {
                            RecurringToDo recurringToDo = new RecurringToDo(description, DAY);
                            taskList.replace(index, recurringToDo);
                            isEdited = true;
                        }
                        break;

                    case week:
                        // check if recurrence date has passed
                        if (dateHasPassedToDo(currentTime, check.getCreated(), WEEK, description, index, isEdited)) {
                            RecurringToDo recurringToDo = new RecurringToDo(description, WEEK);
                            taskList.replace(index, recurringToDo);
                            isEdited = true;
                        }
                        break;

                    case month:
                        // check if recurrence date has passed
                        if (dateHasPassedToDo(currentTime, check.getCreated(), MONTH, description, index, isEdited)) {
                            RecurringToDo recurringToDo = new RecurringToDo(description, MONTH);
                            taskList.replace(index, recurringToDo);
                            isEdited = true;
                        }
                        break;
                    }
                } else {
                    // handling for deadline and event recurrences
                    recurrenceSchedule = array[3].substring(3, array[3].length() - 1);
                    type = RecurrenceScheduleType.valueOf(recurrenceSchedule);
                    switch (type) {
                    case week:
                        // check if recurrence date has passed
                        if (dateHasPassedOthers(currentTime, check, isEdited )) {
                            if (check instanceof RecurringDeadline) {
                                RecurringDeadline recurringDeadline = new RecurringDeadline(description, getNewDate(check), WEEK);
                                taskList.replace(index, recurringDeadline);
                                isEdited = true;
                            } else {
                                RecurringEvent recurringEvent = new RecurringEvent(description, getNewDate(check), WEEK);
                                taskList.replace(index, recurringEvent);
                                isEdited = true;
                            }
                        }
                        break;

                    case day:
                        // check if recurrence date has passed
                        if (dateHasPassedOthers(currentTime, check, isEdited )) {
                            if (check.toString().contains(DEADLINE_MARKER)) {
                                RecurringDeadline recurringDeadline = new RecurringDeadline(description, getNewDate(check), DAY);
                                taskList.replace(index, recurringDeadline);
                                isEdited = true;
                            } else {
                                RecurringEvent recurringEvent = new RecurringEvent(description, getNewDate(check), DAY);
                                taskList.replace(index, recurringEvent);
                                isEdited = true;
                            }
                        }
                        break;

                    case month:
                        // check if recurrence date has passed
                        if (dateHasPassedOthers(currentTime, check, isEdited )) {
                            if (check.toString().contains(DEADLINE_MARKER)) {
                                RecurringDeadline recurringDeadline = new RecurringDeadline(description, getNewDate(check), MONTH);
                                taskList.replace(index, recurringDeadline);
                                isEdited = true;
                            } else {
                                RecurringEvent recurringEvent = new RecurringEvent(description, getNewDate(check), MONTH);
                                taskList.replace(index, recurringEvent);
                                isEdited = true;
                            }
                        }
                        break;
                    }
                }
            }
            // move on to next item in the task list
            index += 1;
        }
        return isEdited;
    }

    /**
     * checks if the recurrence date has passed for a RecurringToDo object
     * if date has passed, returns true
     * if date has not passed, returns false.
     * @param currentTime current time of the computer system as a string
     * @param createdTime time the RecurringToDo object was created
     * @param recurrence recurrence schedule of the object
     * @param description description of the task
     * @param index index the task is stored in the task list
     * @param isEdited boolean variable describing if the task list has been edited in anyway
     * @return
     */
    private boolean dateHasPassedToDo(String currentTime, String createdTime, String recurrence,
                                      String description, int index, boolean isEdited) {
        boolean isPassed = false;
        try{
            Date current = parser.formatDate(currentTime);
            Date created = parser.formatDate(createdTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(created);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date newDate = calendar.getTime();
            if (newDate.compareTo(current) < 0) {
                // date has passed
                isPassed = true;
            }
        } catch (RoomShareException e) {
            System.out.println(DATE_ERROR_GENERATE_NEW_RECURRING_TASK);
            RecurringToDo recurringToDo = new RecurringToDo(description, recurrence);
            taskList.replace(index, recurringToDo);
            isEdited = true;
        }
        return isPassed;
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
            date = date.substring(0, 16);
            Date storedDate = parser.formatDate(date);
            calendar.setTime(storedDate);
            calendar.add(Calendar.MONTH, 1);
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
