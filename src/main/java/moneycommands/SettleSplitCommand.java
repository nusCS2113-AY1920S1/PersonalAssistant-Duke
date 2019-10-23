package moneycommands;

import controlpanel.Parser;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import controlpanel.DukeException;
import javafx.util.Pair;
import money.Account;
import money.Expenditure;
import money.Income;
import money.Split;

import java.text.ParseException;
import java.time.LocalDate;

/**
 * This command settles a debt within a split expenditure in the Total Expenditure List.
 */
public class SettleSplitCommand extends MoneyCommand {

    private String inputString;

    /**
     * Constructor of the command which initialises the settle split expenditure command
     * with the data for the index of the split expenditure in the Total Expenditure List
     * and the person who has settled the debt
     * @param command Settle split expenditure command inputted from user
     */
    //@@ chengweixuan
    public SettleSplitCommand(String command) {
        inputString = command.replaceFirst("settle ", "");
    }

    /**
     * This method determines if the String inputted is a number or the name
     * of a person.
     * @param checkStr Name/Number of person
     * @return Boolean true if the String is a number, false if it is a name
     */
    private boolean isNumeric(String checkStr) {
        try {
            int i = Integer.parseInt(checkStr);
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the settle split expenditure command. Takes the input from the user
     * and checks the Total Expenditure List for the split expenditure entry.
     * Searches for the person specified by the user input in the list of people the expenditure
     * is split with, according to name or index depending on user input, and sets his/her debt
     * as settled.
     * Enters the debt paid into Total Income List.
     * @param account Account object containing all financial info of user saved on the programme
     * @param ui Handles interaction with the user
     * @param storage Saves and loads data into/from the local disk
     * @throws ParseException If invalid date is parsed
     * @throws DukeException When the command is invalid
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        String[] splitStr = inputString.split(" ");
        int serialNo = Integer.parseInt(splitStr[0]);
        int settleNo = -1;
        if (serialNo > account.getExpListTotal().size()) {
            throw new DukeException("The serial number of the expenditure is Out Of Bounds!");
        }
        Expenditure s = account.getExpListTotal().get(serialNo - 1);
        if (!(s instanceof Split)) {
            throw new DukeException("Oops! Index given is not a Split Expenditure!");
        }

        if (isNumeric(splitStr[1])) {
            settleNo = Integer.parseInt(splitStr[1]) - 1;
        } else {
            Pair<String, Boolean> toSearch = new Pair<>(splitStr[1], false);
            if (((Split) s).getParties().contains(toSearch)) {
                settleNo = ((Split) s).getParties().indexOf(toSearch);
            } else {
                throw new DukeException(splitStr[1] + " does not owe you for " + s.getDescription());
            }
        }

        ((Split) s).hasSettledSplit(settleNo);
        account.getExpListTotal().set(serialNo - 1, s); // need to see if this works

        float price = ((Split) s).getEachOwe();
        String description = "Repayment for " + s.getDescription();
        LocalDate payday = Parser.shortcutTime("now");
        Income i = new Income(price, description, payday);
        account.getIncomeListTotal().add(i);
        account.getIncomeListCurrMonth().add(i);
        storage.writeToFile(account);

        String statusStr;
        if (((Split) s).getOutstandingAmount().equals("")) {
            statusStr = "The split debt has been settled";
        } else {
            statusStr = "The split debt is still outstanding. Outstanding amount: $" +
                    ((Split) s).getOutstandingAmount();
        }

        String personName = ((Split) s).getNameOfPerson(settleNo);
        ui.appendToOutput(" Got it. " + personName + " has settled his debt on the split expenditure: \n");
        ui.appendToOutput("     " + account.getExpListTotal().get(serialNo - 1).toString() + "\n");
        ui.appendToOutput(statusStr + "\n");
    }


    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }

}
