package moneycommands;

import controlpanel.*;
import money.Account;

public class initCommand extends MoneyCommand{

    private String inputString;


    public initCommand(String cmd, boolean isNewUser) throws DukeException {
        inputString = cmd;
        if(!isNewUser){
            throw new DukeException("You're an existing user");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) {
        //String userSavings = inputString.split(" ")[1];
        //String avgExp = inputString.split(" ")[2];
        float userSavings = Float.parseFloat(inputString.split(" ")[1]);
        float avgExp = Float.parseFloat(inputString.split(" ")[2]);
        account.initialize(userSavings,avgExp);
        storage.writeToFile(account);

        ui.appendToOutput("Initialised, you're ready to use Financial Ghosts\n");
        //ui.appendToOutput(avgExp + "\n");
    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) { return; }

}
