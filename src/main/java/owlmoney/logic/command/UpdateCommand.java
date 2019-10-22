package owlmoney.logic.command;

import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * UpdateCommandClass which contains the execution function to update outdated recurring transactions.
 */
public class UpdateCommand extends Command {

    /**
     * Executes the function to update outdated recurring transactions.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return False so OwlMoney does not terminate yet.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) {
        profile.profileUpdate(ui);
        return isExit;
    }
}
