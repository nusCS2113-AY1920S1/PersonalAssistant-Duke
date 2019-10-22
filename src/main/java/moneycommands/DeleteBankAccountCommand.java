package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Parser;
import controlpanel.Ui;
import money.Account;
import money.BankTracker;
import money.Expenditure;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * This class is used to delete the bank tracker from the list
 * and withdraw the money from the total saving as a expenditure
 */
public class DeleteBankAccountCommand extends MoneyCommand {

    private int index;

    public DeleteBankAccountCommand(String inputString) {
        inputString = inputString.replaceFirst("delete bank-account ", "");
        index = Integer.parseInt(inputString)-1;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        BankTracker bankTracker = account.getBankTrackerList().get(index);
        Expenditure expenditure = new Expenditure(bankTracker.getAmt(), "Withdraw from "
                + bankTracker.getDescription(), "bank", Parser.shortcutTime("now"));
        account.getExpListTotal().add(expenditure);

        Calendar currDate = Calendar.getInstance();
        int currMonth = currDate.get(Calendar.MONTH) + 1;
        int currYear = currDate.get(Calendar.YEAR);
        LocalDate date = Parser.shortcutTime("now");
        if (date.getMonthValue() == currMonth && date.getYear() == currYear) {
            account.getExpListCurrMonth().add(expenditure);
        }
        storage.markDeletedEntry("BAN", index + 1);
        account.getBankTrackerList().remove(index);
        ui.appendToOutput("The bank account tracker below has been removed: \n");
        ui.appendToOutput(bankTracker.getBankAccountInfo() + "\n");
    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
       storage.undoDeletedEntry(account, "BAN", index + 1);
       Expenditure exp = account.getExpListTotal().get(account.getExpListTotal().size() - 1);
       if (exp == account.getExpListCurrMonth().get(account.getExpListCurrMonth().size() - 1)) {
           account.getExpListCurrMonth().remove(exp);
       }
       storage.writeToFile(account);

        ui.appendToOutput(" Last command undone: \n");
        ui.appendToOutput(account.getBankTrackerList().get(index).getBankAccountInfo() + "\n");
        ui.appendToOutput(" Now you have " + account.getBankTrackerList().size() + " banks listed\n");
    }
}
