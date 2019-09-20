package wallet.logic.command;

import wallet.model.Wallet;

public abstract class Command {

    public abstract boolean execute(Wallet wallet);
}
