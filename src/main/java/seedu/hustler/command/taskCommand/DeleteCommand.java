package seedu.hustler.command.taskCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.data.CommandLog;
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
            CommandLog.deleteLatestLoggedCommand();
            return;
        }
        ParserForCommand doneParser = new ParserForCommand("delete");
        if (userInput[1].equals("all")) {
            Hustler.list.clearList();
        } else {
            int taskIndex = doneParser.parseIndex(this.userInput[1]).intValue();
            Hustler.list.removeTask(taskIndex);
        }
    }
}
