package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.schedule.Scheduler;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.Hustler;

public class UpdateEntryAnomaly extends DetectAnomaly {

    /**
     * Detects anomaly in user input.
     *
     * @param userInput the index issued by the user
     * @throws CommandLineException for anomalies detected
     */
    public void detect(String[] userInput) throws CommandLineException {
        try {
            if (!Hustler.timermanager.isRunning()) {
                throw new CommandLineException("Please start the time completion mode using the /timer command.");
            }
            if (userInput.length == 1) {
                throw new CommandLineException("Please follow the format: /update <index> <H:M:S>");
            }
            String[] numbers = userInput[1].split(" ");
            if (numbers.length != 2) {
                throw new CommandLineException("Please follow the format: /update <index> <H:M:S>");
            }
            int index = -1;
            index = Integer.parseInt(numbers[0]);
            index--;
            Scheduler.recommended.get(index);
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
