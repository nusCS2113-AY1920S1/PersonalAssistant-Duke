package seedu.hustler.logic.command.task;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.OneWordAnomaly;
import seedu.hustler.ui.Ui;

/**
 * Command that lists tasks in TaskList instance.
 */
public class ListCommand extends Command {

    private final String[] userInput;
    private OneWordAnomaly listAnomaly = new OneWordAnomaly();

    public ListCommand(String[] userInput) {
        this.userInput = userInput;
    }

    /**
     * Lists commands in TaskList instance.
     */
    public void execute() {
        Ui ui = new Ui();
        try {
            listAnomaly.detect(userInput);
            Hustler.list.displayList();
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
        }
    }
}
