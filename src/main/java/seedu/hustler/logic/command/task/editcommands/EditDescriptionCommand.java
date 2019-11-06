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
public class EditDescriptionCommand extends Edit {
    /**
     * Detects anomalies for input.
     **/
    /* private DoneAnomaly anomaly = new DoneAnomaly(); */

    /**
     * Description to be edited.
     */
    private String description;

    /**
    * Initializes task to be edited.
    *
    * @param index index of task to be edited in Hustler's TaskList
    * @param description description to be set to
    */
    public EditDescriptionCommand(int index, String description) {
        super(index);
        this.description = description;
    }

    /**
     * Does task at index taskIndex inside.
     */
    public void execute() {
        Ui ui = new Ui();
        Hustler.list.get(this.index).setDescription(this.description); 
        String output = "The task description has been changed: \n\t\t" 
            + Hustler.list.get(this.index).toString();
        ui.showMessage(output); 
    }
}
