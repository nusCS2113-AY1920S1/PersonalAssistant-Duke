package owlmoney.logic.command.goals;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

import java.util.logging.Logger;

import static owlmoney.commons.log.LogsCenter.getLogger;

/**
 * Executes ListGoalsCommand to list goals object.
 */
public class ListGoalsCommand extends Command {

    private static final Logger logger = getLogger(ListGoalsCommand.class);

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
        logger.info("Successful execution of listing goals");
        return this.isExit;
    }
}
