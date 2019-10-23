package parser;

import command.Command;
import command.EditCommand;
import exception.DukeException;

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
    public Command parse() throws DukeException {
        super.extract();
        String newDescription = extractNewDescription(taskFeatures);
        return new EditCommand(indexOfTask, newDescription);
    }

    private String extractNewDescription(String taskFeatures) throws DukeException {
        String[] editCommandParts = taskFeatures.split("\\s+", 2);
        String newDescription = editCommandParts[1].trim();
        if (newDescription.isEmpty()) {
            throw new DukeException(DukeException.emptyUserDescription());
        }
        return newDescription;
    }

}
