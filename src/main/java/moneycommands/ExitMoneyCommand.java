package moneycommands;

import controlpanel.*;
import money.Account;

public class ExitMoneyCommand extends MoneyCommand {

    //@@author cctt1014
    public ExitMoneyCommand(){
    }

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) {
        ui.appendToOutput("     Bye. Hope to see you again soon!\n");
    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}
