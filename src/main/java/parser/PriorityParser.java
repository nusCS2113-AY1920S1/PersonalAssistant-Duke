package parser;

import command.Command;
import command.PriorityCommand;
import exception.DukeException;

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
    public Command parse() throws DukeException {
        super.extract();
        String priorityString = extractPriority(taskFeatures);
        return new PriorityCommand(indexOfTask, priorityString);
    }

    private String extractPriority(String taskFeatures) throws DukeException {
        String priorityString;
        try {
            String[] priorityCommandParts = taskFeatures.split("\\s+", 2);
            priorityString = priorityCommandParts[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyPriorityLevel());
        }
        return priorityString;
    }


}
