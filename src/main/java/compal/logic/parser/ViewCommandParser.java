package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.ViewCommand;
import compal.logic.parser.exceptions.ParserException;

/**
 * Parses input arguments and creates a new ViewCommand object.
 */
public class ViewCommandParser implements CommandParser {
    public static final String MESSAGE_INVALID_PARAM = "Invalid parameter for view command.";


    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     *
     * @param input parameter of the input string
     * @return the prepared command
     */
    @Override
    public Command parseCommand(String input) throws ParserException {
        String[] viewArgs = input.split(" ");
        String emptyString = "";
        String viewType = viewArgs[0];
        if (emptyString.equals(viewType)) {
            throw new ParserException(MESSAGE_MISSING_TOKEN);
        }

        getDate(input);

        if (viewArgs.length > 3) {
            getType(input);
        }

        switch (viewType) {
        case "/month":
        case "/week":
        case "/day":
            return new ViewCommand(viewArgs);
        default:
            break;
        }

        throw new ParserException(MESSAGE_INVALID_PARAM);
    }
}
