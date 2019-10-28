package seedu.hustler.logic.command.achievementCommand;

import seedu.hustler.Hustler;
import seedu.hustler.logic.command.Command;

/**
 * Command to see all achievements.
 */
public class AchievementCommand extends Command {

    /**
     * Adds task of type and description inside taskInfo.
     *
     */
    public void execute() {
        Hustler.achievementList.list();
        //AchievementList.showList();
    }
}
