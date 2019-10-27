package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.schedule.RecommendedSchedule;
import seedu.hustler.logic.CommandLineException;

public class UpdateEntryAnomaly extends DetectAnomaly {

    /**
     * Detects anomaly in user input.
     *
     * @param userInput the index issued by the user
     * @throws CommandLineException for anomalies detected
     */
    public void detect(String[] userInput) throws CommandLineException {
        String[] numbers = userInput[1].split(" ");
        if (numbers.length != 2) {
            throw new CommandLineException("Please follow the format: /update <index> <H:M:S>");
        }
        try {
            int index = -1;
            index = Integer.parseInt(numbers[0]);
            index--;
            RecommendedSchedule.recommended.get(index);
            String[] times = numbers[1].split(":");
            Integer.parseInt(times[0]);
            Integer.parseInt(times[1]);
            Integer.parseInt(times[2]);
        } catch (NumberFormatException e) {
            throw new CommandLineException("Please follow the format: /update <index> <H:M:S>");
        } catch (IndexOutOfBoundsException e) {
            throw new CommandLineException("Please enter the correct index.");
        }
    }
}
