package moneycommands;

import controlpanel.*;
import money.Account;

public class initCommand extends MoneyCommand{

    private String inputString;

    //@@ therealnickcheong
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

        float userSavings = Float.parseFloat(inputString.split(" ")[1]);
        float avgExp = Float.parseFloat(inputString.split(" ")[2]);
        account.initialize(userSavings,avgExp);
        storage.writeToFile(account);

        ui.appendToOutput("Initialised, you're ready to use Financial Ghosts\n");
    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }

}
