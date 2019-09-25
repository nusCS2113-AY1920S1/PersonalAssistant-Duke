package duchess.logic.commands;

import duchess.storage.Storage;
import duchess.model.task.TaskList;
import duchess.logic.commands.exceptions.DukeException;
import duchess.ui.Ui;

public class ByeCommand extends Command {
    /**
     * Bids users farewell.
     *
     * @param taskList List containing tasks
     * @param ui Userinterface object
     * @param storage Storage object
     * @throws DukeException Exception thrown when storage not found
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        this.isExit = true;
        ui.showBye();
    }
}
