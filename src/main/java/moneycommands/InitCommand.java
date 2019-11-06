package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;

public class InitCommand extends MoneyCommand {

    private String inputString;
    private float userSavings;
    private float avgExp;

    //@@author therealnickcheong

    /**
     * command to initialise Financial Ghost.
     * @param cmd command entered by user.
     * @param isNewUser checks if is a first time user.
     * @throws DukeException if is an existing user.
     */
    public InitCommand(String cmd, boolean isNewUser) throws DukeException {
        inputString = cmd;
        if (!isNewUser) {
            throw new DukeException("You're an existing user");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        try {
            userSavings = Float.parseFloat(inputString.split(" ")[1]);
            avgExp = Float.parseFloat(inputString.split(" ")[2]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new DukeException("Please enter in the format: "
                    + "init [existing savings] [Avg Monthly Expenditure]\n");
        }

        account.initialize(userSavings,avgExp);
        storage.writeToFile(account);

        ui.appendToOutput("Initialised, you're ready to use Financial Ghosts\n");
    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }

}
