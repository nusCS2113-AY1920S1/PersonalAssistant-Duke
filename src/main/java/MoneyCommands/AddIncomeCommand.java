package MoneyCommands;

import controlpanel.DukeException;
import controlpanel.Storage;
import controlpanel.Ui;
import Money.Account;
import Money.Income;
import controlpanel.DukeException;
import controlpanel.Storage;
import controlpanel.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddIncomeCommand extends MoneyCommand {

    private String inputString;
    private SimpleDateFormat simpleDateFormat;

    public AddIncomeCommand(String command) {
        inputString = command.replaceFirst("add income ", "");
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy");
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, Storage storage) throws ParseException, DukeException {
        String[] splitStr = inputString.split("/amt ", 2);
        String description = splitStr[0];
        String[] furSplit = splitStr[1].split("/payday ", 2);
        float salary = Float.parseFloat(furSplit[0]);
        Date payDay = simpleDateFormat.parse(furSplit[1]);
        Income i = new Income(salary, description, payDay);
        account.getIncomeListTotal().add(i);

//        System.out.println(" Got it. I've added this income source: \n");
//        System.out.println("     " + account.getIncomeListTotal().get(account.getIncomeListTotal().size()-1).toString() + "\n");
//        System.out.println(" Now you have " + account.getIncomeListTotal().size() + " income sources listed");

        ui.appendToOutput(" Got it. I've added this income source: \n");
        ui.appendToOutput("     " + account.getIncomeListTotal().get(account.getIncomeListTotal().size()-1).toString() + "\n");
        ui.appendToOutput(" Now you have " + account.getIncomeListTotal().size() + " income sources listed\n");
    }
}
