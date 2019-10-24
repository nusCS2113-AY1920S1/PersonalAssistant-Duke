package parser;

import command.AddCommand;
import command.Command;
import exception.DukeException;

/**
 * Extract the components required to add a TodoWithDuration task.
 *
 * @author Fauzan
 * @version v1.0
 */
public class TodoWithDurationParser extends TodoParser {

    private static final String INVALID_DURATION = "Invalid duration format. Duration must be a number";

    public TodoWithDurationParser(String userInput, String command) {
        super(userInput, command);
        this.checkType = Flag.FOR.getFlag();
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();
        int duration = extractDuration(taskFeatures);

        return new AddCommand(command, taskDescription, duration);
    }

    private int extractDuration(String taskFeatures) throws DukeException {
        String substring = taskFeatures.split(checkType, 2)[1].trim();
        int duration;
        try {
            duration = Integer.parseInt(substring.split("\\s+", 2)[0].trim());
        } catch (NumberFormatException e) {
            throw new DukeException(INVALID_DURATION);
        }
        return duration;
    }
}
