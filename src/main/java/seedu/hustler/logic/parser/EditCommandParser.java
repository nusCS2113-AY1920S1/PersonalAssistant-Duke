package seedu.hustler.logic.parser;

import seedu.hustler.logic.command.task.editcommands.EditDifficultyCommand;
import seedu.hustler.logic.command.task.editcommands.Edit;
import seedu.hustler.logic.command.task.editcommands.EditDescriptionCommand;
import java.util.List;
import java.util.Arrays;

/**
 * Parses which edit to be made.
 */
public class EditCommandParser extends Parser {
    /**
     * This method takes the raw user input for edit command and finds out which
     * edit is to be made.
     *
     * @param rawInput user's single line string input
     * @return an Edit to be made.
     */
    public Edit parse(String rawInput) {
        List<String> splitInput = Arrays.asList(rawInput.split(" "));
        int indexOfindex = splitInput.indexOf("/id") + 1;
        int index = Integer.parseInt(splitInput.get(indexOfindex));
        index--;
        
        if (rawInput.contains("/difficulty")) {
            int difficultyIndex = rawInput.indexOf("/difficulty") + "/difficulty".length() + 1;
            String difficulty = rawInput.substring(difficultyIndex);
            return new EditDifficultyCommand(index, difficulty);
        }
        /* else if (rawInput.contains("/description")) { */
        else {
            int descriptionIndex = rawInput.indexOf("/description") + "/description".length() + 1;
            String description = rawInput.substring(descriptionIndex);
            return new EditDescriptionCommand(index, description);
        }
    }
}
