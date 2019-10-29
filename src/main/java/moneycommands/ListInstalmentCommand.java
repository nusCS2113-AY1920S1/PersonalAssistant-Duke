package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Instalment;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * This command lists all Instalments within the Instalments List to the user.
 */
public class ListInstalmentCommand extends MoneyCommand {

    //@@author ChenChao19
    /**
     * Constructor of the list command.
     */
    public ListInstalmentCommand(){
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    /**
     * This method executes the list instalments command.
     * Displays all Instalments in the Instalments List to the user according to index
     * @param account Account object containing all financial info of user saved on the programme
     * @param ui Handles interaction with the user
     * @param storage Saves and loads data into/from the local disk
     */
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        int counter = 1;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        for (Instalment i : account.getInstalments()) {
            ui.appendToGraphContainer(" " + counter + ".[" + df.format(i.getPercentage()) + "%] "
                    + i.getDescription() + " ($" + df.format(i.equalMonthlyInstalment())
                    + " per month until " + i.getDateEndDate() + ")\n");
            counter++;
        }
        ui.appendToOutput("Got it, list will be printed in the other pane!\n");
    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}