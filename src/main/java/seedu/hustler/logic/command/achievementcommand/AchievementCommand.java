package seedu.hustler.logic.command.achievementcommand;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.OneWordAnomaly;
import seedu.hustler.ui.Ui;

/**
 * Command to see all achievements.
 */
public class AchievementCommand extends Command {

    private String []userInput;
    private OneWordAnomaly anomaly;

    public AchievementCommand(String[] userInput) {
        this.userInput = userInput;
        this.anomaly = new OneWordAnomaly();
    }

    /**
     * Adds task of type and description inside taskInfo.
     *
     */
    public void execute() {
        Ui ui = new Ui();
        try {
            anomaly.detect(userInput);
            ui.showAchievementList(Hustler.achievementList.getAchievementList());
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
        }
    }
}
