package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.task.DuchessLog;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

/**
 * Command to display input history.
 */
public class HistoryCommand extends Command {

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        ui.showUserHistory(DuchessLog.getFullLog());
    }
}