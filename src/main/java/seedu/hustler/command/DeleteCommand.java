package seedu.hustler.command;

import seedu.hustler.Hustler;
import seedu.hustler.ui.Ui;
import seedu.hustler.parser.ParserForCommand;

/**
 * Command that deletes task in list.
 */
public class DeleteCommand extends Command {

    /**
     * User input that contains index of task to delete.
     */
    private String[] userInput;
   
    /**
    * Initializes userInput.
    *
    * @param userInput array that contains task id to delete.
    */
    public DeleteCommand(String[] userInput) {
        this.userInput = userInput;
    }

    /**
     * Deletes task at index taskIndex inside.
     *
     */
    public void execute() {
        if (this.userInput.length == 1) {
            Ui ui = new Ui();
            ui.empty_description_error();
            return;
        }
        ParserForCommand doneParser = new ParserForCommand("delete");
        int taskIndex = doneParser.parseIndex(this.userInput[1]).intValue();
        Hustler.list.removeTask(taskIndex);
    }
}
