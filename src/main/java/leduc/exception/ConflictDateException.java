package leduc.exception;

import leduc.task.Task;

import java.util.ArrayList;

/**
 * throw an exception when there is a conflict between two date of event
 */
public class ConflictDateException extends DukeException {
    ArrayList<Task> tasks;

    /**
     * Constructor
     * @param tasks the list of event which are in conflict with the new event
     */
    public ConflictDateException(ArrayList<Task> tasks){
        super();
        this.tasks = tasks;
    }

    /**
     * the error message
     * @return the list of all task that are in conflict with the new event
     */
    @Override
    public String print() {
        String conflictTasks = "";
        for (Task t : tasks){
            conflictTasks += "\n\t\t\t" + t.toString();
        }
        return "\t ConflictDateException:\n\t\t ☹ OOPS!!! There is a date conflict with this event :" +
                conflictTasks;
    }
}
