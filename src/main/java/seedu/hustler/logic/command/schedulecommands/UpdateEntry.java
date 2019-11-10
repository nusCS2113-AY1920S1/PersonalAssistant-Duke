package seedu.hustler.logic.command.schedulecommands;

import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.UpdateEntryAnomaly;
import seedu.hustler.ui.Ui;
import seedu.hustler.schedule.Scheduler;

/**
 * Changes the time allocated to an entry in the
 * recommended schedule.
 */
public class UpdateEntry extends Command {
    /**
     * User's input that needs to be parsed.
     */
    private String[] userInput;

    /**
     * Detects anomalies in input.
     */
    private UpdateEntryAnomaly anomaly = new UpdateEntryAnomaly();
    
    /**
     * Initialized the userInput with supplied value.
     *
     * @param userInput initialization value
     */
    public UpdateEntry(String[] userInput) {
        this.userInput = userInput;
    }
    
    /**
     * Changes the time allocated to a task in the recommended
     * schedule.
     */
    public void execute() {
        Ui ui = new Ui();
        try {
            anomaly.detect(this.userInput);
            String[] numbers = userInput[1].split(" ");
            int index = Integer.parseInt(numbers[0]);
            int time = this.parseDuration(numbers[1]); 
            index--;
            Scheduler.updateAllocTime(index, time);
            Scheduler.displayRecommendedSchedule();
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
            return;
        }
    }

    /**
     * Parses the date time supplied in the form of H:M:S
     * to seconds.
     *
     * @param datetime datetime duration to parse
     * @return parsed number of seconds
     */
    public int parseDuration(String datetime) {
        String[] times = datetime.split(":");
        int hours = Integer.parseInt(times[0]);
        int minutes = Integer.parseInt(times[1]);
        int seconds = Integer.parseInt(times[2]);
        return hours * 3600 + minutes * 60 + seconds;
    }
}
