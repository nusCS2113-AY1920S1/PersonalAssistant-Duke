package seedu.hustler.logic.command.task;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.DeleteAnomaly;
import seedu.hustler.ui.Ui;

/**
 * Command that deletes task in list.
 */
public class DeleteCommand extends Command {
    /**
     * User input that contains index of task to delete.
     */
    private String[] userInput;

    /**
     * Detect anomalies for input.
     */
    private DeleteAnomaly anomaly = new DeleteAnomaly();

    /**
    * Initializes userInput.
    *
    * @param userInput array that contains task id to delete
    */
    public DeleteCommand(String[] userInput) {
        this.userInput = userInput;
    }

    /**
     * Deletes task at index taskIndex inside.
     */
    public void execute() {
        Ui ui = new Ui();
        try {
            anomaly.detect(userInput);
            if (userInput[1].equals("all")) {
                Hustler.list.clearList();
                return;
            }
            int taskIndex = Integer.parseInt(userInput[1]) - 1;
            Hustler.list.removeTask(taskIndex);
        } catch (CommandLineException e) {
            ui.show_message(e.getMessage());
        }
    }
}
