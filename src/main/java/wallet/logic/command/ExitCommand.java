package wallet.logic.command;

import wallet.model.Wallet;

public class ExitCommand extends Command {
    public static final String COMMAND_WORD = "bye";

    @Override
    public boolean execute(Wallet wallet) {
        return true;
    }
}
