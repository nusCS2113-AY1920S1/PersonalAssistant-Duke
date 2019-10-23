package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Expenditure;


/**
 * This command lists all expenditures within the Month Expenditure List to the user.
 */
public class ListMonthExpenditureCommand extends MoneyCommand {

    /**
     * Constructor of the list command.
     */
    //@@author chengweixuan
    public ListMonthExpenditureCommand(){
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the list month expenditure command.
     * Displays all expenditures in the Month Expenditure List to the user according to index.
     * @param account Account object containing all financial info of user saved on the programme
     * @param ui Handles interaction with the user
     * @param storage Saves and loads data into/from the local disk
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) {
        int counter = 1;
        for (Expenditure i : account.getExpListCurrMonth()) {
            ui.appendToGraphContainer(" " + counter + "." + i.toString() + "\n");
            counter++;
        }

        ui.appendToGraphContainer("Total expenditure for the month: $" + account.getCurrMonthExp() + "\n");
        ui.appendToOutput("Got it, list will be printed in the other pane!\n");
    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}
