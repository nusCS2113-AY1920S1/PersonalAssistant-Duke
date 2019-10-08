package seedu.hustler.command;

import seedu.hustler.Hustler;
import seedu.hustler.game.achievement.AchievementList;
import seedu.hustler.ui.Ui;

public class AchievementCommand extends Command{

    /**
     * Adds task of type and description inside taskInfo.
     *
     */
    public void execute() {
        AchievementList.showList();
    }
}
