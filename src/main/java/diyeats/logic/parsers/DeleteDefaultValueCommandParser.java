package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.DeleteDefaultValueCommand;

public class DeleteDefaultValueCommandParser implements ParserInterface<DeleteDefaultValueCommand> {
    /**
     * Parses user input and returns an AddCommand encapsulating a breakfast object.
     * @param userInputStr String input by user.
     * @return <code>AddCommand</code> Command object encapsulating a breakfast object
     */
    @Override
    public DeleteDefaultValueCommand parse(String userInputStr) {
        try {
            InputValidator.validate(userInputStr);
        } catch (ProgramException e) {
            return new DeleteDefaultValueCommand(false, "Please input name of entry you wish to delete");
        }
        return new DeleteDefaultValueCommand(userInputStr);
    }
}
