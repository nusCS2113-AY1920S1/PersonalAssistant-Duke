package seedu.hustler.command.task;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.parser.ParserForCommand;
import seedu.hustler.ui.Ui;

/**
 * Command to do task in list.
 */
public class DoneCommand extends Command {
    /**
     * User input that contains index of task to do.
     */
    private String[] userInput;
   
    /**
    * Initializes userInput.
    *
    * @param userInput input that contains task id to do.
    */
    public DoneCommand(String[] userInput) {
        this.userInput = userInput;
    }

    /**
     * Does task at index taskIndex inside.
     */
    public void execute() {
        if (this.userInput.length == 1) {
            Ui ui = new Ui();
            ui.empty_description_error();
            return;
        }
        ParserForCommand doneParser = new ParserForCommand("done");
        int taskIndex = doneParser.parseIndex(this.userInput[1]).intValue();
        Hustler.list.doTask(taskIndex);
    }
}
