package wallet.logic.command;

import wallet.model.Wallet;

public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    @Override
    public boolean execute(Wallet wallet) {
        return false;
    }
}
