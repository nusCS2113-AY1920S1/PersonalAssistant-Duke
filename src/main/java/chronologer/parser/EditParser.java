package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.EditCommand;
import chronologer.exception.ChronologerException;
import chronologer.ui.UiTemporary;

/**
 * Extract the components required for the edit command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.1
 */
public class EditParser extends IndexParser {

    private static final String EMPTY_DESCRIPTION = "No description";

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
        String newDescription;
        try {
            newDescription = editCommandParts[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            UiTemporary.printOutput(ChronologerException.emptyUserDescription());
            logger.writeLog(EMPTY_DESCRIPTION, this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.emptyUserDescription());
        }
        return newDescription;
    }

}
