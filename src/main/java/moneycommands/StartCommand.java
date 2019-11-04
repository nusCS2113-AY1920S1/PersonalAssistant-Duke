package moneycommands;

import controlpanel.MoneyStorage;
import controlpanel.Ui;
import controlpanel.DukeException;
import money.Account;

public class StartCommand extends MoneyCommand {

    private String message;

    //@@author therealnickcheong

    /**
     * command to start Financial Ghost if is a first time user.
     * @param isNewUser check is the user is a first time user.
     * @throws DukeException tells user to use the init command if is a first time user.
     */
    public StartCommand(boolean isNewUser) throws DukeException {
        if (isNewUser) {
            message = "You are a new user please type: init [existing savings] [Avg Monthly Expenditure]\n";
        } else {
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
