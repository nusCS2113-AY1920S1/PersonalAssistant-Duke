package Operations;

import CustomExceptions.DukeException;
import Enums.ExceptionType;
import Enums.TaskType;
import Model_Classes.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public void checkRecurrence(TaskList taskList) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String time = now.format(dateTimeFormatter);

    }
}
