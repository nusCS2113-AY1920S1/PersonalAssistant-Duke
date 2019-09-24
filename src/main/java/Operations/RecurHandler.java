package Operations;

import CustomExceptions.DukeException;
import Enums.ExceptionType;
import Enums.TaskType;
import Model_Classes.Deadline;
import Model_Classes.Event;
import Model_Classes.ToDo;

import java.util.ArrayList;
import java.util.Date;

public class RecurHandler {
    private TaskList recurringList;
    private Ui ui = new Ui();
    private Parser parser = new Parser();
    public RecurHandler(TaskList recurringList) {
        this.recurringList = recurringList;
    }

    public void addBasedOnOperation(String command) {
        TaskType taskType = TaskType.valueOf(command);
        switch (taskType) {
            case todo:
                try {
                    ui.showAdd();
                    ToDo temp = new ToDo(parser.getDescription());
                    recurringList.add(temp);
                } catch (DukeException e) {
                    ui.showEmptyDescriptionError();
                }
                break;

            case deadline:
                try {
                    ui.showAdd();
                    String[] deadlineArray = parser.getDescriptionWithDate();
                    Date by = parser.formatDate(deadlineArray[1]);
                    Deadline temp = new Deadline(deadlineArray[0], by);
                    recurringList.add(temp);
                } catch (DukeException e) {
                    ui.showDateError();
                }
                break;

            case event:
                try {
                    ui.showAdd();
                    String[] eventArray = parser.getDescriptionWithDate();
                    Date at = parser.formatDate(eventArray[1]);
                    if(CheckAnomaly.checkTime(at, TaskList.currentList())){
                        Event temp = new Event(eventArray[0], at);
                        recurringList.add(temp);
                    } else {
                        throw new DukeException(ExceptionType.timeClash);
                    }
                } catch (DukeException e) {
                    ui.showDateError();
                }
                break;
        }
    }
}
