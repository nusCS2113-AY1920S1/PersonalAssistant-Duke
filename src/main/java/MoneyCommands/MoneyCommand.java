package MoneyCommands;

import ControlPanel.*;
import Money.*;

import java.text.ParseException;


public abstract class MoneyCommand {
    public MoneyCommand(){

    }

    public abstract boolean isExit();
    public abstract void execute(Account account, Ui ui, Storage storage) throws DukeException, ParseException;
}
