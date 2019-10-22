package seedu.hustler.command.taskCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
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
    * @param rawInput array that contains task id to delete.
    */
    public SnoozeCommand(String rawInput) {
        this.userInput = rawInput.split(" ");
    }

    /**
     * Deletes task at index inside userInput.
     */
    public void execute() {
        if (this.userInput.length == 1) {
            Ui ui = new Ui();
            ui.empty_description_error();
            return;
        }
        ParserForCommand doneParser = new ParserForCommand("snooze");
        int taskIndex = doneParser.parseIndex(this.userInput[1]).intValue();
        Hustler.list.snoozeTask(taskIndex, userInput);
    }
}
