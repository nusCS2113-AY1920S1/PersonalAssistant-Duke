//@@author Xdecosee
package wallet.logic.command;

import wallet.model.Wallet;

public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    @Override
    public boolean execute(Wallet wallet) {
        return false;
    }
}
