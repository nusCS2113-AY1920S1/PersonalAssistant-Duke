package wallet.logic.command;

import wallet.model.Wallet;

public class HelpCommand extends Command {
    public static final String COMMAND_WORD = "help";

    @Override
    public boolean execute(Wallet wallet) {
        return false;
    }
}
