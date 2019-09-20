package wallet.logic.command;

import wallet.model.Wallet;
import wallet.storage.Storage;

public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    @Override
    public boolean execute(Wallet wallet, Storage storage) {
        return false;
    }
}
