package duke.commands;

import duke.storage.Storage;
import duke.ui.Ui;
import duke.ui.map.MapWindow;

/**
 * Temporary class to open map window.
 */
public class MapCommand extends Command {
    /**
     * Executes this command on the given task list and user interface.
     *
     * @param ui The user interface displaying events on the task list.
     * @param storage The duke.storage object containing task list.
     */
    @Override
    public void execute(Ui ui, Storage storage) {
        new MapWindow().show();
    }
}
