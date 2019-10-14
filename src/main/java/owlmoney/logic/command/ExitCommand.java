package owlmoney.logic.command;

import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * ExitCommand class which contains the execution function to terminate OwlMoney.
 */
public class ExitCommand extends Command {

    /**
     * Constructor to create an instance of ExitCommand.
     */
    public ExitCommand() {
        this.isExit = true;
    }

    /**
     * Executes the function to exit OwlMoney.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return true so OwlMoney will terminate after execution.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) {
        return isExit;
    }
}
