package owlmoney.logic.command.goals;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

/**
 * ListGoalsCommand class which contains the execution function to list goals object.
 */
public class ListGoalsCommand extends Command {

    /**
     * Executes the function to list goals in the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) {
        profile.profileListGoals(ui);
        return this.isExit;
    }
}
