package executor.accessors;

import duke.exception.DukeException;
import storage.StorageManager;
import ui.UiCode;

public class AccessWalletExpenses extends Accessor {

    /**
     * Constructor for AccessExpenses.
     */
    public AccessWalletExpenses() {
        super();
        this.accessType = AccessType.EXPENSES;
    }

    @Override
    public void execute(StorageManager storageManager) {
        this.infoCapsule.setUiCode(UiCode.UPDATE);
        Double expenses;
        try {
            expenses = storageManager.getWalletExpenses();
        } catch (DukeException e) {
            this.infoCapsule.setUiCode(UiCode.ERROR);
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
        this.infoCapsule.setOutputDouble(expenses);
    }
}
