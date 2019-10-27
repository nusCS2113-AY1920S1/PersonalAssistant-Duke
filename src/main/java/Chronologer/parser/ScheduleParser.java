package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.TaskScheduleCommand;
import chronologer.exception.ChronologerException;

/**
 * Extract the components required for the schedule command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class ScheduleParser extends IndexParser {

    private int indexOfDeadline;

    public ScheduleParser(String userInput, String command) {
        super(userInput, command);
    }


    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        indexOfDeadline = extractDeadline(taskFeatures);

        return new TaskScheduleCommand(indexOfTask, indexOfDeadline);
    }

    private int extractDeadline(String taskFeatures) throws ChronologerException {
        String extractedIndex = taskFeatures.split(Flag.BY.getFlag(), 2)[1].trim();
        int convertedIndex;
        try {
            convertedIndex = Integer.parseInt(extractedIndex) - 1;
        } catch (NumberFormatException e) {
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.unknownUserCommand());
        }

        return convertedIndex;
    }
}
