package MoneyCommands;

import controlpanel.DukeException;
import controlpanel.Storage;
import controlpanel.Ui;
import Money.Account;
import Money.Expenditure;
import controlpanel.DukeException;
import controlpanel.Storage;
import controlpanel.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddExpenditureCommand extends MoneyCommand {

    private String inputString;
    private SimpleDateFormat simpleDateFormat;

    public AddExpenditureCommand(String command) {
        inputString = command.replaceFirst("spent ", "");
        simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, Storage storage) throws ParseException, DukeException {
        String[] splitStr = inputString.split("/amt ", 2);
        String description = splitStr[0];
        String[] furSplit = splitStr[1].split("/cat ", 2);
        float price = Float.parseFloat(furSplit[0]);
        String[] morSplit = furSplit[1].split("/on", 2);
        String category = morSplit[0];
        Date boughtTime = simpleDateFormat.parse(morSplit[1]);
        Expenditure e = new Expenditure(price, description, category, boughtTime);
        account.getExpListTotal().add(e);


//        System.out.println(" Got it. I've added this to your total spending: \n");
//        System.out.println("     " + account.getExpListTotal().get(account.getExpListTotal().size() - 1).toString() + "\n");
//        System.out.println(" Now you have " + account.getExpListTotal().size() + " expenses listed");

        ui.appendToOutput(" Got it. I've added this to your total spending: \n");
        ui.appendToOutput("     " + account.getExpListTotal().get(account.getExpListTotal().size() - 1).toString() + "\n");
        ui.appendToOutput(" Now you have " + account.getExpListTotal().size() + " expenses listed\n");
    }
}