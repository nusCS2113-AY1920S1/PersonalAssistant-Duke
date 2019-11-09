package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.PriorityCommand;
import chronologer.exception.ChronologerException;
import chronologer.ui.UiTemporary;

/**
 * Extract the components required for the priority command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.1
 */
public class PriorityParser extends IndexParser {

    PriorityParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        String priorityString = extractPriority(taskFeatures);
        return new PriorityCommand(indexOfTask, priorityString);
    }


    /**
     * Extract the priority string component from user input.
     *
     * @param taskFeatures The user input
     * @return The priority string
     * @throws ChronologerException If the priority string is empty.
     */
    private String extractPriority(String taskFeatures) throws ChronologerException {
        String priorityString;
        try {
            String[] priorityCommandParts = taskFeatures.split("\\s+", 2);
            priorityString = priorityCommandParts[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            UiTemporary.printOutput(ChronologerException.emptyPriorityLevel());
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.emptyPriorityLevel());
        }
        return priorityString;
    }


}
