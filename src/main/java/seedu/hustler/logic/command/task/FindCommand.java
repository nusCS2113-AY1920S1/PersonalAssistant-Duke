package seedu.hustler.logic.command.task;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.FindAnomaly;
import seedu.hustler.ui.Ui;

/**
 * Command that list given tasks with the given keyword.
 */
public class FindCommand extends Command {
    /**
     * User input that contains keyword to search for in the task list.
     */
    private String[] userInput;

    /**
     * The anomaly class to check if the given command is valid.
     */
    private FindAnomaly anomaly = new FindAnomaly();

    /**
     * Initializes userInput.
     * @param userInput the array of users inputs to initialize with.
     */
    public FindCommand(String[] userInput) {
        this.userInput = userInput;
    }
    
    @Override
    public void execute() {
        Ui ui = new Ui();
        try {
            anomaly.detect(userInput);
            ui.showMatchingTasks(Hustler.list.findTasks(this.userInput[1]), this.userInput[1]);
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
        }
    }
}
