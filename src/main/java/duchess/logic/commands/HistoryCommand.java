package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.DuchessHistory;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

/**
 * Command to display input history.
 */
public class HistoryCommand extends Command {

    /**
     * Displays full log command to users.
     *
     * @param store store object
     * @param ui user interaction object
     * @param storage storage object
     * @throws DuchessException if display is unsuccessful
     */
    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        ui.showUserHistory(DuchessHistory.getFullLog());
    }
}