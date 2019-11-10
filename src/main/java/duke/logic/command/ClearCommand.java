package duke.logic.command;

import java.io.IOException;
import java.util.Optional;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * duke.logic.parser.command.DeleteCommand which executes the procedure for
 * deleting duke.task.Task objects from the duke.tasklist.TaskList
 */
public class ClearCommand extends Command {
    private Optional<String> filter;

    public ClearCommand(Optional<String> filter) {
        this.filter = filter;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException, IOException {
        if (filter.isPresent()) {
            String printFilter = filter.toString().substring(9);
            printFilter = printFilter.replaceAll("]", "");
            ui.showLine("You've cleared your " + printFilter + " task list!");
        } else {
            ui.showLine("You've cleared your task list!");
        }
        tasks.clear(filter);
        storage.save(tasks);
    }

    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {
    }
}
