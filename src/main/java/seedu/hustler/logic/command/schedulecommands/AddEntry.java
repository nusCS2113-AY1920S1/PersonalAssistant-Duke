package seedu.hustler.logic.command.schedulecommands;

import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.AddEntryAnomaly;
import seedu.hustler.schedule.RecommendedSchedule;
import seedu.hustler.ui.Ui;

/**
 * Adds an entry to recommended schedule.
 */
public class AddEntry extends Command {

    /**
     * User input to parse.
     */
    private String[] userInput;

    /**
     * Detects anomalies for input.
     */
    private AddEntryAnomaly anomaly = new AddEntryAnomaly();
    
    /**
     * Initializes user input with supplied input.
     *
     * @param userInput initialization of the userInput
     */
    public AddEntry(String[] userInput) {
        this.userInput = userInput;
    }

    /**
     * Adds an entry to the recommended schedule.
     */
    public void execute() {
        Ui ui = new Ui();
        try {
            anomaly.detect(userInput);
            int index = Integer.parseInt(this.userInput[1]);
            index--;
            RecommendedSchedule.addFromTaskList(index);
            RecommendedSchedule.displayRecommendedSchedule();
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
        }
    }
}
