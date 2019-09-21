package seedu.duke.command;

import seedu.duke.task.TaskList;
import seedu.duke.ui.Ui;

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
     *
     * @param list list of tasks 
     */
    public void execute(TaskList list) {
        if (this.userInput.length == 1) {
            Ui ui = new Ui();
            ui.empty_description_error();
        }
        list.findTask(this.userInput[1]);
    }
}
