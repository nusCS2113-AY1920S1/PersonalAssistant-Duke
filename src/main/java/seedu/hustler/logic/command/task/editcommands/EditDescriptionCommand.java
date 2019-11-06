package seedu.hustler.logic.command.task.editcommands;

import seedu.hustler.Hustler;
import seedu.hustler.data.AvatarStorage;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.ui.Ui;
import seedu.hustler.schedule.Scheduler;
import seedu.hustler.logic.parser.anomaly.DoneAnomaly;
import seedu.hustler.task.Task;

import java.io.IOException;

/**
 * Command to do task in list.
 */
public class EditDescriptionCommand extends EditCommand {
    /**
     * Detects anomalies for input.
     **/
    /* private DoneAnomaly anomaly = new DoneAnomaly(); */

    /**
     * 
     */
    String description;

    /**
    * Initializes task to be edited.
    *
    * @param userInput input that contains task id to do
    */
    public EditDescriptionCommand(Task task, String description) {
        super(task);
        this.description = description;
    }

    /**
     * Does task at index taskIndex inside.
     */
    public void execute() {
        Task.setDescription(this.description); 
    }
}
