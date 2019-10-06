package seedu.hustler.command;

import seedu.hustler.Hustler;
import seedu.hustler.ui.Ui;
import seedu.hustler.parser.ParserForCommand;

/**
 * Command that snoozes tasks.
 */
public class SnoozeCommand extends Command {

    /**
     * User input that contains index of task to snooze.
     */
    private String[] userInput;
   
    /**
    * Initializes userInput.
    *
    * @param userInput array that contains task id to delete.
    */
    public SnoozeCommand(String[] userInput) {
        this.userInput = userInput;
    }

    /**
     * Deletes task at index inside userInput.
     *
     */
    public void execute() {
        if (this.userInput.length == 1) {
            Ui ui = new Ui();
            ui.empty_description_error();
            return;
        }
        ParserForCommand doneParser = new ParserForCommand("snooze");
        int taskIndex = doneParser.parseIndex(this.userInput[1]).intValue();
        Hustler.list.snoozeTask(taskIndex);
    }
}
