package seedu.hustler.command;

import seedu.hustler.game.achievement.AchievementList;

/**
 * Command to see all achievements.
 */
public class AchievementCommand extends Command {
    /**
     * Adds task of type and description inside taskInfo.
     */
    public void execute() {
        AchievementList.showList();
    }
}
