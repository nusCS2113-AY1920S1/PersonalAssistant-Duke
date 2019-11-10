package seedu.hustler.logic.parser.anomaly;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;
import java.time.format.DateTimeParseException;

import seedu.hustler.data.CommandLog;
import seedu.hustler.task.TaskList;
import seedu.hustler.logic.CommandLineException;
import static seedu.hustler.logic.parser.DateTimeParser.getDateTime;

/**
 * Detects anomalies in add command inputted by user.
 */
public class AddCommandAnomaly extends DetectAnomaly {

    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "The add command does not follow the right format.\n"
            + "\t_____________________________________\n"
            + "\tPlease follow the format given below:\n"
            + "\tAdding ToDo: /add <task description>\n"
            + "\tAdding Deadline: /add <task description> /by <date> <time>\n"
            + "\tAdding Event: /add <task description> /at <date> <time>\n"
            + "\t_____________________________________\n"
            + "\tOptional that can be appended to above format\n"
            + "\tAdd recurring: /every <integer> <unit>\n"
            + "\tAdd difficulty: /d <difficulty>\n"
            + "\tAdd tags: /tags <tag>";
    private static final String MESSAGE_EMPTY_TASK_DESCRIPTION = "Task description cannot be empty!";
    private static final String MESSAGE_INVALID_DIFFICULTY = "Invalid difficulty provided. Difficulty should be H/M/L.";
    private static final String MESSAGE_INVALID_DATE_TIME = "Date Time should follow the format DD/MM/YYYY HHmm.";
    private static final String MESSAGE_INVALID_PERIOD = "The <unit> provided is invalid!\n\t"
            + "Valid <unit> are minutes/hours/days/weeks/months.";
    private static final String MESSAGE_PASSED_DATE_TIME = "A past date and time has been provided.\n"
        + "\tPlease only provide upcoming date and time.";

    /**
     * Detects anomaly in add command input.
     *
     * @param userInput input for which anomaly is detected
     * @throws CommandLineException if the user input does not conform the expected format
     */
    public void detect(String[] userInput) throws CommandLineException {
        if (userInput.length == 1 || userInput[1].isBlank()) {
            CommandLog.removeLastCommand();
            throw new CommandLineException(MESSAGE_EMPTY_TASK_DESCRIPTION);
        }

        String taskDescription = userInput[1].split("/by|/at")[0];
        if (taskDescription.isBlank()) {
            CommandLog.removeLastCommand();
            throw new CommandLineException(MESSAGE_EMPTY_TASK_DESCRIPTION);
        }

        try {
            List<String> parsedInput = Arrays.asList(userInput[1].split(" "));

            if (parsedInput.contains("/d")) {
                int difficultyIndex = parsedInput.indexOf("/d") + 1;
                String difficulty = parsedInput.get(difficultyIndex);
                String[] validDifficulty = {"H", "M", "L"};
                if (!Arrays.asList(validDifficulty).contains(difficulty)) {
                    CommandLog.removeLastCommand();
                    throw new CommandLineException(MESSAGE_INVALID_DIFFICULTY );
                }
            }

            if (parsedInput.contains("/tag")) {
                int tagIndex = parsedInput.indexOf("/tag") + 1;
                parsedInput.get(tagIndex);
            }

            if (parsedInput.contains("/by") || parsedInput.contains("/at")) {
                if (LocalDateTime.now().isAfter(getDateTime(TaskList.getTimeString(parsedInput)))) {
                    CommandLog.removeLastCommand();
                    throw new CommandLineException(MESSAGE_PASSED_DATE_TIME);
                }
                if (parsedInput.contains("/every")) {
                    int everyIndex = parsedInput.indexOf("/every");
                    Integer.parseInt(parsedInput.get(everyIndex + 1));
                    String unit = parsedInput.get(everyIndex + 2);
                    String[] validUnits = {"minutes", "hours", "days", "weeks", "months"};
                    if (!Arrays.asList(validUnits).contains(unit)) {
                        CommandLog.removeLastCommand();
                        throw new CommandLineException(MESSAGE_INVALID_PERIOD);
                    }
                }
            } else if (parsedInput.contains("/every")) {
                CommandLog.removeLastCommand();
                throw new CommandLineException("/every does not work on ToDo tasks.");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            CommandLog.removeLastCommand();
            throw new CommandLineException(MESSAGE_INVALID_COMMAND_FORMAT);
        } catch (DateTimeParseException e) {
            CommandLog.removeLastCommand();
            throw new CommandLineException(MESSAGE_INVALID_DATE_TIME);
        } catch (NumberFormatException e) {
            CommandLog.removeLastCommand();
            throw new CommandLineException("Please enter an integer after /every");
        }
    } 
}
