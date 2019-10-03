package MoneyCommands;

import Money.Account;
import Money.BankTracker;
import controlpanel.Storage;
import controlpanel.Ui;

import java.util.ArrayList;

public class ListBankTrackerCommand extends MoneyCommand{

    public ListBankTrackerCommand() {
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, Storage storage) {
        ArrayList<BankTracker> accountList = account.getBankTrackerList();
        ui.appendToOutput("Here are the bank accounts and their info:\n");
        for (int i = 0; i < accountList.size(); i++) {
            ui.appendToOutput((i+1) + ". ----------------------------------------\n");
            ui.appendToOutput(accountList.get(i).getBankAccountInfo() + "\n");
            ui.appendToOutput("-------------------------------------------\n");
        }
    }
}
