package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;

import java.text.ParseException;

/**
 * This command tracks the last command issued to the program and reverts program back to
 * previous state when called.
 */

public class UndoCommand extends  MoneyCommand {
    protected MoneyCommand lastIssuedCommand;

    //@@ Chianhaoplanks
    public UndoCommand() { lastIssuedCommand = null; }

    public boolean isExit() { return  false; }

    public void setLastIssuedCommand(MoneyCommand c) { lastIssuedCommand = c; }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException,
            ParseException {
        lastIssuedCommand.undo(account, ui, storage);
    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        if (lastIssuedCommand == null) {
            throw new DukeException("No command to undo!\n");
        } else {
            throw new DukeException("Command can't be undone!\n");
        }
    }
}
