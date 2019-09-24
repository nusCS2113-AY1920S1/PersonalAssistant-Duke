package Operations;

import CustomExceptions.DukeException;
import Enums.ExceptionType;
import Enums.TaskType;
import Model_Classes.Deadline;
import Model_Classes.Event;
import Model_Classes.ToDo;

import java.util.Date;

public class RecurHandler {
    private RecurList recurringList;
    private Ui ui = new Ui();
    private Parser parser = new Parser();
    public RecurHandler(RecurList recurringList) {
        this.recurringList = recurringList;
    }

    public void addBasedOnOperation() {
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
                    ToDo temp = new ToDo(parser.getDescription());
                    recurringList.add(temp);
                    ui.showAddRecur();
                } catch (DukeException e) {
                    ui.showEmptyDescriptionError();
                }
                break;

            case deadline:
                try {
                    String[] deadlineArray = parser.getDescriptionWithDate();
                    Date by = parser.formatDate(deadlineArray[1]);
                    Deadline temp = new Deadline(deadlineArray[0], by);
                    recurringList.add(temp);
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
                        Event temp = new Event(eventArray[0], at);
                        recurringList.add(temp);
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

    public void deleteRecurring(int index) {
        try {
            recurringList.delete(index);
            ui.showDeletedRecur(index);
        } catch (DukeException e) {
            ui.showIndexError();
        }
    }

    public void listRecurring() {
        ui.showListRecur();
        recurringList.list();
    }
    public void findRecurring(String key) {
        ui.showListRecur();
        recurringList.find(key);
    }
}
