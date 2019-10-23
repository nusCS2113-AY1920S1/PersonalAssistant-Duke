package duke.command.ingredientCommand;

import duke.command.Cmd;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents a specific {@link Cmd} used to mark a {@link Task} as done.
 */
public class DoneCommand<T> extends Cmd<T> {
    private int taskNb;

    public DoneCommand(int taskNb) {
        this.taskNb = taskNb;
    }

    @Override
    public void execute(GenericList<T> taskList, Ui ui, Storage storage) throws DukeException {
        if (taskNb < taskList.size() && taskNb >= 0) {
            ((TaskList)taskList).markTaskDone(taskNb);
            ui.showMarkDone(taskList.getEntry(taskNb).toString());
            storage.changeContent(taskNb);
        } else {
            throw new DukeException("Enter a valid task number after done, between 1 and " + taskList.size());
        }
    }
}
