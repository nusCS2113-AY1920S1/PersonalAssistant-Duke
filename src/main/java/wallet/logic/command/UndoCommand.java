//@@author A0171206R

package wallet.logic.command;

import wallet.model.Wallet;

public class UndoCommand extends Command {

    

    @Override
    public boolean execute(Wallet wallet) {
        return false;
    }
}
