package Commands;

import DukeExceptions.DukeException;
import Commons.Storage;
import Commons.UserInteraction;
import Tasks.Assignment;
import Tasks.TaskList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the command to delete a Task object from a TaskList object.
 */
public class DeleteCommand extends Command {

    private Assignment task;
    private final String list;
    private TaskList listToChange;

    /**
     * Creates a DeleteCommand object.
     * @param task The task to be deleted
     * @param list The name of the TaskList that requires changing
     */
    public DeleteCommand(String list, Assignment task){
        this.task = task;
        this.list = list;
    }


    /**
     * Executes the deletion of a task inside the TaskList object with the given index.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the delete task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display delete task message
     * @throws DukeException On ArrayList out of bound error
     */
    @Override
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage) throws DukeException {
        HashMap<String, HashMap<String, ArrayList<Assignment>>> eventMap = events.getMap();
        HashMap<String, HashMap<String, ArrayList<Assignment>>> deadlineMap = deadlines.getMap();
        if (list.equals("event")) {
            super.insideMapChecker(eventMap, task);
            events.removeTask(task);
            storage.updateEventList(events);
            listToChange = events;
        } else if (list.equals("deadline")) {
            super.insideMapChecker(deadlineMap, task);
            deadlines.removeTask(task);
            storage.updateDeadlineList(deadlines);
            listToChange = deadlines;
        }
        return ui.showDelete(task, listToChange.taskListSize());
    }
}
