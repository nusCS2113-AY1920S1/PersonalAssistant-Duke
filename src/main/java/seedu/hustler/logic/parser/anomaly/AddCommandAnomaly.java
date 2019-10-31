package seedu.hustler.logic.parser.anomaly;

import java.util.List;
import java.util.Arrays;
import java.time.format.DateTimeParseException;
import seedu.hustler.task.TaskList;
import seedu.hustler.logic.CommandLineException;
import static seedu.hustler.logic.parser.DateTimeParser.getDateTime;

/**
 * Detects anomalies in add command inputted by user.
 */
public class AddCommandAnomaly extends DetectAnomaly {

    /**
     * Detects anomaly in add command input.
     *
     * @param userInput input for which anomaly is detected
     * @throws CommandLineException if the user input does not conform the expected format
     */
    public void detect(String[] userInput) throws CommandLineException {
        if (userInput.length == 1 || userInput[1].isBlank()) {
            throw new CommandLineException("Task description cannot be empty!");
        }

        try {
            List<String> parsedInput = Arrays.asList(userInput[1].split(" "));
            if (parsedInput.contains("/d")) {
                int difficultyIndex = parsedInput.indexOf("/d") + 1;
                String difficulty = parsedInput.get(difficultyIndex);
                if (!(difficulty.equals("H") || difficulty.equals("L") || difficulty.equals("M"))) {
                    throw new CommandLineException("Difficulty should be H, M or L.");
                }
            }

            if (parsedInput.contains("/tag")) {
                int tagIndex = parsedInput.indexOf("/tag") + 1;
                parsedInput.get(tagIndex);
            }

            if (parsedInput.contains("/by") || parsedInput.contains("/at")) {
                getDateTime(TaskList.getTimeString(parsedInput));
                if (parsedInput.contains("/every")) {
                    int everyIndex = parsedInput.indexOf("/every");
                    Integer.parseInt(parsedInput.get(everyIndex + 1));
                    String unit = parsedInput.get(everyIndex + 2);
                    if (!(unit.equals("days") || unit.equals("weeks") ||  unit.equals("minutes")
                        || unit.equals("hours") || unit.equals("months"))) {
                        throw new CommandLineException("/every units are minutes, hours, days, weeks, months.");
                    }
                }
            } else if (parsedInput.contains("/every")) {
                throw new CommandLineException("/every does not work on ToDo tasks.");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CommandLineException("The description of the command does not follow the right format.\n"
                    + "\tRefer to User Guide for more info.");
        } catch (DateTimeParseException e) {
            throw new CommandLineException("Date Time should follow the format DD/MM/YY HHmm.");
        } catch (NumberFormatException e) {
            throw new CommandLineException("Please enter an integer after /every");
        }
    } 
}
