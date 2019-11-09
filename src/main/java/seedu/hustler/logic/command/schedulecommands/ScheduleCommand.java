package seedu.hustler.logic.command.schedulecommands;

import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.AddEntryAnomaly;
import seedu.hustler.logic.parser.anomaly.ScheduleAnomaly;
import seedu.hustler.schedule.Scheduler;
import seedu.hustler.ui.Ui;
import seedu.hustler.Hustler;

/**
 * Displays the recommended schedule.
 */
public class ScheduleCommand extends Command {

    /**
     * User input.
     */
    private String[] userInput;

    /**
     * Detects anomalies for input.
     */
    private ScheduleAnomaly anomaly = new ScheduleAnomaly();
    
    /**
     * Initializes user input with supplied input.
     *
     * @param userInput initialization of the userInput
     */
    public ScheduleCommand(String[] userInput) {
        this.userInput = userInput;
    }

    /**
     * Displays the recommended schedule.
     */
    public void execute() {
        Ui ui = new Ui();
        try {
            anomaly.detect(userInput);
            Scheduler.displayRecommendedSchedule();
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
        }
    }
}
