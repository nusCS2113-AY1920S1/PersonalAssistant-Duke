package moneycommands;

import controlpanel.*;
import money.Account;

public class ExitMoneyCommand extends MoneyCommand {

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


}
