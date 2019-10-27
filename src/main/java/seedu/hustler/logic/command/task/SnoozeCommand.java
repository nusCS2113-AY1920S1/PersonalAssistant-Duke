package seedu.hustler.logic.command.task;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.data.CommandLog;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.parser.anomaly.SnoozeAnomaly;
import seedu.hustler.ui.Ui;
import seedu.hustler.parser.ParserForCommand;

/**
 * Command that snoozes tasks.
 */
public class SnoozeCommand extends Command {
    /**
     * User input to parse.
     */
    private String[] userInput;

    /**
     * Detect anomalies for input.
     */
    private SnoozeAnomaly anomaly = new SnoozeAnomaly();

    /**
     * Initializes user input.
     *
     * @param userInput user input
     */
    public SnoozeCommand(String[] userInput) {
        this.userInput = userInput;
    }

    /**
     * Deletes task at index inside userInput.
     */
    public void execute() {
        Ui ui = new Ui();
        try {
            anomaly.detect(userInput);
            int taskIndex = Integer.parseInt(userInput[0]);
            Hustler.list.snoozeTask(taskIndex, userInput);
        } catch (CommandLineException e) {
            ui.show_message(e.getMessage());
        }
    }
}
