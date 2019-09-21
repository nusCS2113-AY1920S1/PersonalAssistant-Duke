package seedu.duke.command;

import seedu.duke.task.TaskList;
import seedu.duke.ui.Ui;
import seedu.duke.parser.ParserForCommand;

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
     * @param list list of tasks
     */
    public void execute(TaskList list) {
        if (this.userInput.length == 1) {
            Ui ui = new Ui();
            ui.empty_description_error();
            return;
        }
        ParserForCommand doneParser = new ParserForCommand("delete");
        int taskIndex = doneParser.parseIndex(this.userInput[1]).intValue();
        list.removeTask(taskIndex);
    }
}
