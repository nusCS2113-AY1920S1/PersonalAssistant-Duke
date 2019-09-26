package Operations;

import CustomExceptions.DukeException;
import Enums.ExceptionType;
import Enums.RecurrenceScheduleType;
import Enums.TaskType;
import Model_Classes.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class RecurHandler {
    private TaskList taskList;
    private Ui ui = new Ui();
    private Parser parser = new Parser();
    public RecurHandler(TaskList recurringList) {
        this.taskList = recurringList;
    }

    public void addBasedOnOperation() {
        ui.promptForRecurrence();
        String recurrence = parser.getRecurrence();
        ui.promptForTask();
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
                } catch (DukeException e) {
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
                } catch (DukeException e) {
                    ui.showDateError();
                }
                break;

            case event:
                try {
                    String[] eventArray = parser.getDescriptionWithDate();
                    Date at = parser.formatDate(eventArray[1]);
                    if(CheckAnomaly.checkTime(at, TaskList.currentList())){
                        RecurringEvent temp = new RecurringEvent(eventArray[0], at, recurrence);
                        taskList.add(temp);
                        ui.showAddRecur();
                    } else {
                        throw new DukeException(ExceptionType.timeClash);
                    }
                } catch (DukeException e) {
                    ui.showDateError();
                }
                break;
            default:
                System.out.println("please enter a todo, deadline or event task type!");
                break;
        }
    }

    public void listRecurring() {
        ui.showListRecur();
        int listCount = 1;
        for (Task output : TaskList.currentList()) {
            if (output.toString().contains("(R:")) {
                System.out.println("\t" + listCount + ". " + output.toString());
                listCount += 1;
            }
        }
    }

    public void findRecurring(String key) {
        ui.showListRecur();
        int queryCount = 1;
        for (Task query : TaskList.currentList()) {
            if (query.toString().toLowerCase().contains(key) && query.toString().contains("(R:")) {
                System.out.println("\t" + queryCount + ". " + query.toString());
            }
            queryCount += 1;
        }
        if (queryCount == 1) {
            System.out.println("\tYour search returned no results.... Try searching with another keyword!");
        }
    }

    public boolean checkRecurrence(TaskList taskList) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatterNow = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String currentTime = now.format(dateTimeFormatterNow);
        int index = 0;
        boolean isEdited = false;
        for (Task check : TaskList.currentList()) {
            if (check.toString().contains("(R:")) {
                String temp = check.toString();
                String[] array = temp.split("\\s+");
                String recurrenceSchedule;
                RecurrenceScheduleType type;
                Calendar calendar = Calendar.getInstance();
                String description = check.getDescription();
                if (check.toString().contains("[T]")) {
                    recurrenceSchedule = array[3].substring(0, array[3].length() - 1);
                    type = RecurrenceScheduleType.valueOf(recurrenceSchedule);
                    switch (type) {
                        case day:
                            try {
                                Date current = parser.formatDate(currentTime);
                                Date created = parser.formatDate(check.getCreated());
                                calendar.setTime(created);
                                calendar.add(Calendar.DAY_OF_MONTH, 1);
                                Date newDate = calendar.getTime();
                                if (newDate.compareTo(current) < 0) {
                                    RecurringToDo recurringToDo = new RecurringToDo(description, "day");
                                    taskList.replace(index, recurringToDo);
                                    isEdited = true;
                                }
                            } catch (DukeException e) {
                                e.printStackTrace();
                            }
                            break;
                        case week:
                            try {
                                Date current = parser.formatDate(currentTime);
                                Date created = parser.formatDate(check.getCreated());
                                calendar.setTime(created);
                                calendar.add(Calendar.DAY_OF_WEEK, 7);
                                Date newDate = calendar.getTime();
                                if (newDate.compareTo(current) < 0) {
                                    RecurringToDo recurringToDo = new RecurringToDo(description, "week");
                                    taskList.replace(index, recurringToDo);
                                    isEdited = true;
                                }
                            } catch (DukeException e) {
                                e.printStackTrace();
                            }
                            break;
                        case month:
                            try {
                                Date current = parser.formatDate(currentTime);
                                Date created = parser.formatDate(check.getCreated());
                                calendar.setTime(created);
                                calendar.add(Calendar.MONTH, 1);
                                Date newDate = calendar.getTime();
                                if (newDate.compareTo(current) < 0) {
                                    RecurringToDo recurringToDo = new RecurringToDo(description, "month");
                                    taskList.replace(index, recurringToDo);
                                    isEdited = true;
                                }
                            } catch (DukeException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                } else {
                    recurrenceSchedule = array[10].substring(0, array[10].length() - 1);
                    type = RecurrenceScheduleType.valueOf(recurrenceSchedule);
                    switch (type) {
                        case week:
                            try {
                                Date current = parser.formatDate(currentTime);
                                String date = new Storage().convertForStorage(check);
                                date = date.substring(0, 16);
                                Date storedDate = parser.formatDate(date);
                                calendar.setTime(storedDate);
                                calendar.add(Calendar.DAY_OF_MONTH, 7);
                                Date newDate = calendar.getTime();
                                if (newDate.compareTo(current) < 0) {
                                    if (check.toString().contains("[D]")) {
                                        RecurringDeadline recurringDeadline = new RecurringDeadline(description, newDate, "week");
                                        taskList.replace(index, recurringDeadline);
                                        isEdited = true;
                                    } else {
                                        RecurringEvent recurringEvent = new RecurringEvent(description, newDate, "week");
                                        taskList.replace(index, recurringEvent);
                                        isEdited = true;
                                    }
                                }
                            } catch (DukeException e) {
                                e.printStackTrace();
                            }
                            break;
                        case day:
                            try {
                                Date current = parser.formatDate(currentTime);
                                String date = new Storage().convertForStorage(check);
                                date = date.substring(0, 16);
                                Date storedDate = parser.formatDate(date);
                                calendar.setTime(storedDate);
                                calendar.add(Calendar.DAY_OF_MONTH, 1);
                                Date newDate = calendar.getTime();
                                if (newDate.compareTo(current) < 0) {
                                    if (check.toString().contains("[D]")) {
                                        RecurringDeadline recurringDeadline = new RecurringDeadline(description, newDate, "day");
                                        taskList.replace(index, recurringDeadline);
                                        isEdited = true;
                                    } else {
                                        RecurringEvent recurringEvent = new RecurringEvent(description, newDate, "day");
                                        taskList.replace(index, recurringEvent);
                                        isEdited = true;
                                    }
                                }
                            } catch (DukeException e) {
                                e.printStackTrace();
                            }
                            break;

                        case month:
                            try {
                                Date current = parser.formatDate(currentTime);
                                String date = new Storage().convertForStorage(check);
                                date = date.substring(0, 16);
                                Date storedDate = parser.formatDate(date);
                                calendar.setTime(storedDate);
                                calendar.add(Calendar.MONTH, 1);
                                Date newDate = calendar.getTime();
                                if (newDate.compareTo(current) < 0) {
                                    if (check.toString().contains("[D]")) {
                                        RecurringDeadline recurringDeadline = new RecurringDeadline(description, newDate, "month");
                                        taskList.replace(index, recurringDeadline);
                                        isEdited = true;
                                    } else {
                                        RecurringEvent recurringEvent = new RecurringEvent(description, newDate, "month");
                                        taskList.replace(index, recurringEvent);
                                        isEdited = true;
                                    }
                                }
                            } catch (DukeException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            }
            index += 1;
        }
        return isEdited;
    }
}
