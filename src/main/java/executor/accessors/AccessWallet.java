package executor.accessors;

import duke.exception.DukeException;
import storage.StorageManager;
import ui.UiCode;
import utils.AccessType;

public class AccessWallet extends Accessor {

    /**
     * Constructor for AccessWallet Class.
     */
    public AccessWallet() {
        super();
        this.accessType = AccessType.WALLET;
    }

    @Override
    public void execute(StorageManager storageManager) {
        this.infoCapsule.setUiCode(UiCode.UPDATE);
        this.infoCapsule.setWallet(storageManager.getWallet());
    }
}
