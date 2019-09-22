package wallet.logic.command;

import wallet.model.Wallet;
import wallet.storage.Storage;

public abstract class Command {

    public abstract boolean execute(Wallet wallet, Storage storage);
}
