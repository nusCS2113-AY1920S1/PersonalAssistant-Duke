package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import money.Account;
import money.BankTracker;
import controlpanel.Ui;

import java.util.ArrayList;

/**
 * This class responds to "list bank trackers" command to list down all
 * the existing bank account trackers.
 */
public class ListBankTrackerCommand extends MoneyCommand{

    //@@ cctt1014
    public ListBankTrackerCommand() {
    }

    /**
     * This method labels whether this command means ceasing the overall program.
     * @return Whether this command means ceasing the overall program.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method execute the list command to list down all the existing bank
     * account trackers by getting the information from account object.
     * @param account The class record all the financial information of the user
     * @param ui The user interface
     * @param storage The class used to store the information to the local disk
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) {
        ArrayList<BankTracker> accountList = account.getBankTrackerList();
        ui.appendToOutput("Here are the bank accounts and their info:\n");
        for (int i = 0; i < accountList.size(); i++) {
            ui.appendToOutput((i+1) + ". ----------------------------------------\n");
            ui.appendToOutput(accountList.get(i).getBankAccountInfo() + "\n");
            ui.appendToOutput("-------------------------------------------\n");
        }
    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}
