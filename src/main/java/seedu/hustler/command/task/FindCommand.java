package seedu.hustler.command.task;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.ui.Ui;

/**
 * Command that lists tasks in TaskList instance.
 */
public class FindCommand extends Command {
    /**
     * User input that contains keyword to search for in list.
     */
    private String[] userInput;

    /**
     * Initializes userInput.
     *
     * @param userInput the array of users inputs to initialize with.
     */
    public FindCommand(String[] userInput) {
        this.userInput = userInput;
    }
    
    /**
     * Lists commands which contain keyword.
     */
    public void execute() {
        if (this.userInput.length == 1) {
            Ui ui = new Ui();
            ui.empty_description_error();
        }
        Hustler.list.findTask(this.userInput[1]);
    }
}
