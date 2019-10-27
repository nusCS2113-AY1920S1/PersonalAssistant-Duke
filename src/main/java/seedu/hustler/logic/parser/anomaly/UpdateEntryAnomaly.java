package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.ui.Ui;
import seedu.hustler.schedule.RecommendedSchedule;

public class UpdateEntryAnomaly extends DetectAnomaly {

    /**
     * Detects anomaly in user input.
     *
     * @param userInput the index issued by the user
     * @return true or false for anomaly detected
     */
    public boolean detect(String[] userInput) {
        Ui ui = new Ui();

        String[] numbers = userInput[1].split(" ");
        if (numbers.length != 2) {
            ui.show_message("Please follow the format: /update <index> <H:M:S>"); 
            return true;
        }
        int index = -1;
        try {
            index = Integer.parseInt(numbers[0]);
        } catch (NumberFormatException e) {
            ui.show_message("Please follow the format: /update <index> <H:M:S>"); 
            return true;
        }
        index--;

        try {
            RecommendedSchedule.recommended.get(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.show_message("Please enter the correct index.");
            return true;
        }
        
        try {
            String[] times = numbers[1].split(":");
            Integer.parseInt(times[0]);
            Integer.parseInt(times[1]);
            Integer.parseInt(times[2]);
        } catch (NumberFormatException e) {
            ui.show_message("Please follow the format: /update <index> <H:M:S>"); 
            return true;
        }

        return false;
    }
}
