package moneycommands;

import controlpanel.MoneyStorage;
import controlpanel.DukeException;
import controlpanel.Ui;
import money.Account;

import java.text.ParseException;


public abstract class MoneyCommand {
    //@@author cctt1014
    public MoneyCommand(){
    }

    public abstract boolean isExit();

    public abstract void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException;

    //@@author Chianhaoplanks
    public abstract void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException;
}
