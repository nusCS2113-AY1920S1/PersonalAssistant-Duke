package seedu.hustler.logic.command.task.editcommands;

import seedu.hustler.Hustler;
import seedu.hustler.data.AvatarStorage;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.ui.Ui;
import seedu.hustler.schedule.Scheduler;
import seedu.hustler.logic.parser.anomaly.EditDifficultyAnomaly;
import seedu.hustler.task.Task;

import java.io.IOException;

/**
 * Command to do task in list.
 */
public class EditDifficultyCommand extends Edit {
    /**
     * Detects anomalies for input.
     **/
    private EditDifficultyAnomaly anomaly = new EditDifficultyAnomaly();

    /**
     * The difficulty to be set.
     */
    private String difficulty;

    /**
    * Initializes task to be edited.
    *
    * @param index index of task in Hustler's TaskList
    * @param difficulty difficulty to be set to
    */
    public EditDifficultyCommand(int index, String difficulty) {
        super(index);
        this.difficulty = difficulty;
    }

    /**
     * Does task at index taskIndex inside.
     */
    public void execute() {
        Ui ui = new Ui();
        try {
            anomaly.detect(index, difficulty);
            Hustler.list.get(this.index).setDifficulty(this.difficulty); 
            String output = "The task difficulty has been changed: \n\t\t" 
                + Hustler.list.get(this.index).toString();
            ui.showMessage(output); 
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
        }
    }
}
