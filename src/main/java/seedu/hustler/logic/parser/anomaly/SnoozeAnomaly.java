package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.task.ToDo;

import java.time.format.DateTimeParseException;
import java.util.Arrays;

import static seedu.hustler.logic.parser.DateTimeParser.getDateTime;

/**
 * Detects anomalies in snooze command inputted by user.
 */
public class SnoozeAnomaly extends DetectAnomaly {

    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Snooze format should be: '/snooze <index> <date> <time>' OR\n\t"
            + "'/snooze <index> <integer> <unit>'";
    private static final String MESSAGE_INVALID_INDEX = "The task index provided is invalid!";
    private static final String MESSAGE_INVALID_DATE_TIME = "Date Time should follow the format DD/MM/YY HHmm.";
    private static final String MESSAGE_INVALID_PERIOD = "The <unit> provided is invalid!\n\t"
            + "Valid <unit> are minutes/hours/days/weeks/months.";
    private static final String MESSAGE_INVALID_TASK_TYPE = "You cannot snooze a ToDo Task!";
    private static final String MESSAGE_INVALID_INTEGER = "Please enter an integer for <index>!";

    /**
     * Detects anomalies in snooze command input.
     *
     * @param userInput input for which anomaly is detected
     * @throws CommandLineException if the user input does not conform the expected format
     */
    @Override
    public void detect(String[] userInput) throws CommandLineException {
        if (userInput.length == 1) {
            throw new CommandLineException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        String[] parsedInput = userInput[1].split(" ");
        if (parsedInput.length != 3) {
            throw new CommandLineException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        int snoozeIndex;
        try {
            snoozeIndex = Integer.parseInt(parsedInput[0]);
        } catch (NumberFormatException e) {
            throw new CommandLineException(MESSAGE_INVALID_INTEGER);
        }

        snoozeIndex--;
        if (snoozeIndex >= Hustler.list.size()) {
            throw new CommandLineException(MESSAGE_INVALID_INDEX);
        }

        if (Hustler.list.get(snoozeIndex) instanceof ToDo) {
            throw new CommandLineException((MESSAGE_INVALID_TASK_TYPE));
        }

        // 1st method to snooze
        if (parsedInput[1].contains("/")) {
            String dateTime = parsedInput[1] + " " + parsedInput[2];
            try {
                getDateTime(dateTime);
            } catch (DateTimeParseException e) {
                throw new CommandLineException(MESSAGE_INVALID_DATE_TIME);
            }
            return;
        } else {
            // 2nd method to snooze
            try {
                Integer.parseInt(parsedInput[1]);
                String unit = parsedInput[2];
                String[] validUnits = {"minutes", "hours", "days", "weeks", "months"};
                if (!Arrays.asList(validUnits).contains(unit)) {
                    throw new CommandLineException(MESSAGE_INVALID_PERIOD);
                }
            } catch (NumberFormatException e) {
                throw new CommandLineException(MESSAGE_INVALID_INTEGER);
            }
        }
    }
}
