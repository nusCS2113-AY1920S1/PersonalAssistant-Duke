package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.EditCommand;
import chronologer.exception.ChronologerException;

/**
 * Extract the components required for the edit command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class EditParser extends IndexParser {

    public EditParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        String newDescription = extractNewDescription(taskFeatures);
        return new EditCommand(indexOfTask, newDescription);
    }

    private String extractNewDescription(String taskFeatures) throws ChronologerException {
        String[] editCommandParts = taskFeatures.split("\\s+", 2);
        String newDescription = editCommandParts[1].trim();
        if (newDescription.isEmpty()) {
            throw new ChronologerException(ChronologerException.emptyUserDescription());
        }
        return newDescription;
    }

}
