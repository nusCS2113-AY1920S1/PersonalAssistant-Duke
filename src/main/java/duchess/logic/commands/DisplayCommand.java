package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

/**
 * Command to display message to user.
 */
public class DisplayCommand extends Command {
    private String message;

    public DisplayCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        ui.displayMessage(this.message);
    }
}
