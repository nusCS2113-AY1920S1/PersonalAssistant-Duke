package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.PriorityCommand;
import chronologer.exception.ChronologerException;

/**
 * Extract the components required for the priority command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class PriorityParser extends IndexParser {

    public PriorityParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        String priorityString = extractPriority(taskFeatures);
        return new PriorityCommand(indexOfTask, priorityString);
    }

    private String extractPriority(String taskFeatures) throws ChronologerException {
        String priorityString;
        try {
            String[] priorityCommandParts = taskFeatures.split("\\s+", 2);
            priorityString = priorityCommandParts[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.emptyPriorityLevel());
        }
        return priorityString;
    }


}
