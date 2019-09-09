package duke.command;

import duke.commons.DukeException;
import duke.commons.Message;
import duke.commons.Ui;
import duke.storage.Storage;
import duke.task.TaskList;

/**
 * Represents a command that terminates the program
 */
public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        Ui.showToUser(Message.getExit());
        System.exit(0);
    }
}
