package Commands;

import DukeExceptions.DukeException;
import Commons.LookupTable;
import Commons.Storage;
import Commons.Ui;
import Tasks.Assignment;
import Tasks.TaskList;


/**
 * Represents the command to done a Task object from a TaskList object.
 */
public class DoneCommand extends Command {

    private Assignment task;
    private final String list;

    /**
     * Creates a DoneCommand object.
     * @param task The task to be mask as done
     * @param list The name of the TaskList that requires changing
     */
    public DoneCommand(String list, Assignment task){
        this.task = task;
        this.list = list;
    }

    /**
     * Executes the mark as done of a task inside the TaskList object with the given index.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the mark as done task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display delete task message
     * @throws DukeException On ArrayList out of bound error
     */
    @Override
    public String execute(LookupTable LT,TaskList events, TaskList deadlines, Ui ui, Storage storage) throws DukeException {
        try{
            if (list.equals("event")) {
                events.updateTask(task);
                storage.updateEventList(events);
            } else if (list.equals("deadline")) {
                deadlines.updateTask(task);
                storage.updateDeadlineList(deadlines);
            }
            task.setDone(true);
            return ui.showDone(task);
        } catch(ArrayIndexOutOfBoundsException e) {
            throw new DukeException("\u2639" + " OOPS!!! I'm sorry, but we cannot find the input task  :-(\n");
        }
    }
}
