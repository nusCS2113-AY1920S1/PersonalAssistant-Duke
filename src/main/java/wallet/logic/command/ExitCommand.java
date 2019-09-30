package wallet.logic.command;

import wallet.model.Wallet;
import wallet.storage.StorageManager;

/**
 * The ExitCommand Class deals with the 'bye' command.
 */
public class ExitCommand extends Command {
    public static final String COMMAND_WORD = "bye";

    /**
     * Returns true to indicate to Main to terminate the application.
     *
     * @param wallet The Wallet object.
     * @param storageManager The StorageManager object.
     * @return True. (Always)
     */
    @Override
    public boolean execute(Wallet wallet, StorageManager storageManager) {
        return true;
    }
}
