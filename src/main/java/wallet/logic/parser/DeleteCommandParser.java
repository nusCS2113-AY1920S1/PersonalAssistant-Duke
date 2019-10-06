package wallet.logic.parser;

import wallet.logic.command.DeleteCommand;

import java.text.ParseException;

/**
 * The DeleteCommandParser Class converts user String to
 * appropriate parameters.
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Changes user input String to appropriate parameters
     * and returns a DeleteCommand object.
     *
     * @param input User input of command.
     * @return A DeleteCommand object.
     * @throws ParseException ParseException.
     */
    @Override
    public DeleteCommand parse(String input) throws ParseException {
        String[] arguments = input.split(" ", 2);

        int index = Integer.parseInt(arguments[1]);
        return new DeleteCommand(arguments[0], index);
    }
}
