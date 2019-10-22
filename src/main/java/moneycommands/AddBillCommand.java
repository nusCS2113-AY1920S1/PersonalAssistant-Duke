package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Parser;
import controlpanel.Ui;
import money.Account;
import money.Bill;
import money.Expenditure;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;

public class AddBillCommand extends MoneyCommand {

    private String inputString;

    //@@ Chianhaoplanks
    public AddBillCommand (String command) {
        inputString = command.replaceFirst("add bill", "");
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        String[] splitStr = inputString.split("/amt ", 2);
        String description = splitStr[0];
        String[] furSplit = splitStr[1].split("/lastpaid ", 2);
        float price = Float.parseFloat(furSplit[0]);
        String[] morSplit = furSplit[1].split(" /paynext ", 2);
        LocalDate boughtDate = Parser.shortcutTime(morSplit[0]);
        LocalDate nextPayDay = Parser.shortcutTime(morSplit[1]);
        Bill bill = new Bill(price, description, "bills", boughtDate, nextPayDay);
        account.getExpListTotal().add(bill);
        storage.writeToFile(account);

        Calendar currDate = Calendar.getInstance();
        int currMonth = currDate.get(Calendar.MONTH) + 1;
        int currYear = currDate.get(Calendar.YEAR);
        if (boughtDate.getMonthValue() == currMonth && boughtDate.getYear() == currYear) {
            account.getExpListCurrMonth().add(bill);
        }

        ui.appendToOutput(" Got it. I've added this bill to your total spending: \n");
        ui.appendToOutput("     ");
        ui.appendToOutput(account.getExpListTotal().get(account.getExpListTotal().size() - 1).toString() + "\n");
        ui.appendToOutput(" Now you have " + account.getExpListTotal().size() + " expenses listed\n");
    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        int lastIndex = account.getExpListTotal().size() - 1;
        String billDes = account.getExpListTotal().get(lastIndex).toString();
        account.getExpListTotal().remove(lastIndex);
        storage.writeToFile(account);

        ui.appendToOutput(" Last command undone: \n");
        ui.appendToOutput( billDes + "\n");
        ui.appendToOutput(" Now you have " + account.getExpListTotal().size() + " expenses listed\n");
    }

}
