package Commands;
import DukeExceptions.DukeException;
import Tasks.*;
import Interface.*;

import java.io.FileNotFoundException;

/**
 * Represent the command to mark a check on a task
 */
public class DoneCommand extends Command {

    private final int index;

    /**
     * Creates a DoneCommand object.
     * @param index The index representing the task number in the TaskList object
     */
    public DoneCommand(int index){
        this.index = index;
    }

    /**
     * Executes the marking a check on a task inside the TaskList object with the given index.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the done task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display done task message
     * @throws DukeException On ArrayList out of bound error
     */
    @Override
    public String execute(LookupTable LT,TaskList events, TaskList deadlines, Ui ui, Storage storage) throws DukeException, FileNotFoundException {
        /*if(index >= 0 && index < todos.taskListSize()) {
            todos.markAsDone(index);
            return ui.showDone(todos.getTask(index));
        } else {
            throw new DukeException("\u2639" + " OOPS!!! I'm sorry, but we cannot find the input task number :-(");
        }*/

        return null; //remove this after fixing
    }
}
