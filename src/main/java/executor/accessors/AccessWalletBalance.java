package executor.accessors;

import duke.exception.DukeException;
import storage.StorageManager;
import ui.UiCode;

public class AccessWalletBalance extends Accessor {

    /**
     * Constructor for AccessWalletBalance Class.
     */
    public AccessWalletBalance() {
        super();
    }

    @Override
    public void execute(StorageManager storageManager) {
        try {
            Double walletBalance = storageManager.getWalletBalance();
            this.infoCapsule.setUiCode(UiCode.UPDATE);
            this.infoCapsule.setOutputDouble(walletBalance);
        } catch (DukeException e) {
            this.infoCapsule.setUiCode(UiCode.ERROR);
            this.infoCapsule.setOutputStr(e.getMessage());
        }
    }
}
