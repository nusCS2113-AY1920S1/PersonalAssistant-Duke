package wallet.logic.command;

import wallet.model.Wallet;

/**
 * The ExitCommand Class deals with the 'bye' command.
 */
public class ExitCommand extends Command {
    public static final String COMMAND_WORD = "bye";

    /**
     * Returns true to indicate to Main to terminate the application.
     *
     * @param wallet The Wallet object.
     * @return True. (Always)
     */
    @Override
    public boolean execute(Wallet wallet) {
        return true;
    }
}
