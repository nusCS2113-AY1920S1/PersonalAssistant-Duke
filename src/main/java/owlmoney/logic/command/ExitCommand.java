package owlmoney.logic.command;

import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * Executes ExitCommand and ends OwlMoney.
 */
public class ExitCommand extends Command {

    /**
     * Creates an instance of ExitCommand.
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
