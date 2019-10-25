package moneycommands;

import controlpanel.*;
import money.Account;

public class startCommand extends MoneyCommand{

    private String message;

    //@@author therealnickcheong
    public startCommand(boolean isNewUser) throws DukeException {
        if(isNewUser){
            message = "You are a new user please type: init [existing savings] [Avg Monthly Expenditure]\n";
        }else{
            throw new DukeException("You're an existing user");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) {
        //System.out.println("current Goal Savings: $" + account.getGoalSavings());
        ui.appendToOutput(message);

    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}
