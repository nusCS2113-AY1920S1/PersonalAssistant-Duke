package seedu.hustler.logic.command.task;

import javafx.application.Platform;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.OneWordAnomaly;
import seedu.hustler.ui.Ui;

/**
 * Command the quits the entire Hustler program.
 */
public class ByeCommand extends Command {

    private final String[] userInput;
    private OneWordAnomaly byeAnomaly = new OneWordAnomaly();

    public ByeCommand(String[] userInput) {
        this.userInput = userInput;
    }

    /**
     * Prints a good bye message and safely exits the program.
     *
     */
    public void execute() {
        Ui ui = new Ui();
        try {
            byeAnomaly.detect(userInput);
            ui.show_bye_message();
            Platform.exit();
        } catch(CommandLineException e) {
            ui.show_message(e.getMessage());
        }


    }
}
