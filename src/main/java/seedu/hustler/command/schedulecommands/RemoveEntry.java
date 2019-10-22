package seedu.hustler.command.schedulecommands;

import seedu.hustler.command.Command;
import seedu.hustler.ui.Ui;
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
        Ui ui = new Ui();
        try {
            int index = Integer.parseInt(this.userInput[1]);
            index--;
            
            RecommendedSchedule.recommended.remove(index);
            RecommendedSchedule.reTime();
            RecommendedSchedule.displayRecommendedSchedule();
        } catch (NumberFormatException e) {
            ui.numberCommandError();
        }
    }
}
