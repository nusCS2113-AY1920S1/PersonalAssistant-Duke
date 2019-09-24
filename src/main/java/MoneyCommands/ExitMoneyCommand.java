package MoneyCommands;

import Money.Account;
import Tasks.*;
import ControlPanel.*;

public class ExitMoneyCommand extends MoneyCommand {

    public ExitMoneyCommand(){

    }

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public void execute(Account account, Ui ui, Storage storage) {
        System.out.println("     Bye. Hope to see you again soon!\n");
    }


}
