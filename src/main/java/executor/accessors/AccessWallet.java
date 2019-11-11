package executor.accessors;

import storage.StorageManager;
import ui.UiCode;

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
