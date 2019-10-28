package Commands;

import DukeExceptions.DukeException;
import Interface.LookupTable;
import Interface.Storage;
import Interface.Ui;
import Tasks.Assignment;
import Tasks.TaskList;

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
    public String execute(LookupTable LT, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws DukeException {
       try{
            if (list.equals("event")) {
                events.removeTask(task);
                storage.updateEventList(events);
                listToChange = events;
            } else if (list.equals("deadline")) {
                deadlines.removeTask(task);
                storage.updateDeadlineList(deadlines);
                listToChange = deadlines;
            }
            return ui.showDelete(task, listToChange.taskListSize());
        } catch(ArrayIndexOutOfBoundsException e) {
            throw new DukeException("\u2639" + " OOPS!!! I'm sorry, but we cannot find the input task  :-(\n");
        }
    }
}
