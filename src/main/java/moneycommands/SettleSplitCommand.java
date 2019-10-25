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
import java.util.ArrayList;

/**
 * This command settles a debt within a split expenditure in the Total Expenditure List.
 */
public class SettleSplitCommand extends MoneyCommand {

    private String inputString;
    private int serialNo;
    private Split sToSettle;

    /**
     * Constructor of the command which initialises the settle split expenditure command
     * with the data for the index of the split expenditure in the Total Expenditure List
     * and the person who has settled the debt
     * @param command Settle split expenditure command inputted from user
     */
    //@@author chengweixuan
    public SettleSplitCommand(String command) {
        inputString = command.replaceFirst("settle ", "");
    }

    @Override
    public boolean isExit() {
        return false;
    }

    private int getSettleNo(String person, ArrayList<Pair<String, Boolean>> parties, String description) throws DukeException {

        if (Parser.isNumeric(person)) {
            int personIndex = Integer.parseInt(person) - 1;
            if (!parties.get(personIndex).getValue()) {
                return Integer.parseInt(person) - 1;
            }
        } else {
            Pair<String, Boolean> toSearch = new Pair<>(person, false);
            if (parties.contains(toSearch)) {
                return parties.indexOf(toSearch);
            }
        }
        throw new DukeException(person + " does not owe you for " + description + "\n");
    }

    private Split getSplitToSettle(ArrayList<Expenditure> expList) throws DukeException {
        Expenditure s = expList.get(serialNo);
        if (!(s instanceof Split)) {
            throw new DukeException("Oops! Index given is not a Split Expenditure!");
        }
        return (Split)s;
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
        int settleNo;
        try {
            String[] splitStr = inputString.split(" ", 2);
            serialNo = Integer.parseInt(splitStr[0]) - 1;
            sToSettle = getSplitToSettle(account.getExpListTotal());
            settleNo = getSettleNo(splitStr[1], sToSettle.getParties(), sToSettle.getDescription());
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("The serial number of the expenditure is Out Of Bounds!");
        }

        try {
            sToSettle.hasSettledSplit(settleNo);
            account.getExpListTotal().set(serialNo, sToSettle);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("The serial number of the party specified is Out of Bounds!\n");
        }

        float price = sToSettle.getEachOwe();
        String description = "Repayment for " + sToSettle.getDescription();
        LocalDate payday = Parser.shortcutTime("now");
        Income i = new Income(price, description, payday);
        account.getIncomeListTotal().add(i);
        storage.writeToFile(account);

        String personName = sToSettle.getNameOfPerson(settleNo);
        ui.appendToOutput(" Got it. " + personName + " has settled his debt on the split expenditure: \n");
        ui.appendToOutput("     " + account.getExpListTotal().get(serialNo).toString() + "\n");
        if (sToSettle.getStatus()) {
            ui.appendToOutput("The split debt has been settled\n");
        } else {
            ui.appendToOutput("The split debt is still outstanding. Outstanding amount: $" +
                    sToSettle.getOutstandingAmount() + "\n");
        }
    }


    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }

}
