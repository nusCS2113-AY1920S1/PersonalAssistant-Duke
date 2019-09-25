package seedu.duke.command;

import seedu.duke.task.TaskList;
import seedu.duke.parser.ParserForCommand;
import seedu.duke.ui.Ui;

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
     *
     * @param list list of Tasks
     */
    public void execute(TaskList list) {
        if (this.userInput.length == 1) {
            Ui ui = new Ui();
            ui.empty_description_error();
            return;
        }
        ParserForCommand doneParser = new ParserForCommand("done");
        int taskIndex = doneParser.parseIndex(this.userInput[1]).intValue();
        list.doTask(taskIndex);
    }
}
