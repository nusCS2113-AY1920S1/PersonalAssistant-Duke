package owlmoney.logic.command.goals;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

import java.util.logging.Logger;

import static owlmoney.commons.log.LogsCenter.getLogger;

/**
 * Executes ListAchievementCommand to list all achievements.
 */
public class ListAchievementCommand extends Command {

    private static final Logger logger = getLogger(ListAchievementCommand.class);

    /**
     * Executes the function to list achievements in the profile.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) {
        profile.profileListAchievement(ui);
        logger.info("Successful execution of listing achievements");
        return this.isExit;
    }
}
