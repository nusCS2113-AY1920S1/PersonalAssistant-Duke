package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

public class ByeCommand extends Command {
    /**
     * Bids users farewell.
     *
     * @param store the store
     * @param ui Userinterface object
     * @param storage Storage object
     * @throws DuchessException Exception thrown when storage not found
     */
    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        this.isExit = true;
        ui.showBye();
    }
}
