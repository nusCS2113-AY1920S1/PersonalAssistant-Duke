package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;

import java.text.ParseException;

/**
 * This command lets the program know that the user wishes to undo the previous command issued.
 */

public class UndoCommand extends  MoneyCommand {

    //@@ Chianhaoplanks
    public UndoCommand() {}

    public boolean isExit() { return  false; }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        return;
    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        return;
    }
}
