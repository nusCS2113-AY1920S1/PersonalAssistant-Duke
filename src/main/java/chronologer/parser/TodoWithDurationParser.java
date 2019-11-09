package chronologer.parser;

import chronologer.command.AddCommand;
import chronologer.command.Command;
import chronologer.exception.ChronologerException;
import chronologer.ui.UiMessageHandler;

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
    public Command parse() throws ChronologerException {
        super.extract();
        int duration = extractDuration(taskFeatures);
        return new AddCommand(command, taskDescription, duration);
    }

    private int extractDuration(String taskFeatures) throws ChronologerException {
        String substring = taskFeatures.split(checkType, 2)[1].trim();
        int duration;
        try {
            duration = Integer.parseInt(substring.split("\\s+", 2)[0].trim());
        } catch (NumberFormatException e) {
            UiMessageHandler.outputMessage(INVALID_DURATION);
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(INVALID_DURATION);
        }
        return duration;
    }
}
