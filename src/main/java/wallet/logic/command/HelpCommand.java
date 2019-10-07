package wallet.logic.command;

import wallet.model.Wallet;

/**
 * The HelpCommand Class shows users what the valid commands are.
 */
public class HelpCommand extends Command {
    public static final String COMMAND_WORD = "help";

    /**
     * Shows a list of valid commands to the user and returns false.
     *
     * @param wallet The Wallet object.
     * @return false.
     */
    @Override
    public boolean execute(Wallet wallet) {
        return false;
    }
}
