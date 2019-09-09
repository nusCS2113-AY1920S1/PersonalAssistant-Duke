package command;

import commons.DukeException;
import commons.Message;
import commons.Ui;
import storage.Storage;
import task.TaskList;

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
