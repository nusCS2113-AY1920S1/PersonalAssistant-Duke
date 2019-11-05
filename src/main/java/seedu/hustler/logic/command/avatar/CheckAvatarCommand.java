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
     * The given user input.
     */
    private String[] userInput;

    /**
     * The anomaly class to check if the given command is valid.
     */
    private OneWordAnomaly anomaly = new OneWordAnomaly();

    /**
     * Constructs a new check avatar command with the given user input.
     * @param userInput the user input made in the command line.
     */
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
