package seedu.hustler.logic.command.avatar;

import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.parser.anomaly.OneWordAnomaly;
import seedu.hustler.ui.Ui;

/**
 * Command that checks the avatar's current status.
 */
public class CheckAvatarCommand extends Command {

    /**
     * The user input.
     */
    private String[] userInput;

    private OneWordAnomaly anomaly = new OneWordAnomaly();

    public CheckAvatarCommand(String[] userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute() {
        Ui ui = new Ui();
        try {
            anomaly.detect(userInput);
            ui.showAvatarStatistics();
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
        }
    }
}
