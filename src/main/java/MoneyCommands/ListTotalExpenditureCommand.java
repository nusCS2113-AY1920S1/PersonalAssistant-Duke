package MoneyCommands;

import controlpanel.Storage;
import controlpanel.Ui;
import Money.Account;
import Money.Expenditure;

public class ListTotalExpenditureCommand extends MoneyCommand{

    public ListTotalExpenditureCommand(){
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, Storage storage) {
        int counter = 1;
        for (Expenditure i : account.getExpListTotal()) {
            //System.out.println(" " + counter + "." + i.toString() + "\n");
            ui.appendToOutput(" " + counter + "." + i.toString() + "\n");
            counter++;
        }
        //System.out.println("Total income so far: $" + account.getTotalExp());
        ui.appendToOutput("Total expenditure so far: $" + account.getTotalExp() + "\n");

    }
}
