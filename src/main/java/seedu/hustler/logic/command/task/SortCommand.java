package seedu.hustler.logic.command.task;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.SortAnomaly;
import seedu.hustler.ui.Ui;

/**
 * Command that sorts the task list.
 */
public class SortCommand extends Command {
    /**
     * User input that contains the way to sort the tasks.
     */
    private String[] sortType;

    /**
     * Detect anomalies for input.
     */
    private SortAnomaly anomaly = new SortAnomaly();

    /**
     * Initializes the sortType.
     *
     * @param sortType type of sort.
     */
    public SortCommand(String[] sortType) {
        this.sortType = sortType;
    }

    /**
     * Sorts the task list.
     */
    public void execute() {
        Ui ui = new Ui();
        try {
            anomaly.detect(sortType);
            Hustler.list.sortTask(sortType[0]);
        } catch (CommandLineException e) {
            ui.show_message(e.getMessage());
        }
    }
}
