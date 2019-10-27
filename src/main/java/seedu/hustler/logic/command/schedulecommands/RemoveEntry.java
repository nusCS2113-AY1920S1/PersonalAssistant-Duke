package seedu.hustler.logic.command.schedulecommands;

import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.RemoveEntryAnomaly;
import seedu.hustler.schedule.RecommendedSchedule;

/**
 * Removes an entry from the recommended schedule.
 */
public class RemoveEntry extends Command {
    /**
     * User input to parse.
     */
    private String[] userInput;

    /**
     * Detects anomalies in input.
     */
    private RemoveEntryAnomaly anomaly = new RemoveEntryAnomaly();
    
    /**
     * Initializes userInput with supplied value.
     *
     * @param userInput initialization for the userInput
     */
    public RemoveEntry(String[] userInput) {
        this.userInput = userInput;
    }
    
    /**
     * Removes an entry from the recommended schedule.
     */
    public void execute() {
        if (anomaly.detect(this.userInput)) {
            return; 
        }
        int index = Integer.parseInt(this.userInput[1]);
        index--;

        RecommendedSchedule.recommended.remove(index);
        RecommendedSchedule.reTime();
        RecommendedSchedule.displayRecommendedSchedule();
    }
}
