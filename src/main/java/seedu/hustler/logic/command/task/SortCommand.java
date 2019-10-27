package seedu.hustler.logic.command.task;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.SortAnomaly;
import seedu.hustler.ui.Ui;

/**
 * Command that sorts the task list.
 */
public class SortCommand extends Command {
    /**
     * User input to parse.
     */
    private String[] userInput;

    /**
     * Detect anomalies for input.
     */
    private SortAnomaly anomaly = new SortAnomaly();

    /**
     * Initializes the sortType.
     *
     * @param userInput type of sort.
     */
    public SortCommand(String[] userInput) {
        this.userInput = userInput;
    }

    /**
     * Sorts the task list.
     */
    public void execute() {
        Ui ui = new Ui();
        try {
            anomaly.detect(userInput);
            Hustler.list.sortTask(userInput[1]);
        } catch (CommandLineException e) {
            ui.show_message(e.getMessage());
        }
    }
}
