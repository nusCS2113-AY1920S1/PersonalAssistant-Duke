package seedu.hustler.logic.command.task;

import seedu.hustler.Hustler;
import seedu.hustler.data.AvatarStorage;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.ui.Ui;
import seedu.hustler.schedule.Scheduler;
import seedu.hustler.logic.parser.anomaly.DoneAnomaly;
import java.io.IOException;
import seedu.hustler.logic.parser.EditCommandParser;
import seedu.hustler.logic.command.task.editcommands.Edit;

/**
 * Command to do task in list.
 */
public class EditCommand extends Command {
    /**
     * User input that contains index of task to do.
     */
    private String[] userInput;
    
    /**
     * Parser that parses edit commands;
     */
    private EditCommandParser parser = new EditCommandParser();

    /**
    * Initializes userInput.
    *
    * @param userInput input that contains edit
    */
    public EditCommand(String[] userInput) {
        this.userInput = userInput;
    }

    /**
     * Does task at index taskIndex inside.
     */
    public void execute() {
        Ui ui = new Ui();

        try {
            Edit edit = parser.parse(userInput[1]);
            edit.execute(); 
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
        }
    }
}
