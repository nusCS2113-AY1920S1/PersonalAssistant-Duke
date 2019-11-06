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
public class EditDifficultyCommand extends Edit {
    /**
     * Detects anomalies for input.
     **/
    /* private DoneAnomaly anomaly = new DoneAnomaly(); */

    /**
     * The difficulty to be set.
     */
    private String difficulty;

    /**
    * Initializes task to be edited.
    *
    * @param userInput input that contains task id to do
    */
    public EditDifficultyCommand(Task task, String difficulty) {
        super(task);
        this.difficulty = difficulty;
    }

    /**
     * Does task at index taskIndex inside.
     */
    public void execute() {
        this.task.setDifficulty(this.difficulty); 
    }
}
