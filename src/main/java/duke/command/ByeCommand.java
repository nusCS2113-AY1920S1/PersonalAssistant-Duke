package duke.command;

import duke.task.TaskList;
import duke.dukeexception.DukeException;

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
