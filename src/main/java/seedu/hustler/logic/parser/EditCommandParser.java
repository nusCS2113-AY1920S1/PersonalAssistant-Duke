package seedu.hustler.logic.parser;

import seedu.hustler.logic.command.task.editcommands.EditDifficultyCommand;
import seedu.hustler.logic.command.task.editcommands.Edit;
import seedu.hustler.logic.command.task.editcommands.EditDescriptionCommand;
import seedu.hustler.logic.command.task.editcommands.InvalidEditCommand;
import java.util.List;
import java.util.Arrays;
import seedu.hustler.logic.CommandLineException;

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
    public Edit parse(String rawInput) throws CommandLineException {
        List<String> splitInput = Arrays.asList(rawInput.split(" "));
        if (!splitInput.contains("/id")) {
            throw new CommandLineException("/edit command requires an index using /id keyword.");
        }
        try {
            int indexOfindex = splitInput.indexOf("/id") + 1;
            int index = Integer.parseInt(splitInput.get(indexOfindex));
            index--;

            if (rawInput.contains("/difficulty")) {
                int difficultyIndex = rawInput.indexOf("/difficulty") + "/difficulty".length() + 1;
                try {
                    String difficulty = rawInput.substring(difficultyIndex);
                    return new EditDifficultyCommand(index, difficulty);
                } catch (StringIndexOutOfBoundsException e) {
                    throw new CommandLineException("Please enter a difficulty");
                }
            } else if (rawInput.contains("/description")) {
                int descriptionIndex = rawInput.indexOf("/description") + "/description".length() + 1;
                try {
                    String description = rawInput.substring(descriptionIndex);
                    return new EditDescriptionCommand(index, description);
                } catch (StringIndexOutOfBoundsException e) {
                    throw new CommandLineException("Please enter a description");
                }
            } else {
                return new InvalidEditCommand();
            }
        } catch (NumberFormatException e) {
            throw new CommandLineException("Please enter an index.");
        }
    }
}
