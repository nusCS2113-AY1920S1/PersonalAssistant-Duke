package duke.logic.command;

import java.io.IOException;
import java.util.Optional;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * duke.logic.parser.command.ClearCommand which executes the procedure for
 * clearinngg duke.task.Task objects from the duke.tasklist.TaskList
 */
public class ClearCommand extends Command {
    private Optional<String> filter;

    /**
     * Constructor for ClearCommand that takes in the filter to allow you to clear a specific filtered list
     *
     * @param filter Optional<String> of the filter to be passed in
     */
    public ClearCommand(Optional<String> filter) {
        this.filter = filter;
    }

    /**
     * execute() that clears the task list specified
     * @param tasks
     * @param ui
     * @param storage
     * @throws DukeException
     * @throws IOException
     * @throws IOException
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException, IOException {
        if (tasks.clear(filter)) {
            throw new DukeException("Your" + (filter.isPresent() ? " " + filter.get() : "") +
                    " task list is already empty!");
        } else {
            ui.showLine("Your" + (filter.isPresent() ? " " + filter.get() : "") +
                    " task list has been cleared!");
        }
        storage.save(tasks);
    }

    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {
    }
}
