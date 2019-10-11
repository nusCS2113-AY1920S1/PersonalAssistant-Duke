package Commands;
import Tasks.*;
import Interface.*;

import java.io.FileNotFoundException;

/**
 * Represents the command to delete a Task object from a TaskList object.
 */
public class DeleteCommand extends Command {

    private final int index;
    private final String list;
    private TaskList listToChange;

    /**
     * Creates a DeleteCommand object.
     * @param index The index representing the task number in the TaskList object
     * @param list The name of the TaskList that requires changing
     */
    public DeleteCommand(int index, String list){
        this.index = index;
        this.list = list;
    }

    /**
     * Executes the deletion of a task inside the TaskList object with the given index.
     * @param todos The TaskList object for todos
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the delete task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display delete task message
     * @throws DukeException On ArrayList out of bound error
     */
    @Override
    public String execute(TaskList todos, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws DukeException, FileNotFoundException {
        if (list.equals("todo")) {
            listToChange = todos;
        } else if (list.equals("event")) {
            listToChange = events;
        } else if (list.equals("deadline")) {
            listToChange = deadlines;
        }
        if (index >= 0 && index < listToChange.taskListSize()) {
            Task task = listToChange.getTask(index);
            listToChange.removeTask(this.index);
            if (listToChange.equals("todo")) {
                storage.updateTodoList(listToChange);
            } else if (listToChange.equals("event")) {
                storage.updateEventList(listToChange);
            } else if (listToChange.equals("deadline")) {
                storage.updateDeadlineList(listToChange);
            }
            return ui.showDelete(task, listToChange.taskListSize());
        } else {
            throw new DukeException("\u2639" + " OOPS!!! I'm sorry, but we cannot find the input task number :-(\n");
        }
    }
}
