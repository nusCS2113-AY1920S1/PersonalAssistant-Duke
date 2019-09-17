package command;

import exception.DukeException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.io.IOException;
import java.text.ParseException;

/**
 * command.Command to exit Duke programme.
 */
public class ByeCommand extends Command {

    /**
     * If "bye" is entered.
     * @param tasks task list
     * @param ui user interface
     * @param storage handles read write of text file
     * @throws IOException if IOException found
     * @throws DukeException if Duke specific exception found
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, DukeException, ParseException {
        super.execute(tasks, ui, storage);
        ui.showBye();
        this.isExit = true;
    }
}
