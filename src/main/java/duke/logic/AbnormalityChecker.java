package duke.logic;

import duke.exception.DukeException;
import duke.task.Event;
import duke.task.Task;
import duke.tasklist.TaskList;

/**
 * Class that checks if there is event clash in timing
 */
public class AbnormalityChecker {
    private TaskList taskList;

    /**
     * Constructor for AbnormalityChecker
     *
     * @param taskList TaskList containing all of user's tasks
     */
    public AbnormalityChecker(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Method that checks if there is another event with the same timing
     *
     * @param event Event task to be checked
     * @return a boolean whether the event classes with current Events
     */
    public boolean checkEventClash(Event event) throws DukeException {
        Task task;
        for (int i = 0; i < taskList.size(); i++) {
            task = taskList.get(i);
            if (task instanceof Event) {
                if (task.getDateTime().equals(event.getDateTime())) {
                    return true;
                }
            }
        }
        return false;
    }
}
