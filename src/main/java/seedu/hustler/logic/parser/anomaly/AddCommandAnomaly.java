package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.logic.CommandLineException;
import java.util.List;
import java.util.Arrays;
import java.time.format.DateTimeParseException;
import seedu.hustler.task.TaskList;
import static seedu.hustler.logic.parser.DateTimeParser.getDateTime;

public class AddCommandAnomaly extends DetectAnomaly {

    /**
     * Detects anomaly in user input.
     *
     * @param userInput the index issued by the user
     * @throws CommandLineException for anomalies detected
     */
    public void detect(String[] userInput) throws CommandLineException {
        try {
            List<String> splitInput = Arrays.asList(userInput[1].split(" "));
            if (splitInput.contains("/d")) {
                int difficultyIndex = splitInput.indexOf("/d") + 1;
                splitInput.get(difficultyIndex);
            }

            if (splitInput.contains("/tag")) {
                int tagIndex = splitInput.indexOf("/d") + 1;
                splitInput.get(tagIndex);
            }
            if (splitInput.contains("/by") || splitInput.contains("/at")) {
                String timeStr = TaskList.getTime(splitInput);
                getDateTime(timeStr);
                if (splitInput.contains("/every")) {
                    int everyIndex = splitInput.indexOf("/every");
                    String frequency = splitInput.get(everyIndex + 1) + " " + splitInput.get(everyIndex + 2);
                    TaskList.convertToMinute(frequency);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CommandLineException("The description of the command does not follow the right"
                + " format. Please check user guide.");
        } catch (DateTimeParseException e) {
            throw new CommandLineException("Date Time should follow the format DD/MM/YY HHmm.");
        } catch (NumberFormatException e) {
            throw new CommandLineException("The description of the command does not follow the right"
                + " format. Please check user guide.");
        }
    } 
}
