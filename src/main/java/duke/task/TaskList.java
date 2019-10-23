package duke.task;

import duke.Duke;
import duke.list.GenericList;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of {@link Task}s added by {@link Duke}.
 */
public class TaskList extends GenericList<Task> {


    /**
     * The constructor method(1) for TaskList.
     */
    public TaskList(List<Task> taskList) {
       super(taskList);
    }

    /**
     * The constructor method(2) for TaskList.
     */
    public TaskList() {
        super();
    }


    /**
     * Marks a task as completed if the user finished it.
     * @param taskNb the number of the {@link Task} in the {@link TaskList} that was completed
     */
    public void markTaskDone(int taskNb) {
        genList.get(taskNb).markAsDone();
    }

    public void changeTaskDate(int taskNb, String date) {
        genList.get(taskNb).setNewDate(date);
    }
}
