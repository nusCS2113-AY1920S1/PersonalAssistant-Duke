package MoneyCommands;

import controlpanel.*;
import Money.Account;

public class ExitMoneyCommand extends MoneyCommand {

    public ExitMoneyCommand(){

    }

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public void execute(Account account, Ui ui, Storage storage) {
        //System.out.println("     Bye. Hope to see you again soon!\n");

        ui.appendToOutput("     Bye. Hope to see you again soon!\n");
    }


}
