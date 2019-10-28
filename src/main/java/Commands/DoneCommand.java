package Commands;
import Tasks.*;
import DukeExceptions.DukeException;
import Interface.*;

import java.io.FileNotFoundException;

/**
 * Represents the command to delete a Task object from a TaskList object.
 */
public class DoneCommand extends Command {

    private Task T;
    private final String list;

    /**
     * Creates a DeleteCommand object.
     * @param T The task to be deleted
     * @param list The name of the TaskList that requires changing
     */
    public DoneCommand(String list, Task T){
        this.T =T;
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
    public String execute(LookupTable LT,TaskList events, TaskList deadlines, Ui ui, Storage storage) throws DukeException {
        try{
            if (list.equals("event")) {
                events.updateTask(T);
                storage.updateEventList(events);
            } else if (list.equals("deadline")) {
                deadlines.updateTask(T);
                storage.updateDeadlineList(deadlines);
            }
            T.setDone(true);
            return ui.showDone(T);
        } catch(ArrayIndexOutOfBoundsException e) {
            throw new DukeException("\u2639" + " OOPS!!! I'm sorry, but we cannot find the input task  :-(\n");
        }
    }
}
