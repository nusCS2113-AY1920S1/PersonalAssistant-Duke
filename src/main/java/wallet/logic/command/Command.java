package wallet.logic.command;

import wallet.model.Wallet;
import wallet.storage.StorageManager;

public abstract class Command {

    /**
     * Returns a boolean indicating whether the user wants to continue running the application.
     *
     * @param wallet The Wallet object.
     * @param storageManager The StorageManager object.
     * @return A boolean indicating whether the user wants to continue running the application.
     */
    public abstract boolean execute(Wallet wallet, StorageManager storageManager);
}
