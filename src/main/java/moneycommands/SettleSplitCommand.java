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
import moneycommands.MoneyCommand;

import java.text.ParseException;
import java.time.LocalDate;


public class SettleSplitCommand extends MoneyCommand {

    private String inputString;

    public SettleSplitCommand(String command) {
        inputString = command.replaceFirst("settle ", "");
    }

    @Override
    public boolean isExit() {
        return false;
    }

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

    private boolean isNumeric(String checkStr) {
        try {
            int i = Integer.parseInt(checkStr);
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {

    }

}
