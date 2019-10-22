package seedu.hustler.command.schedulecommands;

import seedu.hustler.command.Command;
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
            int index = Integer.parseInt(this.userInput[1]);
            index--;
            RecommendedSchedule.addFromTaskList(index);
            RecommendedSchedule.displayRecommendedSchedule();
        } catch (NumberFormatException e) {
            ui.numberCommandError();
        }
    }
}
