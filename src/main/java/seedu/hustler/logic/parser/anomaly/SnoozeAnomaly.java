package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;
import static seedu.hustler.logic.parser.DateTimeParser.getDateTime;

/**
 * Detects snooze anomalies in user input.
 */
public class SnoozeAnomaly extends DetectAnomaly {

    /**
     * Detects anomalies for input.
     *
     * @param userInput input for which anomaly is detected
     * @throws CommandLineException if the user input does not conform the expected format
     */
    @Override
    public void detect(String[] userInput) throws CommandLineException {
        String[] splitInput = userInput[1].split(" ");

        if (splitInput.length != 3) {
            throw new CommandLineException("Incorrect format for snooze command.");
        }

        int index;
        try {
            index = Integer.parseInt(splitInput[0]);
        } catch (NumberFormatException e) {
            throw new CommandLineException("Please enter a number after the command.");
        }

        // 0-based index
        index--;
        if (index >= Hustler.list.size()) {
            throw new CommandLineException("The task index provided is invalid.");
        }

        // 1st method to snooze
        if (splitInput[1].contains("/")) {
            String dateTimeString = splitInput[1] + " " + splitInput[2];
            if (getDateTime(dateTimeString) == null) {
                throw new CommandLineException("Invalid date and time format.");
            }
        } else {
            // 2nd method to snooze
            String period = splitInput[2];
            period.toLowerCase();
            String[] validPeriods = {"minutes", "hours", "days", "weeks", "months"};
            for (String validPeriod : validPeriods) {
                if (period.equals(validPeriod)) {
                    return;
                }
            }
            throw new CommandLineException("Invalid input period.");
        }
    }
}
