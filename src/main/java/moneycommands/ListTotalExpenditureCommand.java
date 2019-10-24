package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Expenditure;

/**
 * This command lists all expenditures within the Total Expenditure List to the user.
 */
public class ListTotalExpenditureCommand extends MoneyCommand {

    /**
     * Constructor of the list command.
     */
    //@@author chengweixuan
    public ListTotalExpenditureCommand(){
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the list all expenditure command.
     * Displays all expenditures in the Total Expenditure List to the user according to index.
     * @param account Account object containing all financial info of user saved on the programme
     * @param ui Handles interaction with the user
     * @param storage Saves and loads data into/from the local disk
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) {
        int counter = 1;
        for (Expenditure i : account.getExpListTotal()) {
            //System.out.println(" " + counter + "." + i.toString() + "\n");
            ui.appendToGraphContainer(" " + counter + "." + i.toString() + "\n");
            counter++;
        }
        //System.out.println("Total income so far: $" + account.getTotalExp());
        ui.appendToGraphContainer("Total expenditure so far: $" + account.getTotalExp() + "\n");
        ui.appendToOutput("Got it, list will be printed in the other pane!\n");

    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}
