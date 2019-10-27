package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.ui.Ui;
import seedu.hustler.Hustler;
import seedu.hustler.schedule.RecommendedSchedule;

public class RemoveEntryAnomaly extends DetectAnomaly {

    /**
     * Detects anomaly in user input.
     *
     * @param userInput the index issued by the user
     * @return true or false for anomaly detected
     */
    public boolean detect(String[] userInput) {
        Ui ui = new Ui();
        int index = -1;
        try {
            index = Integer.parseInt(userInput[1]);
        } catch (NumberFormatException e) {
            ui.numberCommandError();
            return true;
        }
        index--;

        try {
            RecommendedSchedule.recommended.get(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.show_message("Please enter the current index.");
            return true;
        }

        return false;
    } 
}
