package duke.command;

import duke.commons.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;


/**
 * Represents a command that terminates the program
 */
public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) throws DukeException {
        System.exit(0);
    }
}
