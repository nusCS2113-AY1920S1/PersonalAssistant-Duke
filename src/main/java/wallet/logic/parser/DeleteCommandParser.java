package wallet.logic.parser;

import wallet.exception.WrongParameterFormat;
import wallet.logic.command.DeleteCommand;
import java.text.ParseException;

/**
 * The DeleteCommandParser Class converts user String to
 * appropriate parameters.
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    public static final String MESSAGE_ERROR_INVALID_ID = "You need to provide a valid ID (Number) when deleting.";

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
        int id = 0;
        try {
            id = Integer.parseInt(arguments[1]);
        } catch (NumberFormatException err) {
            throw new WrongParameterFormat(MESSAGE_ERROR_INVALID_ID);
        }

        return new DeleteCommand(arguments[0], id);
    }

}
