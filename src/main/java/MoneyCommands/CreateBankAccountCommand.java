package MoneyCommands;

import Money.Account;
import Money.BankTracker;
import controlpanel.Storage;
import controlpanel.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CreateBankAccountCommand extends MoneyCommand {

    private BankTracker newTracker;

    public CreateBankAccountCommand(String inputString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
        String desc = inputString.split(" /amt ")[0];
        String info = inputString.split(" /amt ")[1];
        desc = desc.replaceFirst("bank-account ","");
        String[] words = info.split(" ");
        newTracker = new BankTracker(desc, Integer.parseInt(words[0]),
                simpleDateFormat.parse(words[2]), Double.parseDouble(words[4]));
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, Storage storage) {
        account.getBankTrackerList().add(newTracker);
        ui.appendToOutput("New bank account tracker has been added to the list: \n");
        ui.appendToOutput(newTracker.getBankAccountInfo() + "\n");
    }
}