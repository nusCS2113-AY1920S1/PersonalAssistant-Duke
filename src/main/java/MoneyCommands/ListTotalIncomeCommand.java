package MoneyCommands;

import controlpanel.Storage;
import controlpanel.Ui;
import Money.Account;
import Money.Income;

public class ListTotalIncomeCommand extends MoneyCommand{

    public ListTotalIncomeCommand(){
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, Storage storage) {
        int counter = 1;
        for (Income i : account.getIncomeListTotal()) {
            //System.out.println(" " + counter + "." + i.toString() + "\n");
            ui.appendToOutput(" " + counter + "." + i.toString() + "\n");
            counter++;
        }
        //System.out.println("Total income so far: $" + account.getTotalIncome());
        ui.appendToOutput("Total income so far: $" + account.getTotalIncome() + "\n");
    }
}
